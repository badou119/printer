package com.wx.common.utils;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import com.wx.enterprise.service.EnterpriseService;
import com.wx.enterprise.vo.Enterprise;
import com.wx.exampeople.service.JkzseqService;
import com.wx.exampeople.service.ProfessionService;
import com.wx.exampeople.vo.ExamPeople;
import com.wx.exampeople.vo.ExamPeoplePo;
import com.wx.exampeople.vo.Jkzseq;
import com.wx.exampeople.vo.Profession;

/**
 * 上传通讯录时如果是csv文件，则在此类中逐行读取信息进行处理。
 * 
 * @author JZY
 * 
 */
public class ReadCsv {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ReadCsv.class);

	String[] sb = new String[30];// 存放一条联系人的信息。
	String[] csvString = null; // 一行csv文件中的数据以","拆分得到的数组。

	String rowString = "";
	String groupid = "";

	OutputStreamWriter filewriter = null;
	OutputStreamWriter filewriteError = null;

	public int totalCount = 0;
	public int errorCount = 0;
	public List<ExamPeople> epList = new ArrayList<ExamPeople>();
	
	private BufferedReader bufferedreader = null;

	public ReadCsv(String filename, int minColumns,
			OutputStreamWriter filewrite, String groupid,OutputStreamWriter filewriteError,
			EnterpriseService enterpriseService,JkzseqService jkzseqService,
			ProfessionService professionService) throws IOException {

		this.filewriter = filewrite;
		this.filewriteError = filewriteError;
		this.groupid = groupid;
		bufferedreader = new BufferedReader(new FileReader(filename));
		
		String stemp;
		// 逐行遍历csv文件的内容
		while ((stemp = bufferedreader.readLine()) != null) {

			csvString = stemp.split(",");
			for (int i = 0; i < csvString.length; i++) {
				sb[i] = csvString[i].trim();// 将当前列的内容存到数组中
			}
			// 获取不少于minColumns列的一条联系人的数组。
			if (csvString.length < minColumns) {
				for (int i = csvString.length; i < (minColumns); i++) {
					sb[i] = "";// 将当前列的内容存到数组中
				}
			}
			sb[0] = sb[0].replace(" ", "");
			// 验证联系人的各列信息是否符合格式要求，符合格式的则计算其对应的全拼以及简拼，md5唯一标示。然后将数据存放到等待入库的csv文件中
			if (RegexCheck.checkString(sb, minColumns,groupid, filewriteError,enterpriseService,professionService)) {
				totalCount++;
				
				ExamPeople ePo = new ExamPeople();
				ePo.setId(CommonUtil.getUUID());
				ePo.setRegtime(new Date());
				ePo.setUpdatetime(new Date());
				ePo.setDeleted(1);
				
				String jkzcode = "";
				String ymd = Tools.getTodayStr();
				String lsh = "0001";
				
				Jkzseq jkzseq = jkzseqService.get(groupid, ymd);
				if(jkzseq != null){
					lsh = getjkzseq(jkzseq.getJkzseq());
					jkzseq.setJkzseq(lsh);//流水号+1
				}else{
					jkzseq = new Jkzseq();
					jkzseq.setId(CommonUtil.getUUID());
					jkzseq.setEnterid(groupid);
					jkzseq.setJkzseq("0001");
					jkzseq.setYmd(ymd);
				}
				jkzseqService.addOrUpdate(jkzseq);
				
				Enterprise enterprise = enterpriseService.load(groupid);
				jkzcode = ymd+enterprise.getEntercode()+lsh;
				ePo.setJkzcode(jkzcode);
				setExamPeople(ePo, minColumns,enterpriseService,professionService);
				
				epList.add(ePo);
				
				for (int index = 0; index < minColumns; index++) {
					rowString += sb[index] + ",";
				}
				try {
					filewriter.write(rowString + "\r\n");
				} catch (IOException e) {
					logger.error("ReadCsv(String, int, OutputStreamWriter, String, OutputStreamWriter, EnterpriseService)", e); //$NON-NLS-1$
					e.printStackTrace();
				}
			} else {
				errorCount++;
			}
		}
	}
	
	private void setExamPeople(ExamPeople ePo,int minColumns,EnterpriseService enterpriseService,ProfessionService professionService){
		if (logger.isDebugEnabled()) {
			logger.debug("setExamPeople(ExamPeople, int, EnterpriseService) - start"); //$NON-NLS-1$
		}
		
		for (int index = 0; index < minColumns; index++) {
			String checkS = sb[index].replace("\"", "").trim();
			
			switch (index) {
				case 0:// 姓名
					ePo.setName(checkS);
					break;
				case 1:// 身份证
					ePo.setCode(checkS);
					break;
				case 2:// 性别
					ePo.setSex(checkS);
					break;
				case 3:// 年龄 1-119
					ePo.setAge(Integer.parseInt(checkS));
					break;
				case 4:// 体检时间  2014/07/28
					SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");
					try {
						ePo.setExamtime(sdFormat.parse(checkS));
					} catch (ParseException e) {
						logger.error("setExamPeople(ExamPeople, int, EnterpriseService)", e); //$NON-NLS-1$
						e.printStackTrace();
					}
					break;
				case 5:// 体检单位
					List<Enterprise> list = enterpriseService.selectEnterprise(checkS);
					if(list!=null && list.size()>0){
						ePo.setEnterid(list.get(0).getId());
					}
					break;
				case 6:// 工种
					Profession profession = professionService.get("", checkS);
					ePo.setProfession(profession.getId());
					break;
				case 7:
					ePo.setRemark(checkS);
					break;
				default:
					break;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setExamPeople(ExamPeople, int, EnterpriseService) - end"); //$NON-NLS-1$
		}
	}
	
	private String getjkzseq(String jkzseq){
		String lsh = "0000"+(Integer.parseInt(jkzseq)+1);
		lsh = lsh.substring(lsh.length()-4, lsh.length());
		return lsh;
	}
}

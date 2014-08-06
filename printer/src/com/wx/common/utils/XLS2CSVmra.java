package com.wx.common.utils;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.ArrayList;

import org.apache.poi.hssf.eventusermodel.FormatTrackingHSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.hssf.eventusermodel.MissingRecordAwareHSSFListener;
import org.apache.poi.hssf.eventusermodel.EventWorkbookBuilder.SheetRecordCollectingListener;
import org.apache.poi.hssf.eventusermodel.dummyrecord.LastCellOfRowDummyRecord;
import org.apache.poi.hssf.eventusermodel.dummyrecord.MissingCellDummyRecord;
import org.apache.poi.hssf.model.HSSFFormulaParser;
import org.apache.poi.hssf.record.BOFRecord;
import org.apache.poi.hssf.record.BlankRecord;
import org.apache.poi.hssf.record.BoolErrRecord;
import org.apache.poi.hssf.record.BoundSheetRecord;
import org.apache.poi.hssf.record.FormulaRecord;
import org.apache.poi.hssf.record.LabelRecord;
import org.apache.poi.hssf.record.LabelSSTRecord;
import org.apache.poi.hssf.record.NoteRecord;
import org.apache.poi.hssf.record.NumberRecord;
import org.apache.poi.hssf.record.RKRecord;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.record.SSTRecord;
import org.apache.poi.hssf.record.StringRecord;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
/**
 * 上传通讯录时如果是csv文件，则在此类中逐行读取信息进行处理。
 * A XLS -> CSV processor, that uses the MissingRecordAware
 *  EventModel code to ensure it outputs all columns and rows.
 * @author JZY
 *
 */

public class XLS2CSVmra implements HSSFListener {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(XLS2CSVmra.class);

	private int minColumns;
	private POIFSFileSystem fs;
	private PrintStream output;

	//private int lastRowNumber;
	private int lastColumnNumber=-1;
    public int totalCount=0;
	/** Should we output the formula, or the value it has? */
	private boolean outputFormulaValues = true;

	/** For parsing Formulas */
	private SheetRecordCollectingListener workbookBuildingListener;
	private HSSFWorkbook stubWorkbook;

	// Records we pick up as we process
	private SSTRecord sstRecord;
	private FormatTrackingHSSFListener formatListener;
	
	/** So we known which sheet we're on */
	private int sheetIndex = -1;
	private BoundSheetRecord[] orderedBSRs;
	private ArrayList boundSheetRecords = new ArrayList();
	
	String filecscvname="";
	String rowString="";
	
	private int nextColumn;
	private boolean outputNextStringRecord;
	
	OutputStreamWriter   filewriter = null;
	
	
	/**
	 * Creates a new XLS -> CSV converter
	 * @param fs The POIFSFileSystem to process
	 * @param output The PrintStream to output the CSV to
	 * @param minColumns The minimum number of columns to output, or -1 for no minimum
	 */
	public XLS2CSVmra(POIFSFileSystem fs, PrintStream output, int minColumns) {
		this.fs = fs;
		this.output = output;
		this.minColumns = minColumns;
	}

	/**
	 * Creates a new XLS -> CSV converter
	 * @param filename The file to process
	 * @param minColumns The minimum number of columns to output, or -1 for no minimum
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public XLS2CSVmra(String filename, int minColumns,OutputStreamWriter filewrite,String groupid,OutputStreamWriter filewriteError) throws IOException, FileNotFoundException {
		
		this(
				new POIFSFileSystem(new FileInputStream(filename)),
				System.out, minColumns
		);
		this.filewriter=filewrite;
		
		
	}

	/**
	 * Initiates the processing of the XLS file to CSV
	 */
	
	public void process() throws IOException {
		if (logger.isDebugEnabled()) {
			logger.debug("process() - start"); //$NON-NLS-1$
		}
 
		MissingRecordAwareHSSFListener listener = new MissingRecordAwareHSSFListener(this);
		formatListener = new FormatTrackingHSSFListener(listener);

		
		HSSFEventFactory factory = new HSSFEventFactory();
		HSSFRequest request = new HSSFRequest();

		if(outputFormulaValues) {
			request.addListenerForAllRecords(formatListener);
		} else {
			workbookBuildingListener = new SheetRecordCollectingListener(formatListener);
			request.addListenerForAllRecords(workbookBuildingListener);
		}

		factory.processWorkbookEvents(request, fs);

		if (logger.isDebugEnabled()) {
			logger.debug("process() - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Main HSSFListener method, processes events, and outputs the
	 *  CSV as the file is processed.
	 */
	
	public void processRecord(Record record) {
		if (logger.isDebugEnabled()) {
			logger.debug("processRecord(Record) - start"); //$NON-NLS-1$
		}

		//int thisRow = -1;
		int thisColumn = -1;
		String thisStr = null;
		switch (record.getSid())
		{
		case BoundSheetRecord.sid:
			boundSheetRecords.add(record);
			break;
		case BOFRecord.sid:
			BOFRecord br = (BOFRecord)record;
			if(br.getType() == BOFRecord.TYPE_WORKSHEET) {
				// Create sub workbook if required
				if(workbookBuildingListener != null && stubWorkbook == null) {
					stubWorkbook = workbookBuildingListener.getStubHSSFWorkbook();
				}
				
				// Output the worksheet name
				// Works by ordering the BSRs by the location of
				//  their BOFRecords, and then knowing that we
				//  process BOFRecords in byte offset order
				sheetIndex++;
				if(orderedBSRs == null) {
					orderedBSRs = BoundSheetRecord.orderByBofPosition(boundSheetRecords);
				}
				output.println();
				output.println( 
						orderedBSRs[sheetIndex].getSheetname() +
						" [" + (sheetIndex+1) + "]:"
				);
			}
			break;

		case SSTRecord.sid:
			sstRecord = (SSTRecord) record;
			break;

		case BlankRecord.sid:
			BlankRecord brec = (BlankRecord) record;

			//thisRow = brec.getRow();
			thisColumn = brec.getColumn();
			thisStr = "";
			break;
		case BoolErrRecord.sid:
			BoolErrRecord berec = (BoolErrRecord) record;

			//thisRow = berec.getRow();
			thisColumn = berec.getColumn();
			thisStr = "";
			break;

		case FormulaRecord.sid:
			FormulaRecord frec = (FormulaRecord) record;

			//thisRow = frec.getRow();
			thisColumn = frec.getColumn();

			if(outputFormulaValues) {
				if(Double.isNaN( frec.getValue() )) {
					// Formula result is a string
					// This is stored in the next record
					outputNextStringRecord = true;
					frec.getRow();
					nextColumn = frec.getColumn();
				} else {
					thisStr = formatListener.formatNumberDateCell(frec);
				}
			} else {
				thisStr = '"' +
					HSSFFormulaParser.toFormulaString(stubWorkbook, frec.getParsedExpression()) + '"';
			}
			break;
		case StringRecord.sid:
			if(outputNextStringRecord) {
				// String for formula
				StringRecord srec = (StringRecord)record;
				thisStr = srec.getString();
				//thisRow = nextRow;
				thisColumn = nextColumn;
				outputNextStringRecord = false;
			}
			break;

		case LabelRecord.sid:
			LabelRecord lrec = (LabelRecord) record;

			//thisRow = lrec.getRow();
			thisColumn = lrec.getColumn();
			thisStr = '"' + lrec.getValue() + '"';
			break;
		case LabelSSTRecord.sid:
			LabelSSTRecord lsrec = (LabelSSTRecord) record;

			//thisRow = lsrec.getRow();
			thisColumn = lsrec.getColumn();
			if(sstRecord == null) {
				thisStr = '"' + "(No SST Record, can't identify string)" + '"';
			} else {
				thisStr = '"' + sstRecord.getString(lsrec.getSSTIndex()).toString() + '"';
			}
			break;
		case NoteRecord.sid:
			NoteRecord nrec = (NoteRecord) record;

			//thisRow = nrec.getRow();
			thisColumn = nrec.getColumn();
			// TODO: Find object to match nrec.getShapeId()
			thisStr = '"' + "(TODO)" + '"';
			break;
		case NumberRecord.sid:
			NumberRecord numrec = (NumberRecord) record;

			//thisRow = numrec.getRow();
			thisColumn = numrec.getColumn();

			// Format
			thisStr = formatListener.formatNumberDateCell(numrec);
			break;
		case RKRecord.sid:
			RKRecord rkrec = (RKRecord) record;

			//thisRow = rkrec.getRow();
			thisColumn = rkrec.getColumn();
			thisStr = '"' + "(TODO)" + '"';
			break;
		default:
			break;
		}

		// Handle new row
//		if(thisRow != -1 && thisRow != lastRowNumber) {
//			lastColumnNumber = -1;
//		}

		// Handle missing column
		if(record instanceof MissingCellDummyRecord) {
			MissingCellDummyRecord mc = (MissingCellDummyRecord)record;
			//thisRow = mc.getRow();
			thisColumn = mc.getColumn();
			thisStr = "";
		}

		// If we got something to print out, do so
		if(thisStr != null) {
			
			//output.print(thisStr+",");
			rowString+=thisStr+",";
			//sb[thisColumn]=thisStr.trim();//将当前列的内容存到数组中

		}
		
		// Update column and row count
//		if(thisRow > -1)
//			lastRowNumber = thisRow;
		if(thisColumn > -1)
			lastColumnNumber = thisColumn;

		// Handle end of row一行数据的结尾
		if(record instanceof LastCellOfRowDummyRecord) {
			// Print out any missing commas if needed
			//得到一条联系人数据
			
				// Columns are 0 based
				
				for(int i=lastColumnNumber+1; i<(minColumns); i++) {
					//output.print(',');
					rowString+=",";
					//sb[i]="";//将当前列的内容存到数组中
				}
			
//			//验证联系人的各列信息是否符合格式要求，符合格式的则计算其对应的全拼以及简拼，md5唯一标示。然后将数据存放到等待入库的csv文件中
//			if (RegexCheck.checkString(sb, minColumns,filewriteError)) {
				totalCount++;
//				for (int index = 0; index < minColumns; index++) {
//					rowString += sb[index] + ",";
//				}
//				rowString = rowString + "\"" + groupid + "\"";
//				//md5唯一标示包括除部门名称以外的其他所有字段。
//				MD5 md5 = new MD5(md5string);
//				rowString = rowString + ",\"" + md5.compute() + "\"";
				try {
					filewriter.write(rowString + "\r\n");
				} catch (IOException e) {
				logger.error("processRecord(Record)", e); //$NON-NLS-1$

					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//			}
//            quanpin="";
//            jianpin="";
            rowString="";
//            md5string="";
           // sb=new String[30];
			// We're onto a new row
			lastColumnNumber = -1;

			// End the row
			output.println(totalCount);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("processRecord(Record) - end"); //$NON-NLS-1$
		}
	}
   
}

package com.wx.common.utils;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.StrutsConstants;
import org.apache.struts2.dispatcher.multipart.MultiPartRequest;

import com.opensymphony.xwork2.inject.Inject;

public class MineMultiPartRequest implements MultiPartRequest {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MineMultiPartRequest.class);
	
    static final Log log = LogFactory.getLog(MultiPartRequest.class);
    
    // maps parameter name -> List of FileItem objects
    private Map<String,List<FileItem>> files = new HashMap<String,List<FileItem>>();
    // maps parameter name -> List of param values
    private Map<String,List<String>> params = new HashMap<String,List<String>>();
    // any errors while processing this request
    private List<String> errors = new ArrayList<String>();
    
    private long maxSize;
    
    @Inject(StrutsConstants.STRUTS_MULTIPART_MAXSIZE)
    public void setMaxSize(String maxSize) {
		if (logger.isDebugEnabled()) {
			logger.debug("setMaxSize(String) - start"); //$NON-NLS-1$
		}

        this.maxSize = Long.parseLong(maxSize);

		if (logger.isDebugEnabled()) {
			logger.debug("setMaxSize(String) - end"); //$NON-NLS-1$
		}
    }

    /**
     * Creates a new request wrapper to handle multi-part data using methods adapted from Jason Pell's
     * multipart classes (see class description).
     *
     * @param saveDir        the directory to save off the file
     * @param servletRequest the request containing the multipart
     * @throws java.io.IOException  is thrown if encoding fails.
     */
    public void parse(HttpServletRequest servletRequest, String saveDir)
            throws IOException {
		if (logger.isDebugEnabled()) {
			logger.debug("parse(HttpServletRequest, String) - start"); //$NON-NLS-1$
		}

        DiskFileItemFactory fac = new DiskFileItemFactory();
        // Make sure that the data is written to file
        fac.setSizeThreshold(0);
        if (saveDir != null) {
            fac.setRepository(new File(saveDir));
        }

        // Parse the request
        try {
            ServletFileUpload upload = new ServletFileUpload(fac);
            upload.setSizeMax(maxSize);
            List<?> items = upload.parseRequest(createRequestContext(servletRequest));

            for (Object item1 : items) {
                FileItem item = (FileItem) item1;
                if (log.isDebugEnabled()) log.debug("Found item " + item.getFieldName());
                if (item.isFormField()) {
                    log.debug("Item is a normal form field");
                    List<String> values;
                    if (params.get(item.getFieldName()) != null) {
                        values = params.get(item.getFieldName());
                    } else {
                        values = new ArrayList<String>();
                    }

                    // note: see http://jira.opensymphony.com/browse/WW-633
                    // basically, in some cases the charset may be null, so
                    // we're just going to try to "other" method (no idea if this
                    // will work)
                    String charset = servletRequest.getCharacterEncoding();
                    if (charset != null) {
                        values.add(item.getString(charset));
                    } else {
                        values.add(item.getString());
                    }
                    params.put(item.getFieldName(), values);
                } else {
                    log.debug("Item is a file upload");

                    // Skip file uploads that don't have a file name - meaning that no file was selected.
                    if (item.getName() == null || item.getName().trim().length() < 1) {
                        log.debug("No file has been uploaded for the field: " + item.getFieldName());
                        continue;
                    }

                    List<FileItem> values;
                    if (files.get(item.getFieldName()) != null) {
                        values = files.get(item.getFieldName());
                    } else {
                        values = new ArrayList<FileItem>();
                    }

                    values.add(item);
                    files.put(item.getFieldName(), values);
                }
            }
        } catch (FileUploadException e) {
			logger.error("parse(HttpServletRequest, String)", e); //$NON-NLS-1$

            log.error(e);
            //errors.add(e.getMessage());
        }

		if (logger.isDebugEnabled()) {
			logger.debug("parse(HttpServletRequest, String) - end"); //$NON-NLS-1$
		}
    }

    /* (non-Javadoc)
     * @see org.apache.struts2.dispatcher.multipart.MultiPartRequest#getFileParameterNames()
     */
    public Enumeration<String> getFileParameterNames() {
		if (logger.isDebugEnabled()) {
			logger.debug("getFileParameterNames() - start"); //$NON-NLS-1$
		}

		Enumeration<String> returnEnumeration = Collections.enumeration(files.keySet());
		if (logger.isDebugEnabled()) {
			logger.debug("getFileParameterNames() - end"); //$NON-NLS-1$
		}
        return returnEnumeration;
    }

    /* (non-Javadoc)
     * @see org.apache.struts2.dispatcher.multipart.MultiPartRequest#getContentType(java.lang.String)
     */
    public String[] getContentType(String fieldName) {
		if (logger.isDebugEnabled()) {
			logger.debug("getContentType(String) - start"); //$NON-NLS-1$
		}

        List<?> items = (List<?>) files.get(fieldName);

        if (items == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("getContentType(String) - end"); //$NON-NLS-1$
			}
            return null;
        }

        List<String> contentTypes = new ArrayList<String>(items.size());
        for (int i = 0; i < items.size(); i++) {
            FileItem fileItem = (FileItem) items.get(i);
            contentTypes.add(fileItem.getContentType());
        }

		String[] returnStringArray = (String[]) contentTypes.toArray(new String[contentTypes.size()]);
		if (logger.isDebugEnabled()) {
			logger.debug("getContentType(String) - end"); //$NON-NLS-1$
		}
        return returnStringArray;
    }

    /* (non-Javadoc)
     * @see org.apache.struts2.dispatcher.multipart.MultiPartRequest#getFile(java.lang.String)
     */
    public File[] getFile(String fieldName) {
		if (logger.isDebugEnabled()) {
			logger.debug("getFile(String) - start"); //$NON-NLS-1$
		}

        List<?> items = (List<?>) files.get(fieldName);

        if (items == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("getFile(String) - end"); //$NON-NLS-1$
			}
            return null;
        }

        List<File> fileList = new ArrayList<File>(items.size());
        for (int i = 0; i < items.size(); i++) {
            DiskFileItem fileItem = (DiskFileItem) items.get(i);
            fileList.add(fileItem.getStoreLocation());
        }

		File[] returnFileArray = (File[]) fileList.toArray(new File[fileList.size()]);
		if (logger.isDebugEnabled()) {
			logger.debug("getFile(String) - end"); //$NON-NLS-1$
		}
        return returnFileArray;
    }

    /* (non-Javadoc)
     * @see org.apache.struts2.dispatcher.multipart.MultiPartRequest#getFileNames(java.lang.String)
     */
    public String[] getFileNames(String fieldName) {
		if (logger.isDebugEnabled()) {
			logger.debug("getFileNames(String) - start"); //$NON-NLS-1$
		}

        List<FileItem> items = files.get(fieldName);

        if (items == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("getFileNames(String) - end"); //$NON-NLS-1$
			}
            return null;
        }

        List<String> fileNames = new ArrayList<String>(items.size());
        for (int i = 0; i < items.size(); i++) {
            DiskFileItem fileItem = (DiskFileItem) items.get(i);
            fileNames.add(getCanonicalName(fileItem.getName()));
        }

		String[] returnStringArray = (String[]) fileNames.toArray(new String[fileNames.size()]);
		if (logger.isDebugEnabled()) {
			logger.debug("getFileNames(String) - end"); //$NON-NLS-1$
		}
        return returnStringArray;
    }

    /* (non-Javadoc)
     * @see org.apache.struts2.dispatcher.multipart.MultiPartRequest#getFilesystemName(java.lang.String)
     */
    public String[] getFilesystemName(String fieldName) {
		if (logger.isDebugEnabled()) {
			logger.debug("getFilesystemName(String) - start"); //$NON-NLS-1$
		}

        List<?> items = (List<?>) files.get(fieldName);

        if (items == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("getFilesystemName(String) - end"); //$NON-NLS-1$
			}
            return null;
        }

        List<String> fileNames = new ArrayList<String>(items.size());
        for (int i = 0; i < items.size(); i++) {
            DiskFileItem fileItem = (DiskFileItem) items.get(i);
            fileNames.add(fileItem.getStoreLocation().getName());
        }

		String[] returnStringArray = (String[]) fileNames.toArray(new String[fileNames.size()]);
		if (logger.isDebugEnabled()) {
			logger.debug("getFilesystemName(String) - end"); //$NON-NLS-1$
		}
        return returnStringArray;
    }

    /* (non-Javadoc)
     * @see org.apache.struts2.dispatcher.multipart.MultiPartRequest#getParameter(java.lang.String)
     */
    public String getParameter(String name) {
		if (logger.isDebugEnabled()) {
			logger.debug("getParameter(String) - start"); //$NON-NLS-1$
		}

        List<?> v = (List<?>) params.get(name);
        if (v != null && v.size() > 0) {
			String returnString = (String) v.get(0);
			if (logger.isDebugEnabled()) {
				logger.debug("getParameter(String) - end"); //$NON-NLS-1$
			}
            return returnString;
        }

		if (logger.isDebugEnabled()) {
			logger.debug("getParameter(String) - end"); //$NON-NLS-1$
		}
        return null;
    }

    /* (non-Javadoc)
     * @see org.apache.struts2.dispatcher.multipart.MultiPartRequest#getParameterNames()
     */
    public Enumeration<String> getParameterNames() {
		if (logger.isDebugEnabled()) {
			logger.debug("getParameterNames() - start"); //$NON-NLS-1$
		}

		Enumeration<String> returnEnumeration = Collections.enumeration(params.keySet());
		if (logger.isDebugEnabled()) {
			logger.debug("getParameterNames() - end"); //$NON-NLS-1$
		}
        return returnEnumeration;
    }

    /* (non-Javadoc)
     * @see org.apache.struts2.dispatcher.multipart.MultiPartRequest#getParameterValues(java.lang.String)
     */
    public String[] getParameterValues(String name) {
		if (logger.isDebugEnabled()) {
			logger.debug("getParameterValues(String) - start"); //$NON-NLS-1$
		}

        List<String> v = params.get(name);
        if (v != null && v.size() > 0) {
			String[] returnStringArray = (String[]) v.toArray(new String[v.size()]);
			if (logger.isDebugEnabled()) {
				logger.debug("getParameterValues(String) - end"); //$NON-NLS-1$
			}
            return returnStringArray;
        }

		if (logger.isDebugEnabled()) {
			logger.debug("getParameterValues(String) - end"); //$NON-NLS-1$
		}
        return null;
    }

    /* (non-Javadoc)
     * @see org.apache.struts2.dispatcher.multipart.MultiPartRequest#getErrors()
     */
    public List<?> getErrors() {
        return errors;
    }

    /**
     * Returns the canonical name of the given file.
     *
     * @param filename  the given file
     * @return the canonical name of the given file
     */
    private String getCanonicalName(String filename) {
		if (logger.isDebugEnabled()) {
			logger.debug("getCanonicalName(String) - start"); //$NON-NLS-1$
		}

        int forwardSlash = filename.lastIndexOf("/");
        int backwardSlash = filename.lastIndexOf("\\");
        if (forwardSlash != -1 && forwardSlash > backwardSlash) {
            filename = filename.substring(forwardSlash + 1, filename.length());
        } else if (backwardSlash != -1 && backwardSlash >= forwardSlash) {
            filename = filename.substring(backwardSlash + 1, filename.length());
        }

		if (logger.isDebugEnabled()) {
			logger.debug("getCanonicalName(String) - end"); //$NON-NLS-1$
		}
        return filename;
    }

    /**
     * Creates a RequestContext needed by Jakarta Commons Upload.
     *
     * @param req  the request.
     * @return a new request context.
     */
    private RequestContext createRequestContext(final HttpServletRequest req) {
		if (logger.isDebugEnabled()) {
			logger.debug("createRequestContext(HttpServletRequest) - start"); //$NON-NLS-1$
		}

		RequestContext returnRequestContext = new RequestContext() {
			public String getCharacterEncoding() {
				if (logger.isDebugEnabled()) {
					logger.debug("$RequestContext.getCharacterEncoding() - start"); //$NON-NLS-1$
				}

				String returnString = req.getCharacterEncoding();
				if (logger.isDebugEnabled()) {
					logger.debug("$RequestContext.getCharacterEncoding() - end"); //$NON-NLS-1$
				}
				return returnString;
			}

			public String getContentType() {
				if (logger.isDebugEnabled()) {
					logger.debug("$RequestContext.getContentType() - start"); //$NON-NLS-1$
				}

				String returnString = req.getContentType();
				if (logger.isDebugEnabled()) {
					logger.debug("$RequestContext.getContentType() - end"); //$NON-NLS-1$
				}
				return returnString;
			}

			public int getContentLength() {
				if (logger.isDebugEnabled()) {
					logger.debug("$RequestContext.getContentLength() - start"); //$NON-NLS-1$
				}

				int returnint = req.getContentLength();
				if (logger.isDebugEnabled()) {
					logger.debug("$RequestContext.getContentLength() - end"); //$NON-NLS-1$
				}
				return returnint;
			}

			public InputStream getInputStream() throws IOException {
				if (logger.isDebugEnabled()) {
					logger.debug("$RequestContext.getInputStream() - start"); //$NON-NLS-1$
				}

				InputStream returnInputStream = req.getInputStream();
				if (logger.isDebugEnabled()) {
					logger.debug("$RequestContext.getInputStream() - end"); //$NON-NLS-1$
				}
				return returnInputStream;
			}
		};
		if (logger.isDebugEnabled()) {
			logger.debug("createRequestContext(HttpServletRequest) - end"); //$NON-NLS-1$
		}
        return returnRequestContext;
    }
}

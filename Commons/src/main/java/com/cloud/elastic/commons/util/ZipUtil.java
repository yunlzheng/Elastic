package com.cloud.elastic.commons.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipUtil {

	static final int BUFFER = 512;

	/**
	 * @param baseDir 索要压缩的目录名
	 * @param objFileName 压缩后的文件名
	 * */
	public void createZip(String baseDir, String objFileName) throws Exception {

		File folderObject = new File(baseDir);
		if (folderObject.exists()) {
			List<File> fileList = getSubFiles(new File(baseDir));

			// 压缩文件名
			ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(objFileName));
			ZipEntry ze = null;
			byte[] buf = new byte[1024];
			int readLen = 0;
			for (int i = 0; i < fileList.size(); i++) {
				File f = (File) fileList.get(i);
				System.out.println("Adding: " + f.getPath() +">>>>>>>>" +f.getName());
				/*
				 * 创建一个ZipEntry，并设置Name和其它的一些属性
				 * 注意，该ZipEntry是包含目录结构的，此功能是由getAbsFileName(String,string)完成的
				 */
				ze = new ZipEntry(getAbsFileName(baseDir, f));
				ze.setSize(f.length());
				ze.setTime(f.lastModified());
				// 将ZipEntry加到zos中，再写入实际的文件内容
				zos.putNextEntry(ze);
				InputStream is = new BufferedInputStream(new FileInputStream(f));
				while ((readLen = is.read(buf, 0, 1024)) != -1) {
					zos.write(buf, 0, readLen);
				}
				is.close();
				System.out.println("done...");
			}
			zos.close();
		} else {
			throw new Exception("this folder isnot exist!");
		}
	}
	
    @SuppressWarnings("resource")
	public static void unZip(String zipfile, String destDir) {
    	 
        destDir = destDir.endsWith( "//" ) ? destDir : destDir + "//" ;
        byte b[] = new byte [1024];
        int length;
  
        ZipFile zipFile;
        try {
            zipFile = new ZipFile( new File(zipfile));
            Enumeration<?> enumeration = zipFile.entries();
            ZipEntry zipEntry = null ;
  
            while (enumeration.hasMoreElements()) {
               zipEntry = (ZipEntry) enumeration.nextElement();
               File loadFile = new File(destDir + zipEntry.getName());
  
               if (zipEntry.isDirectory()) {
                   // 这段都可以不要，因为每次都貌似从最底层开始遍历的
                   loadFile.mkdirs();
               } else {
                   if (!loadFile.getParentFile().exists())
                      loadFile.getParentFile().mkdirs();
  
                   OutputStream outputStream = new FileOutputStream(loadFile);
                   InputStream inputStream = zipFile.getInputStream(zipEntry);
  
                   while ((length = inputStream.read(b)) > 0)
                      outputStream.write(b, 0, length);
  
               }
            }
            System. out .println( " 文件解压成功 " );
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
  
     }

	/**
	 * 取得指定目录以及其各级子目录下的所有文件的列表，注意，返回的列表中不含目录.
	 * 
	 * @param baseDir
	 *            File 指定的目录
	 * @return 包含java.io.File的List
	 */
	private List<File> getSubFiles(File baseDir) {
		List<File> fileList = new ArrayList<File>();
		File[] tmp = baseDir.listFiles();
		for (int i = 0; i < tmp.length; i++) {
			// 如果是文件，直接加入fileList
			if (tmp[i].isFile()) {
				fileList.add(tmp[i]);
			}
			// 如果是目录，递归调用本方法，并将结果集加入fileList
			if (tmp[i].isDirectory()) {
				fileList.addAll(getSubFiles(tmp[i]));
			}
		}
		return fileList;
	}

	private String getAbsFileName(String baseDir, File realFileName) {

		File real = realFileName;
		File base = new File(baseDir);
		String ret = real.getName();
		while (true) {
			real = real.getParentFile();
			if (real == null)
				break;
			if (real.equals(base))
				break;
			else {
				ret = real.getName() + "/" + ret;
			}
		}
		return ret;

	}

	public static void main(String[] args) {

		ZipUtil util = new ZipUtil();
		File sources = new File("C:\\Users\\云龙\\cloud.tmp");
		File target = new File("C:\\Users\\云龙\\apache-tomcat.zip");
		try {
			util.createZip(sources.getAbsolutePath(), target.getAbsolutePath());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

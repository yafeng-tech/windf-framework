package com.windf.core.util;

import com.windf.core.Constant;
import com.windf.core.exception.CodeException;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FileUtil {
	private static String classPath = null;

	/**
	 * 获取类文件路径 e: 项目路径/WEB-INF/classes/
	 *
	 * @return
	 */
	public static String getClassPath() {
		if (classPath == null) {
			classPath = new File(FileUtil.class.getClassLoader().getResource("").getPath()).getPath() + "/";
		}
		return classPath;
	}

	/**
	 * 获取项目的部署路径
	 */
	public static String getWebappPath() {
		String classPath = getClassPath();
		return classPath.substring(0, classPath.lastIndexOf("WEB-INF"));
	}

	/**
	 * 获得WEB-INF文件位置
	 * @return
	 */
	public static String getWEBINFPath() {
		return getWebappPath() + "WEB-INF/";
	}

	/**
	 * 获得文件的真实路径
	 *
	 * @param filePath
	 * @return
	 */
	public static String getFileRealPath(String filePath) {
		String result = filePath;

		String realPath = getWebappPath();
		if (!filePath.startsWith(realPath)) {
			result = realPath + filePath;
		}

		return result;
	}

	/**
	 * 获取web文件的路径,如果文件时绝对路径的话
	 * 如果不是绝对路径，直接返回原始路径
	 * @param filePath
	 * @return
	 */
	public static String getWebPath(String filePath) {
		if (filePath.indexOf(FileUtil.getWebappPath()) != -1) {
			return filePath.substring(FileUtil.getWebappPath().length() - 1).replaceAll("\\\\", "/");
		}
		return filePath;
	}

	/**
	 * 获得文件,根据是否已webapp路径开头，判断是否是相对路径
	 * @param filePath
	 * @return
	 */
	public static File getFile(String filePath) {
		return getFile(filePath, false);
	}

	/**
	 * 获得文件
	 * @param filePath
	 * @return
	 */
	public static File getFile(String filePath, boolean isRealPath) {
		String realPath = filePath;
		if (!isRealPath) {
			realPath = FileUtil.getFileRealPath(filePath);
		}
		File file = new File(realPath);
		return file;
	}

	/**
	 * 复制文件夹
	 * 如果目标目录不存在创建 复制文件、递归复制文件夹
	 * @param source 源目录
	 * @param target 目标目录
	 * @return 内容列表
	 */
	public static List<File> copyFolder(String source, String target) {

		List<File> list = new LinkedList<File>();

		/*
		 * 获取目标目录，如果目标目录不存在创建
		 */
		File targetFolder = new File(target);
		if (!targetFolder.exists()) {
			targetFolder.mkdirs();
		}

		/*
		 * 遍历源目录，复制文件、递归复制文件夹
		 */
		File sourceFolder = new File(source);
		File[] sourceFiles = sourceFolder.listFiles();
		if (sourceFiles != null) {
			for (File file : sourceFiles) {
				// 不遍历隐藏文件
				if (file.getName().startsWith(".")) {
					continue;
				}

				if (file.isFile()) {
					// 复制文件
					String targetFilePath = target + "/" + file.getName();
					copyFile(file.getPath(), targetFilePath);

					// 添加复制的文件到文件列表
					File targetFile = new File(targetFilePath);
					list.add(targetFile);
				} else if (file.isDirectory()) {
					String targetDirectoryPath = target + "/" + file.getName();
					List<File> tempList = copyFolder(file.getPath(), targetDirectoryPath);

					/*
					 * 添加复制的文件夹到文件列表
					 */
					if (tempList != null && tempList.size() > 0) {
						File targetDirectory = new File(targetDirectoryPath);
						list.add(targetDirectory);
					}

					/*
					 * 添加复制的所有文件到文件列表
					 */
					list.addAll(tempList);

				}
			}
		}

		return list;
	}

	/**
	 * 复制文件
	 *
	 * @param source
	 * @param target
	 */
	public static void copyFile(String source, String target) throws CodeException {
		/*
		 * 复制文件
		 */
		BufferedReader bufferedReader = null;
		PrintStream printStream = null;
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(source)));
			printStream = new PrintStream(new FileOutputStream(target));
			String s = null;
			while ((s = bufferedReader.readLine()) != null) {
				printStream.println(s);
				printStream.flush();
			}
		} catch (IOException e) {
			throw new CodeException(e);
		} finally {
			try {
				if (bufferedReader != null)
					bufferedReader.close();
				if (printStream != null)
					printStream.close();
			} catch (IOException e) {
				throw new CodeException(e);
			}
		}
	}

	/**
	 * 给文件重命名
	 *
	 * @param file
	 * @param newName
	 * @return
	 */
	public static void rename(File file, String newName) {
		String directory = file.getParentFile().getAbsolutePath();
		file.renameTo(new File(directory + "/" + newName));
	}

	/**
	 * 获取路径中的后缀名
	 * @param fileName
	 * @return
	 */
	public static String getSimpleName(String fileName) {
		// 是否是文件全名
		int lastSeparator = fileName.indexOf("/");
		if (lastSeparator > -1) {
			fileName = fileName.substring(lastSeparator);
		}

		// 去掉后缀名
		int lastPoint = fileName.lastIndexOf(".");
		if (lastPoint > -1) {
			fileName = fileName.substring(0, lastPoint);
		}
		return fileName;
	}

	/**
	 * 获取路径中的后缀名
	 * @param fileName
	 * @return
	 */
	public static String getSuffix(String fileName) {
		int lastPointIndex = fileName.lastIndexOf(".");
		String prefix = null;
		if (lastPointIndex > 0) {
			prefix = fileName.substring(fileName.lastIndexOf(".")+1);
		}
		return prefix;
	}

	/**
	 * 如果文件不存在，创建
	 * @param files
	 */
	public static void createFile(File... files) {
		for (File file : files) {
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				try {
					file.createNewFile();
				} catch (IOException e) {
					throw new CodeException(e);
				}
			}
		}
	}


	/**
	 * 如果目录不存在，创建
	 * 如果文件不存在，不会创建
	 * @param files
	 */
	public static void createPath(File... files) {
		for (File file : files) {
			if (!file.exists()) {
				file.getParentFile().mkdirs();
			}
		}
	}

	/**
     * 获取文件路径下所有文件名称
     * @param path
     * @return
     */
    public static List<String> getFileName(String path){
        File file = new File(path);
        return Arrays.asList(file.list());
    }

    /**
     * 获取文件夹下所有文件
     * @param path
     * @param fileName
     */
    public static void getAllFileName(String path,List<String> fileName){
        File file = new File(path);
        File [] files = file.listFiles();
        String [] names = file.list();
        if(names != null)
            fileName.addAll(Arrays.asList(names));
        for(File f : files) {
            if(f.isDirectory()) {
                getAllFileName(f.getAbsolutePath(),fileName);
            }
        }
    }

    /**
     * 读物文件每一行
     * @param filePath
     * @return
     */
    public static List<String> readLine(String filePath) throws FileNotFoundException {
		// 获取文件
        File file = FileUtil.getFile(filePath);

        // 文件转换
        return readLine(file);
    }

	/**
	 * 读取文件，转换为一行一行的文件
	 * @param file
	 * @return
	 */
	public static List<String> readLine(File file) throws FileNotFoundException {
        List<String> result = new ArrayList<>();

        BufferedReader reader = null;
        String lineContent = null;
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(file), Constant.DEFAULT_ENCODING);
            reader = new BufferedReader(isr);

            while ((lineContent = reader.readLine()) != null) {
                result.add(lineContent);
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
			throw new CodeException(e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					throw new CodeException(e);
				}
			}
		}

		return result;
	}

}

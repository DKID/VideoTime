package VideoTime;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;
import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;

public class Videotime {

	private static long time;
	private static String count = "";

	public static void main(String[] args) throws IOException {

		// 输入需要遍历的路径
		String n = JOptionPane.showInputDialog("请输入路径:直接复制资源管理器路径即可");
		if (n != null) {
			File file = new File(n);
			listAllFile(file);
		} else {
			System.out.println("路径有问题");
		}
		System.out
				.println("所有视频的总长度为:" + time / 3600000 + "时" + (time % 3600000)
						/ 60000 + "分" + (time % 60000) / 1000 + "秒！");
	}

	public static void listAllFile(File f) {
		if (f.isDirectory()) {
			File[] fs = f.listFiles();
			System.out.println(count + f.getName());
			count = count + "----";
			for (int i = 0; i < fs.length; i++) {

				try {
					listAllFile(fs[i]);
				} catch (Exception e) {
				}
			}
			count = truncateHeadString(count, 4);
		} else
			findVideo(f);
	}

	public static void findVideo(File f) {
		String name = f.toString();
		if (name.endsWith(".mp4") | name.endsWith(".avi")
				| name.endsWith(".rmvb") | name.endsWith(".rm")) {
			System.out.println(count + f.getName());
			countViewTime(f);
		}
	}

	private static void countViewTime(File source) {
		Encoder rencoder = new Encoder();
		try {
			MultimediaInfo m = rencoder.getInfo(source);
			long ls = m.getDuration();
			System.out.println("此视频时长为:" + ls / 60000 + "分" + (ls % 60000)
					/ 1000 + "秒！");
			time = time + ls;
		} catch (Exception e) {
			System.out.println("读取失败");
			e.printStackTrace();
		}
	}

	// 去除字符串开头几位
	public static String truncateHeadString(String origin, int count) {
		if (origin == null || origin.length() < count) {
			return null;
		}
		char[] arr = origin.toCharArray();
		char[] ret = new char[arr.length - count];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = arr[i + count];
		}
		return String.copyValueOf(ret);
	}

}
package saiwei.com.river.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 图片工具类
 *
 * @author xiaobin.wu
 *
 */
public class BitmapTool {

	/**
	 * 上传图片的size最大500KB
	 */
	public static final double UPLOAD_PIC_MAX_SIZE = 500.00;

	/**
	 * 图片缩放到指定大小
	 *
	 * @param bitmap
	 * @param maxSize
	 *            单位KB
	 */
	public static Bitmap zoomToSize(Bitmap bitmap, double maxSize) {
		if (bitmap == null) {
			return null;
		}
		try {
			double mid = getBitmapRealSize(bitmap);
			// 判断bitmap占用空间是否大于允许最大空间 如果大于则压缩 小于则不压缩
			if (mid > maxSize) {
				// 获取bitmap大小 是允许最大大小的多少倍
				double i = mid / maxSize;
				// 开始压缩 此处用到平方根 将宽带和高度压缩掉对应的平方根倍
				// （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
				bitmap = zoomImage(bitmap, bitmap.getWidth() / Math.sqrt(i), bitmap.getHeight() / Math.sqrt(i));
			}
			return bitmap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取bitmap实际大小，单位KB
	 *
	 * @return
	 */
	public static double getBitmapRealSize(Bitmap bitmap) {
		// 将bitmap放至数组中，意在bitmap的大小（与实际读取的原文件要大）
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		byte[] b = baos.toByteArray();
		// 将字节换成KB
		return b.length / 1024;
	}

	/**
	 * 图片的缩放方法
	 *
	 * @param bgimage
	 *            ：源图片资源
	 * @param newWidth
	 *            ：缩放后宽度
	 * @param newHeight
	 *            ：缩放后高度
	 * @return
	 */
	public static Bitmap zoomImage(Bitmap bgimage, double newWidth, double newHeight) {
		if (bgimage == null) {
			return null;
		}

		try {
			// 获取这个图片的宽和高
			float width = bgimage.getWidth();
			float height = bgimage.getHeight();
			// 创建操作图片用的matrix对象
			Matrix matrix = new Matrix();
			// 计算宽高缩放率
			float scaleWidth = ((float) newWidth) / width;
			float scaleHeight = ((float) newHeight) / height;
			// 缩放图片动作
			matrix.postScale(scaleWidth, scaleHeight);
			Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width, (int) height, matrix, true);
			return bitmap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 保存到文件
	 *
	 * @param bitmap
	 * @param path
	 */
	public static void saveImage(Bitmap bitmap, String path) {
		if (bitmap == null) {
			return;
		}
		try {
			File file = new File(path);
			FileOutputStream out = new FileOutputStream(file);
			if (bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)) {
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 通过图片uri获得bitmap
	 *
	 *            true 上传（原图） false 预览（缩略图）
	 * @return
	 */
	public static Bitmap getBitmapFormUri(Context mContext, Uri mImageUri, boolean isUpload) {

		InputStream is = null;
		try {
			is = mContext.getContentResolver().openInputStream(mImageUri);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		if (is == null) {
			return null;
		}

		Bitmap bitmap = null;
		try {
			if (isUpload) {
				// 需要上传时就获取原始图片
				bitmap = BitmapFactory.decodeStream(is);
			} else {
				// 获取原始图片的宽度
				BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
				bitmapOptions.inJustDecodeBounds = true;
				BitmapFactory.decodeStream(is, null, bitmapOptions);
				int oldWidth = bitmapOptions.outWidth;

				// 设置新图片的宽度
				int newWidth = 200;
				int widthScale = oldWidth / newWidth;
				bitmapOptions.inSampleSize = widthScale;
				bitmapOptions.inJustDecodeBounds = false;
				bitmapOptions.inPurgeable = true;
				// 要重新获取一次
				is = mContext.getContentResolver().openInputStream(mImageUri);
				// 获取设置后的图片
				bitmap = BitmapFactory.decodeStream(is, null, bitmapOptions);
			}
		} catch (FileNotFoundException e) {

		} catch (OutOfMemoryError e) {

		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bitmap;
	}
}
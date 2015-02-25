package com.rekoe.crawler.bean;

import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import com.rekoe.crawler.core.constants.Constant;


/**
 * 图片处理值对象
 */
	public class ImageSettingBean{
	/** 图片格式对照MAP*/
	public final static Map<String,String> FORMAT_TYPE_MAP = new HashMap<String,String>();
	static{
		FORMAT_TYPE_MAP.put("JPEG", "jpg");
		FORMAT_TYPE_MAP.put("PNG", "png");
		FORMAT_TYPE_MAP.put("GIF", "gif");
	}
	
	
	/**演示图片地址*/
	private String exampleImagePath;
	/** 缩放方式：true为按比例,false为按分辨率 */
	private String resizeValue = Constant.NO;
	/** 按分辨率设置,宽度 */
	private int resizeByPixelWidth;
	/** 按分辨率设置,高度 */
	private int resizeByPixelHeight;
	/** 是否等比例缩放 */
	private String equalScaleValue = Constant.YES;
	/** 按百分比缩放值 */
	private int resizeByScaleValue;
	
	
	/** 是否改变保存格式 */
	private String saveFormatValue = Constant.YES;
	/** 图片格式*/
	private String saveFormatType;
	/** 图片质量 */
	private int saveImageQualityValue;
	/** 处理后图片保存路径 */
	private String savePath;
	
	
	
	/** 是否旋转*/
	private String cirCehckValue = Constant.NO;
	/** 是否水平旋转*/
	private String cirCehckTypeHorizontalValue = Constant.NO;
	/** 是否垂直旋转*/
	private String cirCehckTypeVerticalValue = Constant.NO;
	/** 旋转值*/
	private int cirValue;
	
	/** 是否裁剪*/
	private String cutCehckValue = Constant.NO;
	/**是否自动裁剪*/
	private String cutCehckTypeValue = Constant.YES;
	/**裁剪宽度*/
	private int cutWidth;
	/**裁剪高度*/
	private int cutHeight;
	/**剪切框X坐标*/
	private int cutPanelX = 0;
	/**剪切框Y坐标*/
	private int cutPanelY = 0;
	
	
	/** 是否使用水印图片*/
	private String waterImageCehckValue = Constant.NO;
	/** 水印图片路径*/
	private String waterImagePath;
	/** 是否取消背景*/
	private String waterImageCancelBgValue = Constant.NO;
	/** 是否设置透明度*/
	private String waterImageDiaphaneityCehckValue = Constant.NO;
	/**透明度值*/
	private int waterImageDiaphaneityValue;
	/**水印图片X坐标*/
	private int waterImageX = 0;
	/**水印图片Y坐标*/
	private int waterImageY = 0;
	
	
	/** 是否使用水印文字*/
	private String waterTextCehckValue = Constant.NO;
	/** 水印文字字体*/
	private Font waterTextFontValue;
	/** 水印文字颜色*/
	private Color waterTextColorValue;
	/** 水印文字*/
	private String[] waterText = new String[]{};
	/** 是否有背景颜色*/
	private String waterTextBgColorCehckValue = Constant.NO;
	/** 水印文字背景颜色*/
	private Color waterTextBgColor;
	/** 是否有边框*/
	private String waterTextBorderCehckValue = Constant.NO;
	/** 水印文字边框*/
	private Color waterTextBorderColor;
	/** 是否添加日期*/
	private String waterTextDateCehckValue = Constant.NO;
	/** 是否添加时间*/
	private String waterTextTimeCehckValue = Constant.NO;
	/** 是否使用透明度*/
	private String waterTextDiaphaneityCehckValue = Constant.NO;
	/** 水印文字透明度*/
	private int waterTextDiaphaneityValue;
	/**水印文字X坐标*/
	private int waterTextX = 0;
	/**水印文字Y坐标*/
	private int waterTextY = 0;
	/**水印日期*/
	private String waterTextDate = "";
	
	/** 是否设置亮度*/
	private String imageLightenessCheckValue = Constant.NO;
	/** 亮度值*/
    private int imageLighteness;
	/** 是否设置对比度*/
	private String imageContrastValue = Constant.NO;
	/** 对比度值*/
    private int imageContrast;
    
    /** 是否黑白*/
    private String blackAndwhiteCheckValue = Constant.NO;
    /** 是否锐化*/
	private String sharpCheckValue = Constant.NO;
	/** 是否模糊*/
    private String blurCheckValue = Constant.NO;
	/** 是否光滑*/
	private String slickCheckValue = Constant.NO;
	/** 是否底片*/
	private String negativeCheckValue = Constant.NO;
	/** 是否浮雕*/
	private String raisedCheckValue = Constant.NO;
	
	
	
	public String getExampleImagePath() {
		return exampleImagePath;
	}
	public void setExampleImagePath(String exampleImagePath) {
		this.exampleImagePath = exampleImagePath;
	}
	public String getResizeValue() {
		return resizeValue;
	}
	public void setResizeValue(String resizeValue) {
		this.resizeValue = resizeValue;
	}
	public int getResizeByPixelWidth() {
		return resizeByPixelWidth;
	}
	public void setResizeByPixelWidth(int resizeByPixelWidth) {
		this.resizeByPixelWidth = resizeByPixelWidth;
	}
	public int getResizeByPixelHeight() {
		return resizeByPixelHeight;
	}
	public void setResizeByPixelHeight(int resizeByPixelHeight) {
		this.resizeByPixelHeight = resizeByPixelHeight;
	}
	public String getEqualScaleValue() {
		return equalScaleValue;
	}
	public void setEqualScaleValue(String equalScaleValue) {
		this.equalScaleValue = equalScaleValue;
	}
	public int getResizeByScaleValue() {
		return resizeByScaleValue;
	}
	public void setResizeByScaleValue(int resizeByScaleValue) {
		this.resizeByScaleValue = resizeByScaleValue;
	}
	public String getSaveFormatValue() {
		return saveFormatValue;
	}
	
	public String getSaveFormatType() {
		return saveFormatType;
	}
	public void setSaveFormatType(String saveFormatType) {
		this.saveFormatType = saveFormatType;
	}
	public void setSaveFormatValue(String saveFormatValue) {
		this.saveFormatValue = saveFormatValue;
	}
	public int getSaveImageQualityValue() {
		return saveImageQualityValue;
	}
	public void setSaveImageQualityValue(int saveImageQualityValue) {
		this.saveImageQualityValue = saveImageQualityValue;
	}
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	public String getCirCehckValue() {
		return cirCehckValue;
	}
	public void setCirCehckValue(String cirCehckValue) {
		this.cirCehckValue = cirCehckValue;
	}
	
	public String getCirCehckTypeHorizontalValue() {
		return cirCehckTypeHorizontalValue;
	}
	public void setCirCehckTypeHorizontalValue(String cirCehckTypeHorizontalValue) {
		this.cirCehckTypeHorizontalValue = cirCehckTypeHorizontalValue;
	}
	public String getCirCehckTypeVerticalValue() {
		return cirCehckTypeVerticalValue;
	}
	public void setCirCehckTypeVerticalValue(String cirCehckTypeVerticalValue) {
		this.cirCehckTypeVerticalValue = cirCehckTypeVerticalValue;
	}
	
	public int getCirValue() {
		return cirValue;
	}
	public void setCirValue(int cirValue) {
		this.cirValue = cirValue;
	}
	public String getCutCehckValue() {
		return cutCehckValue;
	}
	public void setCutCehckValue(String cutCehckValue) {
		this.cutCehckValue = cutCehckValue;
	}
	public String getCutCehckTypeValue() {
		return cutCehckTypeValue;
	}
	public void setCutCehckTypeValue(String cutCehckTypeValue) {
		this.cutCehckTypeValue = cutCehckTypeValue;
	}
	public int getCutWidth() {
		return cutWidth;
	}
	public void setCutWidth(int cutWidth) {
		this.cutWidth = cutWidth;
	}
	public int getCutHeight() {
		return cutHeight;
	}
	public void setCutHeight(int cutHeight) {
		this.cutHeight = cutHeight;
	}
	
	public int getCutPanelX() {
		return cutPanelX;
	}
	public void setCutPanelX(int cutPanelX) {
		this.cutPanelX = cutPanelX;
	}
	public int getCutPanelY() {
		return cutPanelY;
	}
	public void setCutPanelY(int cutPanelY) {
		this.cutPanelY = cutPanelY;
	}
	public String getWaterImageCehckValue() {
		return waterImageCehckValue;
	}
	public void setWaterImageCehckValue(String waterImageCehckValue) {
		this.waterImageCehckValue = waterImageCehckValue;
	}
	public String getWaterImagePath() {
		return waterImagePath;
	}
	public void setWaterImagePath(String waterImagePath) {
		this.waterImagePath = waterImagePath;
	}
	public String getWaterImageCancelBgValue() {
		return waterImageCancelBgValue;
	}
	public void setWaterImageCancelBgValue(String waterImageCancelBgValue) {
		this.waterImageCancelBgValue = waterImageCancelBgValue;
	}
	public String getWaterImageDiaphaneityCehckValue() {
		return waterImageDiaphaneityCehckValue;
	}
	public void setWaterImageDiaphaneityCehckValue(
			String waterImageDiaphaneityCehckValue) {
		this.waterImageDiaphaneityCehckValue = waterImageDiaphaneityCehckValue;
	}
	public int getWaterImageDiaphaneityValue() {
		return waterImageDiaphaneityValue;
	}
	public void setWaterImageDiaphaneityValue(int waterImageDiaphaneityValue) {
		this.waterImageDiaphaneityValue = waterImageDiaphaneityValue;
	}
	
	public int getWaterImageX() {
		return waterImageX;
	}
	public void setWaterImageX(int waterImageX) {
		this.waterImageX = waterImageX;
	}
	public int getWaterImageY() {
		return waterImageY;
	}
	public void setWaterImageY(int waterImageY) {
		this.waterImageY = waterImageY;
	}
	public String getWaterTextCehckValue() {
		return waterTextCehckValue;
	}
	public void setWaterTextCehckValue(String waterTextCehckValue) {
		this.waterTextCehckValue = waterTextCehckValue;
	}
	public Font getWaterTextFontValue() {
		return waterTextFontValue;
	}
	public void setWaterTextFontValue(Font waterTextFontValue) {
		this.waterTextFontValue = waterTextFontValue;
	}
	public Color getWaterTextColorValue() {
		return waterTextColorValue;
	}
	public void setWaterTextColorValue(Color waterTextColorValue) {
		this.waterTextColorValue = waterTextColorValue;
	}
	public String[] getWaterText() {
		return waterText;
	}
	public void setWaterText(String[] waterText) {
		this.waterText = waterText;
	}
	public String getWaterTextBgColorCehckValue() {
		return waterTextBgColorCehckValue;
	}
	public void setWaterTextBgColorCehckValue(String waterTextBgColorCehckValue) {
		this.waterTextBgColorCehckValue = waterTextBgColorCehckValue;
	}
	public Color getWaterTextBgColor() {
		return waterTextBgColor;
	}
	public void setWaterTextBgColor(Color waterTextBgColor) {
		this.waterTextBgColor = waterTextBgColor;
	}
	public String getWaterTextBorderCehckValue() {
		return waterTextBorderCehckValue;
	}
	public void setWaterTextBorderCehckValue(String waterTextBorderCehckValue) {
		this.waterTextBorderCehckValue = waterTextBorderCehckValue;
	}
	public Color getWaterTextBorderColor() {
		return waterTextBorderColor;
	}
	public void setWaterTextBorderColor(Color waterTextBorderColor) {
		this.waterTextBorderColor = waterTextBorderColor;
	}
	public String getWaterTextDateCehckValue() {
		return waterTextDateCehckValue;
	}
	public void setWaterTextDateCehckValue(String waterTextDateCehckValue) {
		this.waterTextDateCehckValue = waterTextDateCehckValue;
	}
	public String getWaterTextTimeCehckValue() {
		return waterTextTimeCehckValue;
	}
	public void setWaterTextTimeCehckValue(String waterTextTimeCehckValue) {
		this.waterTextTimeCehckValue = waterTextTimeCehckValue;
	}
	public String getWaterTextDiaphaneityCehckValue() {
		return waterTextDiaphaneityCehckValue;
	}
	public void setWaterTextDiaphaneityCehckValue(
			String waterTextDiaphaneityCehckValue) {
		this.waterTextDiaphaneityCehckValue = waterTextDiaphaneityCehckValue;
	}
	
	
	public int getWaterTextDiaphaneityValue() {
		return waterTextDiaphaneityValue;
	}
	public void setWaterTextDiaphaneityValue(int waterTextDiaphaneityValue) {
		this.waterTextDiaphaneityValue = waterTextDiaphaneityValue;
	}
	public int getWaterTextX() {
		return waterTextX;
	}
	public void setWaterTextX(int waterTextX) {
		this.waterTextX = waterTextX;
	}
	public int getWaterTextY() {
		return waterTextY;
	}
	public void setWaterTextY(int waterTextY) {
		this.waterTextY = waterTextY;
	}
	public String getImageLightenessCheckValue() {
		return imageLightenessCheckValue;
	}
	public String getWaterTextDate() {
		return waterTextDate;
	}
	public void setWaterTextDate(String waterTextDate) {
		this.waterTextDate = waterTextDate;
	}
	public void setImageLightenessCheckValue(String imageLightenessCheckValue) {
		this.imageLightenessCheckValue = imageLightenessCheckValue;
	}
	public int getImageLighteness() {
		return imageLighteness;
	}
	public void setImageLighteness(int imageLighteness) {
		this.imageLighteness = imageLighteness;
	}
	public String getImageContrastValue() {
		return imageContrastValue;
	}
	public void setImageContrastValue(String imageContrastValue) {
		this.imageContrastValue = imageContrastValue;
	}
	public int getImageContrast() {
		return imageContrast;
	}
	public void setImageContrast(int imageContrast) {
		this.imageContrast = imageContrast;
	}
	public String getBlackAndwhiteCheckValue() {
		return blackAndwhiteCheckValue;
	}
	public void setBlackAndwhiteCheckValue(String blackAndwhiteCheckValue) {
		this.blackAndwhiteCheckValue = blackAndwhiteCheckValue;
	}
	public String getSharpCheckValue() {
		return sharpCheckValue;
	}
	public void setSharpCheckValue(String sharpCheckValue) {
		this.sharpCheckValue = sharpCheckValue;
	}
	
	public String getBlurCheckValue() {
		return blurCheckValue;
	}
	public void setBlurCheckValue(String blurCheckValue) {
		this.blurCheckValue = blurCheckValue;
	}
	public String getSlickCheckValue() {
		return slickCheckValue;
	}
	public void setSlickCheckValue(String slickCheckValue) {
		this.slickCheckValue = slickCheckValue;
	}
	public String getNegativeCheckValue() {
		return negativeCheckValue;
	}
	public void setNegativeCheckValue(String negativeCheckValue) {
		this.negativeCheckValue = negativeCheckValue;
	}
	public String getRaisedCheckValue() {
		return raisedCheckValue;
	}
	public void setRaisedCheckValue(String raisedCheckValue) {
		this.raisedCheckValue = raisedCheckValue;
	}
	
	
	
	

}

package com.example.printxpress;

public class GuidelineItem {

    private String title;
    private String imageName;
    private String fileFormat;
    private String colorFormat;
    private String bleed;
    private String resolution;
    private String fonts;
    private String fileSize;

    public GuidelineItem(String title, String imageName, String fileFormat,
                         String colorFormat, String bleed, String resolution,
                         String fonts, String fileSize) {
        this.title = title;
        this.imageName = imageName;
        this.fileFormat = fileFormat;
        this.colorFormat = colorFormat;
        this.bleed = bleed;
        this.resolution = resolution;
        this.fonts = fonts;
        this.fileSize = fileSize;
    }

    public String getTitle() { return title; }
    public String getImageName() { return imageName; }
    public String getFileFormat() { return fileFormat; }
    public String getColorFormat() { return colorFormat; }
    public String getBleed() { return bleed; }
    public String getResolution() { return resolution; }
    public String getFonts() { return fonts; }
    public String getFileSize() { return fileSize; }

    public void setTitle(String title) { this.title = title; }
    public void setImageName(String imageName) { this.imageName = imageName; }
    public void setFileFormat(String fileFormat) { this.fileFormat = fileFormat; }
    public void setColorFormat(String colorFormat) { this.colorFormat = colorFormat; }
    public void setBleed(String bleed) { this.bleed = bleed; }
    public void setResolution(String resolution) { this.resolution = resolution; }
    public void setFonts(String fonts) { this.fonts = fonts; }
    public void setFileSize(String fileSize) { this.fileSize = fileSize; }
}
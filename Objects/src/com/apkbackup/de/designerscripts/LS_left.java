package com.apkbackup.de.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_left{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("lvmenu").vw.setLeft((int)((0d / 100 * width)));
views.get("lvmenu").vw.setWidth((int)((100d / 100 * width) - ((0d / 100 * width))));
views.get("lvmenu").vw.setTop((int)((10d / 100 * height)));
views.get("lvmenu").vw.setHeight((int)((100d / 100 * height) - ((10d / 100 * height))));
views.get("btb").vw.setTop((int)((0d / 100 * width)));
views.get("btb").vw.setHeight((int)((views.get("lvmenu").vw.getTop()) - ((0d / 100 * width))));
views.get("btb").vw.setLeft((int)((0d / 100 * width)));
views.get("btb").vw.setWidth((int)((100d / 100 * width) - ((0d / 100 * width))));

}
}
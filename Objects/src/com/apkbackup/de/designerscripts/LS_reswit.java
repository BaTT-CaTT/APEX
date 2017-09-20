package com.apkbackup.de.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_reswit{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("lvg").vw.setTop((int)(0d));
views.get("lvg").vw.setHeight((int)((55d / 100 * height) - (0d)));
views.get("lvg").vw.setLeft((int)((15d / 100 * width)));
views.get("lvg").vw.setWidth((int)((85d / 100 * width) - ((15d / 100 * width))));
views.get("label1").vw.setLeft((int)((5d * scale)));
views.get("label1").vw.setWidth((int)((98d / 100 * width) - ((5d * scale))));
views.get("label1").vw.setTop((int)((40d / 100 * height)));
//BA.debugLineNum = 8;BA.debugLine="Label1.Height=100dip"[reswit/General script]
views.get("label1").vw.setHeight((int)((100d * scale)));

}
}
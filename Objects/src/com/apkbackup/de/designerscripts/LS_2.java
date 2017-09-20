package com.apkbackup.de.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_2{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
//BA.debugLineNum = 2;BA.debugLine="AutoScaleAll"[2/General script]
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
//BA.debugLineNum = 3;BA.debugLine="toolbar.Height=54dip"[2/General script]
views.get("toolbar").vw.setHeight((int)((54d * scale)));
//BA.debugLineNum = 4;BA.debugLine="toolbar.Top=0"[2/General script]
views.get("toolbar").vw.setTop((int)(0d));
//BA.debugLineNum = 5;BA.debugLine="toolbar.SetLeftAndRight(0,100%x)"[2/General script]
views.get("toolbar").vw.setLeft((int)(0d));
views.get("toolbar").vw.setWidth((int)((100d / 100 * width) - (0d)));
//BA.debugLineNum = 6;BA.debugLine="svList.SetTopAndBottom(toolbar.Bottom,100%y)"[2/General script]
views.get("svlist").vw.setTop((int)((views.get("toolbar").vw.getTop() + views.get("toolbar").vw.getHeight())));
views.get("svlist").vw.setHeight((int)((100d / 100 * height) - ((views.get("toolbar").vw.getTop() + views.get("toolbar").vw.getHeight()))));
//BA.debugLineNum = 7;BA.debugLine="svList.SetLeftAndRight(0%x,100%x)"[2/General script]
views.get("svlist").vw.setLeft((int)((0d / 100 * width)));
views.get("svlist").vw.setWidth((int)((100d / 100 * width) - ((0d / 100 * width))));

}
}
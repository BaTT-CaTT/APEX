package com.apex.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_2{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
//BA.debugLineNum = 2;BA.debugLine="AutoScaleAll"[2/General script]
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
//BA.debugLineNum = 3;BA.debugLine="toolbar.Top=0dip"[2/General script]
views.get("toolbar").vw.setTop((int)((0d * scale)));
//BA.debugLineNum = 4;BA.debugLine="toolbar.Height=50dip"[2/General script]
views.get("toolbar").vw.setHeight((int)((50d * scale)));
//BA.debugLineNum = 5;BA.debugLine="toolbar.SetLeftAndRight(0dip,100%x)"[2/General script]
views.get("toolbar").vw.setLeft((int)((0d * scale)));
views.get("toolbar").vw.setWidth((int)((100d / 100 * width) - ((0d * scale))));
//BA.debugLineNum = 6;BA.debugLine="ListView1.SetTopAndBottom(10%y,99%y)"[2/General script]
views.get("listview1").vw.setTop((int)((10d / 100 * height)));
views.get("listview1").vw.setHeight((int)((99d / 100 * height) - ((10d / 100 * height))));
//BA.debugLineNum = 7;BA.debugLine="ListView1.SetLeftAndRight(1dip,99%x)"[2/General script]
views.get("listview1").vw.setLeft((int)((1d * scale)));
views.get("listview1").vw.setWidth((int)((99d / 100 * width) - ((1d * scale))));

}
}
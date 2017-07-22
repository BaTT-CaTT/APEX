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
//BA.debugLineNum = 4;BA.debugLine="toolbar.SetLeftAndRight(0dip,100%x)"[2/General script]
views.get("toolbar").vw.setLeft((int)((0d * scale)));
views.get("toolbar").vw.setWidth((int)((100d / 100 * width) - ((0d * scale))));
//BA.debugLineNum = 5;BA.debugLine="ListView1.SetTopAndBottom(12%y,100%y)"[2/General script]
views.get("listview1").vw.setTop((int)((12d / 100 * height)));
views.get("listview1").vw.setHeight((int)((100d / 100 * height) - ((12d / 100 * height))));
//BA.debugLineNum = 6;BA.debugLine="ListView1.SetLeftAndRight(1dip,99%x)"[2/General script]
views.get("listview1").vw.setLeft((int)((1d * scale)));
views.get("listview1").vw.setWidth((int)((99d / 100 * width) - ((1d * scale))));
//BA.debugLineNum = 7;BA.debugLine="Label1.SetLeftAndRight(0dip,90%x)"[2/General script]
views.get("label1").vw.setLeft((int)((0d * scale)));
views.get("label1").vw.setWidth((int)((90d / 100 * width) - ((0d * scale))));
//BA.debugLineNum = 8;BA.debugLine="lvt.SetLeftAndRight(0dip,90%x)"[2/General script]
views.get("lvt").vw.setLeft((int)((0d * scale)));
views.get("lvt").vw.setWidth((int)((90d / 100 * width) - ((0d * scale))));
//BA.debugLineNum = 9;BA.debugLine="iv1.SetLeftAndRight(2dip,25%x)"[2/General script]
views.get("iv1").vw.setLeft((int)((2d * scale)));
views.get("iv1").vw.setWidth((int)((25d / 100 * width) - ((2d * scale))));
//BA.debugLineNum = 10;BA.debugLine="iv1.Top=28dip"[2/General script]
views.get("iv1").vw.setTop((int)((28d * scale)));
//BA.debugLineNum = 11;BA.debugLine="Panel1.SetLeftAndRight(5%x,95%x)"[2/General script]
views.get("panel1").vw.setLeft((int)((5d / 100 * width)));
views.get("panel1").vw.setWidth((int)((95d / 100 * width) - ((5d / 100 * width))));
//BA.debugLineNum = 12;BA.debugLine="Panel2.SetLeftAndRight(5%x,95%x)"[2/General script]
views.get("panel2").vw.setLeft((int)((5d / 100 * width)));
views.get("panel2").vw.setWidth((int)((95d / 100 * width) - ((5d / 100 * width))));
//BA.debugLineNum = 14;BA.debugLine="ab1.Left=17dip"[2/General script]
views.get("ab1").vw.setLeft((int)((17d * scale)));
//BA.debugLineNum = 15;BA.debugLine="ab2.Right=270dip"[2/General script]
views.get("ab2").vw.setLeft((int)((270d * scale) - (views.get("ab2").vw.getWidth())));

}
}
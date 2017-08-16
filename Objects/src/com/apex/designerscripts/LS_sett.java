package com.apex.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_sett{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("panel2").vw.setLeft((int)((0d * scale)));
views.get("panel2").vw.setWidth((int)((100d / 100 * width) - ((0d * scale))));
views.get("panel1").vw.setTop((int)((90d * scale)));
views.get("panel1").vw.setHeight((int)((100d / 100 * height) - ((90d * scale))));
views.get("label1").vw.setLeft((int)((0d * scale)));
views.get("label1").vw.setWidth((int)((100d / 100 * width) - ((0d * scale))));
views.get("label1").vw.setHeight((int)((25d * scale)));
//BA.debugLineNum = 7;BA.debugLine="axt.SetLeftAndRight(0dip,100%x)"[sett/General script]
views.get("axt").vw.setLeft((int)((0d * scale)));
views.get("axt").vw.setWidth((int)((100d / 100 * width) - ((0d * scale))));
//BA.debugLineNum = 8;BA.debugLine="axt.SetTopAndBottom(25dip,30%x)"[sett/General script]
views.get("axt").vw.setTop((int)((25d * scale)));
views.get("axt").vw.setHeight((int)((30d / 100 * width) - ((25d * scale))));
//BA.debugLineNum = 9;BA.debugLine="b1.SetLeftAndRight(30%x,70%x)"[sett/General script]
views.get("b1").vw.setLeft((int)((30d / 100 * width)));
views.get("b1").vw.setWidth((int)((70d / 100 * width) - ((30d / 100 * width))));
//BA.debugLineNum = 10;BA.debugLine="b1.SetTopAndBottom(88%y,0dip)"[sett/General script]
views.get("b1").vw.setTop((int)((88d / 100 * height)));
views.get("b1").vw.setHeight((int)((0d * scale) - ((88d / 100 * height))));
//BA.debugLineNum = 11;BA.debugLine="b1.Height=50dip"[sett/General script]
views.get("b1").vw.setHeight((int)((50d * scale)));
//BA.debugLineNum = 12;BA.debugLine="b1.Bottom=99%y"[sett/General script]
views.get("b1").vw.setTop((int)((99d / 100 * height) - (views.get("b1").vw.getHeight())));
//BA.debugLineNum = 13;BA.debugLine="loglist.SetLeftAndRight(0dip,100%x)"[sett/General script]
views.get("loglist").vw.setLeft((int)((0d * scale)));
views.get("loglist").vw.setWidth((int)((100d / 100 * width) - ((0d * scale))));
//BA.debugLineNum = 14;BA.debugLine="mst.SetLeftAndRight(5dip,99%x)"[sett/General script]
views.get("mst").vw.setLeft((int)((5d * scale)));
views.get("mst").vw.setWidth((int)((99d / 100 * width) - ((5d * scale))));
//BA.debugLineNum = 15;BA.debugLine="loglist.SetTopAndBottom(0dip,33%y)"[sett/General script]
views.get("loglist").vw.setTop((int)((0d * scale)));
views.get("loglist").vw.setHeight((int)((33d / 100 * height) - ((0d * scale))));
//BA.debugLineNum = 16;BA.debugLine="mst.SetTopAndBottom(160dip,77%y)"[sett/General script]
views.get("mst").vw.setTop((int)((160d * scale)));
views.get("mst").vw.setHeight((int)((77d / 100 * height) - ((160d * scale))));

}
}
package com.apkbackup.de.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_right{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("otb").vw.setLeft((int)(0d));
views.get("otb").vw.setWidth((int)((100d / 100 * width) - (0d)));
views.get("otb").vw.setTop((int)((0d * scale)));
views.get("otb").vw.setHeight((int)((52d * scale) - ((0d * scale))));
views.get("rftext").vw.setTop((int)((views.get("acp").vw.getTop() + views.get("acp").vw.getHeight())-(12d / 100 * height)));
views.get("rftext").vw.setLeft((int)(0d));
views.get("rftext").vw.setWidth((int)((85d / 100 * width) - (0d)));
//BA.debugLineNum = 7;BA.debugLine="Panel1.SetLeftAndRight(0,100%x)"[right/General script]
views.get("panel1").vw.setLeft((int)(0d));
views.get("panel1").vw.setWidth((int)((100d / 100 * width) - (0d)));
//BA.debugLineNum = 8;BA.debugLine="Panel1.SetTopAndBottom(otb.Bottom,100%y)"[right/General script]
views.get("panel1").vw.setTop((int)((views.get("otb").vw.getTop() + views.get("otb").vw.getHeight())));
views.get("panel1").vw.setHeight((int)((100d / 100 * height) - ((views.get("otb").vw.getTop() + views.get("otb").vw.getHeight()))));
//BA.debugLineNum = 9;BA.debugLine="rvmenu.SetTopAndBottom(50%y,89%y)"[right/General script]
views.get("rvmenu").vw.setTop((int)((50d / 100 * height)));
views.get("rvmenu").vw.setHeight((int)((89d / 100 * height) - ((50d / 100 * height))));
//BA.debugLineNum = 10;BA.debugLine="rvmenu.SetLeftAndRight(0dip,100%x)"[right/General script]
views.get("rvmenu").vw.setLeft((int)((0d * scale)));
views.get("rvmenu").vw.setWidth((int)((100d / 100 * width) - ((0d * scale))));
//BA.debugLineNum = 11;BA.debugLine="acp.Top=1%y"[right/General script]
views.get("acp").vw.setTop((int)((1d / 100 * height)));
//BA.debugLineNum = 12;BA.debugLine="acp.SetLeftAndRight(0dip,100%x)"[right/General script]
views.get("acp").vw.setLeft((int)((0d * scale)));
views.get("acp").vw.setWidth((int)((100d / 100 * width) - ((0d * scale))));
//BA.debugLineNum = 13;BA.debugLine="acp.Height=50dip"[right/General script]
views.get("acp").vw.setHeight((int)((50d * scale)));
//BA.debugLineNum = 14;BA.debugLine="acs.Top=acp.Bottom+5dip"[right/General script]
views.get("acs").vw.setTop((int)((views.get("acp").vw.getTop() + views.get("acp").vw.getHeight())+(5d * scale)));
//BA.debugLineNum = 15;BA.debugLine="acs.SetLeftAndRight(75%x,90%x)"[right/General script]
views.get("acs").vw.setLeft((int)((75d / 100 * width)));
views.get("acs").vw.setWidth((int)((90d / 100 * width) - ((75d / 100 * width))));
//BA.debugLineNum = 16;BA.debugLine="acs.Height=50dip"[right/General script]
views.get("acs").vw.setHeight((int)((50d * scale)));
//BA.debugLineNum = 17;BA.debugLine="acs.Right=100%x"[right/General script]
views.get("acs").vw.setLeft((int)((100d / 100 * width) - (views.get("acs").vw.getWidth())));

}
}
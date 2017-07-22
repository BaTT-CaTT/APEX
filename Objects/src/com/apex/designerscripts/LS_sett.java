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
views.get("label1").vw.setLeft((int)((0d * scale)));
views.get("label1").vw.setWidth((int)((100d / 100 * width) - ((0d * scale))));
views.get("axt").vw.setLeft((int)((0d * scale)));
views.get("axt").vw.setWidth((int)((100d / 100 * width) - ((0d * scale))));
views.get("mct").vw.setLeft((int)((0d * scale)));
views.get("mct").vw.setWidth((int)((100d / 100 * width) - ((0d * scale))));
views.get("b1").vw.setLeft((int)((30d / 100 * width)));
views.get("b1").vw.setWidth((int)((70d / 100 * width) - ((30d / 100 * width))));
views.get("b1").vw.setTop((int)((88d / 100 * height)));
views.get("b1").vw.setHeight((int)((0d * scale) - ((88d / 100 * height))));
views.get("b1").vw.setHeight((int)((50d * scale)));

}
}
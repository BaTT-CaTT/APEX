package com.apkbackup.de;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class clschecklist extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    private static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new BA(_ba, this, htSubs, "com.apkbackup.de.clschecklist");
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            
        }
        if (BA.isShellModeRuntimeCheck(ba)) 
			   this.getClass().getMethod("_class_globals", com.apkbackup.de.clschecklist.class).invoke(this, new Object[] {null});
        else
            ba.raiseEvent2(null, true, "class_globals", false);
    }

 public anywheresoftware.b4a.keywords.Common __c = null;
public int _backgroundcolor = 0;
public int _checkedcolor = 0;
public int _dividercolor = 0;
public int _extensioncolor = 0;
public boolean _filterresult = false;
public flm.b4a.scrollview2d.ScrollView2DWrapper _sv = null;
public int _svheight = 0;
public int _extensionindex = 0;
public anywheresoftware.b4a.objects.ConcreteViewWrapper _extensionview = null;
public Object _callbackmod = null;
public String _sub_check = "";
public String _sub_click = "";
public String _sub_longclick = "";
public String _sub_paint = "";
public boolean _modecheck = false;
public boolean _blockcheckevent = false;
public anywheresoftware.b4a.objects.PanelWrapper _pressedpanel = null;
public Object _presseddrawable = null;
public Object _oldbackground = null;
public int _dividersize = 0;
public anywheresoftware.b4a.objects.PanelWrapper _draganddrop = null;
public anywheresoftware.b4a.objects.PanelWrapper _draganddropshadow = null;
public int _originddtop = 0;
public int _lasty = 0;
public String _sub_afterdrop = "";
public int _chkboxsize = 0;
public int _bitmapsize = 0;
public int _labelheight = 0;
public int _panelheight = 0;
public int _paint_default = 0;
public int _paint_extended = 0;
public int _paint_checked = 0;
public int _paint_pressed = 0;
public com.apkbackup.de.main _main = null;
public com.apkbackup.de.swit _swit = null;
public com.apkbackup.de.animator _animator = null;
public com.apkbackup.de.savemodule _savemodule = null;
public com.apkbackup.de.starter _starter = null;
public com.apkbackup.de.fileobserve _fileobserve = null;
public static class _typsortdata1{
public boolean IsInitialized;
public int Index;
public Object Value;
public void Initialize() {
IsInitialized = true;
Index = 0;
Value = new Object();
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public String  _abortdraganddrop() throws Exception{
 //BA.debugLineNum = 1047;BA.debugLine="Public Sub AbortDragAndDrop()";
 //BA.debugLineNum = 1048;BA.debugLine="If DragAndDrop.IsInitialized Then";
if (_draganddrop.IsInitialized()) { 
 //BA.debugLineNum = 1049;BA.debugLine="DragAndDrop.RemoveView";
_draganddrop.RemoveView();
 //BA.debugLineNum = 1050;BA.debugLine="DragAndDrop = Null";
_draganddrop.setObject((android.view.ViewGroup)(__c.Null));
 //BA.debugLineNum = 1051;BA.debugLine="DragAndDropShadow.RemoveView";
_draganddropshadow.RemoveView();
 //BA.debugLineNum = 1052;BA.debugLine="DragAndDropShadow = Null";
_draganddropshadow.setObject((android.view.ViewGroup)(__c.Null));
 };
 //BA.debugLineNum = 1054;BA.debugLine="End Sub";
return "";
}
public String  _addcustomitem(Object _id,anywheresoftware.b4a.objects.PanelWrapper _pnl,int _pnlheight) throws Exception{
 //BA.debugLineNum = 227;BA.debugLine="Public Sub AddCustomItem(ID As Object, pnl As Pane";
 //BA.debugLineNum = 228;BA.debugLine="If pnl.IsInitialized Then";
if (_pnl.IsInitialized()) { 
 //BA.debugLineNum = 229;BA.debugLine="pnl.Tag = ID";
_pnl.setTag(_id);
 //BA.debugLineNum = 230;BA.debugLine="AddToSV(pnl, pnlHeight, True)";
_addtosv(_pnl,_pnlheight,__c.True);
 };
 //BA.debugLineNum = 232;BA.debugLine="End Sub";
return "";
}
public String  _addheader(String _text) throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _pnlh = null;
anywheresoftware.b4a.objects.LabelWrapper _lblb = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _cdh = null;
 //BA.debugLineNum = 235;BA.debugLine="Public Sub AddHeader(Text As String)";
 //BA.debugLineNum = 236;BA.debugLine="Dim pnlH As Panel: pnlH.Initialize(\"\")";
_pnlh = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 236;BA.debugLine="Dim pnlH As Panel: pnlH.Initialize(\"\")";
_pnlh.Initialize(ba,"");
 //BA.debugLineNum = 237;BA.debugLine="Dim lblB As Label: lblB.Initialize(\"\")";
_lblb = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 237;BA.debugLine="Dim lblB As Label: lblB.Initialize(\"\")";
_lblb.Initialize(ba,"");
 //BA.debugLineNum = 238;BA.debugLine="Dim cdH As ColorDrawable";
_cdh = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 239;BA.debugLine="cdH.Initialize(Colors.Transparent, 0)";
_cdh.Initialize(__c.Colors.Transparent,(int) (0));
 //BA.debugLineNum = 240;BA.debugLine="lblB.Background = cdH";
_lblb.setBackground((android.graphics.drawable.Drawable)(_cdh.getObject()));
 //BA.debugLineNum = 241;BA.debugLine="lblB.Text = \"   \" & Text";
_lblb.setText(BA.ObjectToCharSequence("   "+_text));
 //BA.debugLineNum = 242;BA.debugLine="lblB.TextColor = Colors.White";
_lblb.setTextColor(__c.Colors.White);
 //BA.debugLineNum = 243;BA.debugLine="lblB.TextSize = 20";
_lblb.setTextSize((float) (20));
 //BA.debugLineNum = 244;BA.debugLine="lblB.Typeface = Typeface.LoadFromAssets(\"OpenSans";
_lblb.setTypeface(__c.Typeface.LoadFromAssets("OpenSans.ttf"));
 //BA.debugLineNum = 245;BA.debugLine="lblB.Gravity = Gravity.CENTER_HORIZONTAL";
_lblb.setGravity(__c.Gravity.CENTER_HORIZONTAL);
 //BA.debugLineNum = 246;BA.debugLine="pnlH.AddView(lblB, 0, 0, 100%x, 30dip)";
_pnlh.AddView((android.view.View)(_lblb.getObject()),(int) (0),(int) (0),__c.PerXToCurrent((float) (100),ba),__c.DipToCurrent((int) (30)));
 //BA.debugLineNum = 247;BA.debugLine="AddToSV(pnlH, PanelHeight, False)";
_addtosv(_pnlh,_panelheight,__c.False);
 //BA.debugLineNum = 248;BA.debugLine="PaintBackground(pnlH, False)";
_paintbackground(_pnlh,__c.False);
 //BA.debugLineNum = 249;BA.debugLine="End Sub";
return "";
}
public String  _additem(Object _id,boolean _checked,String _text1,String _text2,anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _image) throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _pnl = null;
anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chk = null;
int _largeurlabel = 0;
int _posx = 0;
anywheresoftware.b4a.objects.ImageViewWrapper _iv = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _bmp = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl1 = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl2 = null;
 //BA.debugLineNum = 131;BA.debugLine="Public Sub AddItem(ID As Object, Checked As Boolea";
 //BA.debugLineNum = 132;BA.debugLine="Dim pnl As Panel: pnl.Initialize(\"\")";
_pnl = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 132;BA.debugLine="Dim pnl As Panel: pnl.Initialize(\"\")";
_pnl.Initialize(ba,"");
 //BA.debugLineNum = 133;BA.debugLine="Dim chk As CheckBox:	chk.Initialize(\"chbSV\")";
_chk = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 133;BA.debugLine="Dim chk As CheckBox:	chk.Initialize(\"chbSV\")";
_chk.Initialize(ba,"chbSV");
 //BA.debugLineNum = 134;BA.debugLine="pnl.Tag = ID";
_pnl.setTag(_id);
 //BA.debugLineNum = 135;BA.debugLine="pnl.AddView(chk, 0, 0, ChkBoxSize, PanelHeight)";
_pnl.AddView((android.view.View)(_chk.getObject()),(int) (0),(int) (0),_chkboxsize,_panelheight);
 //BA.debugLineNum = 136;BA.debugLine="Dim LargeurLabel As Int, PosX As Int";
_largeurlabel = 0;
_posx = 0;
 //BA.debugLineNum = 137;BA.debugLine="LargeurLabel = 150%x";
_largeurlabel = __c.PerXToCurrent((float) (150),ba);
 //BA.debugLineNum = 138;BA.debugLine="If Not(Image.IsInitialized) Then";
if (__c.Not(_image.IsInitialized())) { 
 //BA.debugLineNum = 139;BA.debugLine="PosX = ChkBoxSize";
_posx = _chkboxsize;
 }else {
 //BA.debugLineNum = 141;BA.debugLine="Dim iv As ImageView: iv.Initialize(\"\")";
_iv = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 141;BA.debugLine="Dim iv As ImageView: iv.Initialize(\"\")";
_iv.Initialize(ba,"");
 //BA.debugLineNum = 142;BA.debugLine="Dim bmp As Bitmap";
_bmp = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 143;BA.debugLine="bmp = Image";
_bmp = _image;
 //BA.debugLineNum = 144;BA.debugLine="If NumberFormat(bmp.Width / bmp.Height, 1, 2) =";
if ((__c.NumberFormat(_bmp.getWidth()/(double)_bmp.getHeight(),(int) (1),(int) (2))).equals(__c.NumberFormat(1,(int) (1),(int) (2)))) { 
 //BA.debugLineNum = 146;BA.debugLine="iv.Gravity = Gravity.FILL";
_iv.setGravity(__c.Gravity.FILL);
 }else {
 //BA.debugLineNum = 149;BA.debugLine="bmp = AdjustBitmap(bmp, BitmapSize, BitmapSize)";
_bmp = _adjustbitmap(_bmp,_bitmapsize,_bitmapsize);
 //BA.debugLineNum = 150;BA.debugLine="iv.Gravity = Gravity.NO_GRAVITY";
_iv.setGravity(__c.Gravity.NO_GRAVITY);
 };
 //BA.debugLineNum = 152;BA.debugLine="iv.Bitmap = bmp";
_iv.setBitmap((android.graphics.Bitmap)(_bmp.getObject()));
 //BA.debugLineNum = 153;BA.debugLine="pnl.AddView(iv, ChkBoxSize, 0, BitmapSize, Bitma";
_pnl.AddView((android.view.View)(_iv.getObject()),_chkboxsize,(int) (0),_bitmapsize,_bitmapsize);
 //BA.debugLineNum = 154;BA.debugLine="PosX = ChkBoxSize + BitmapSize + 5dip";
_posx = (int) (_chkboxsize+_bitmapsize+__c.DipToCurrent((int) (5)));
 };
 //BA.debugLineNum = 156;BA.debugLine="Dim lbl1 As Label: lbl1.Initialize(\"\")";
_lbl1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 156;BA.debugLine="Dim lbl1 As Label: lbl1.Initialize(\"\")";
_lbl1.Initialize(ba,"");
 //BA.debugLineNum = 157;BA.debugLine="lbl1.Gravity = Gravity.CENTER_VERTICAL";
_lbl1.setGravity(__c.Gravity.CENTER_VERTICAL);
 //BA.debugLineNum = 158;BA.debugLine="lbl1.Text = Text1";
_lbl1.setText(BA.ObjectToCharSequence(_text1));
 //BA.debugLineNum = 159;BA.debugLine="lbl1.TextColor = Colors.White";
_lbl1.setTextColor(__c.Colors.White);
 //BA.debugLineNum = 160;BA.debugLine="lbl1.TextSize = 18";
_lbl1.setTextSize((float) (18));
 //BA.debugLineNum = 161;BA.debugLine="lbl1.Typeface = Typeface.LoadFromAssets(\"OpenSans";
_lbl1.setTypeface(__c.Typeface.LoadFromAssets("OpenSans.ttf"));
 //BA.debugLineNum = 162;BA.debugLine="If Text2 = \"\" Then";
if ((_text2).equals("")) { 
 //BA.debugLineNum = 163;BA.debugLine="pnl.AddView(lbl1, PosX, 0, LargeurLabel, PanelHe";
_pnl.AddView((android.view.View)(_lbl1.getObject()),_posx,(int) (0),_largeurlabel,_panelheight);
 }else {
 //BA.debugLineNum = 165;BA.debugLine="pnl.AddView(lbl1, PosX, 0, LargeurLabel, LabelHe";
_pnl.AddView((android.view.View)(_lbl1.getObject()),_posx,(int) (0),_largeurlabel,_labelheight);
 //BA.debugLineNum = 166;BA.debugLine="Dim lbl2 As Label: lbl2.Initialize(\"\")";
_lbl2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 166;BA.debugLine="Dim lbl2 As Label: lbl2.Initialize(\"\")";
_lbl2.Initialize(ba,"");
 //BA.debugLineNum = 167;BA.debugLine="lbl2.Gravity = Gravity.TOP";
_lbl2.setGravity(__c.Gravity.TOP);
 //BA.debugLineNum = 168;BA.debugLine="lbl2.Text = Text2";
_lbl2.setText(BA.ObjectToCharSequence(_text2));
 //BA.debugLineNum = 169;BA.debugLine="lbl2.TextColor = Colors.LightGray";
_lbl2.setTextColor(__c.Colors.LightGray);
 //BA.debugLineNum = 170;BA.debugLine="lbl2.TextSize = 16";
_lbl2.setTextSize((float) (16));
 //BA.debugLineNum = 171;BA.debugLine="lbl2.Typeface = Typeface.DEFAULT";
_lbl2.setTypeface(__c.Typeface.DEFAULT);
 //BA.debugLineNum = 172;BA.debugLine="pnl.AddView(lbl2, PosX, LabelHeight, LargeurLabe";
_pnl.AddView((android.view.View)(_lbl2.getObject()),_posx,_labelheight,_largeurlabel,_labelheight);
 };
 //BA.debugLineNum = 174;BA.debugLine="AddToSV(pnl, PanelHeight, True)";
_addtosv(_pnl,_panelheight,__c.True);
 //BA.debugLineNum = 175;BA.debugLine="BlockCheckEvent = True";
_blockcheckevent = __c.True;
 //BA.debugLineNum = 176;BA.debugLine="chk.Checked = Checked";
_chk.setChecked(_checked);
 //BA.debugLineNum = 177;BA.debugLine="BlockCheckEvent = False";
_blockcheckevent = __c.False;
 //BA.debugLineNum = 178;BA.debugLine="PaintBackground(pnl, False)";
_paintbackground(_pnl,__c.False);
 //BA.debugLineNum = 179;BA.debugLine="End Sub";
return "";
}
public String  _additemnochkbx(Object _id,String _text1,String _text2,anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _image) throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _pnl = null;
int _largeurlabel = 0;
int _posx = 0;
anywheresoftware.b4a.objects.ImageViewWrapper _iv = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _bmp = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl1 = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl2 = null;
 //BA.debugLineNum = 183;BA.debugLine="Public Sub AddItemNoChkbx(ID As Object, Text1 As S";
 //BA.debugLineNum = 184;BA.debugLine="Dim pnl As Panel: pnl.Initialize(\"\")";
_pnl = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 184;BA.debugLine="Dim pnl As Panel: pnl.Initialize(\"\")";
_pnl.Initialize(ba,"");
 //BA.debugLineNum = 185;BA.debugLine="pnl.Tag = ID";
_pnl.setTag(_id);
 //BA.debugLineNum = 186;BA.debugLine="Dim LargeurLabel As Int, PosX As Int";
_largeurlabel = 0;
_posx = 0;
 //BA.debugLineNum = 187;BA.debugLine="LargeurLabel = 150%x";
_largeurlabel = __c.PerXToCurrent((float) (150),ba);
 //BA.debugLineNum = 188;BA.debugLine="If Image.IsInitialized Then";
if (_image.IsInitialized()) { 
 //BA.debugLineNum = 189;BA.debugLine="Dim iv As ImageView: iv.Initialize(\"\")";
_iv = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 189;BA.debugLine="Dim iv As ImageView: iv.Initialize(\"\")";
_iv.Initialize(ba,"");
 //BA.debugLineNum = 190;BA.debugLine="Dim bmp As Bitmap";
_bmp = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 191;BA.debugLine="bmp = Image";
_bmp = _image;
 //BA.debugLineNum = 192;BA.debugLine="If NumberFormat(bmp.Width / bmp.Height, 1, 2) =";
if ((__c.NumberFormat(_bmp.getWidth()/(double)_bmp.getHeight(),(int) (1),(int) (2))).equals(__c.NumberFormat(1,(int) (1),(int) (2)))) { 
 //BA.debugLineNum = 194;BA.debugLine="iv.Gravity = Gravity.FILL";
_iv.setGravity(__c.Gravity.FILL);
 }else {
 //BA.debugLineNum = 197;BA.debugLine="bmp = AdjustBitmap(bmp, BitmapSize, BitmapSize)";
_bmp = _adjustbitmap(_bmp,_bitmapsize,_bitmapsize);
 //BA.debugLineNum = 198;BA.debugLine="iv.Gravity = Gravity.NO_GRAVITY";
_iv.setGravity(__c.Gravity.NO_GRAVITY);
 };
 //BA.debugLineNum = 200;BA.debugLine="iv.Bitmap = bmp";
_iv.setBitmap((android.graphics.Bitmap)(_bmp.getObject()));
 //BA.debugLineNum = 201;BA.debugLine="pnl.AddView(iv, 0, 0, BitmapSize, BitmapSize)";
_pnl.AddView((android.view.View)(_iv.getObject()),(int) (0),(int) (0),_bitmapsize,_bitmapsize);
 //BA.debugLineNum = 202;BA.debugLine="PosX = BitmapSize + 5dip";
_posx = (int) (_bitmapsize+__c.DipToCurrent((int) (5)));
 };
 //BA.debugLineNum = 204;BA.debugLine="Dim lbl1 As Label: lbl1.Initialize(\"\")";
_lbl1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 204;BA.debugLine="Dim lbl1 As Label: lbl1.Initialize(\"\")";
_lbl1.Initialize(ba,"");
 //BA.debugLineNum = 205;BA.debugLine="lbl1.Gravity = Gravity.CENTER_VERTICAL";
_lbl1.setGravity(__c.Gravity.CENTER_VERTICAL);
 //BA.debugLineNum = 206;BA.debugLine="lbl1.Text = Text1";
_lbl1.setText(BA.ObjectToCharSequence(_text1));
 //BA.debugLineNum = 207;BA.debugLine="lbl1.TextColor = Colors.White";
_lbl1.setTextColor(__c.Colors.White);
 //BA.debugLineNum = 208;BA.debugLine="lbl1.TextSize = 18";
_lbl1.setTextSize((float) (18));
 //BA.debugLineNum = 209;BA.debugLine="lbl1.Typeface = Typeface.DEFAULT_BOLD";
_lbl1.setTypeface(__c.Typeface.DEFAULT_BOLD);
 //BA.debugLineNum = 210;BA.debugLine="If Text2 = \"\" Then";
if ((_text2).equals("")) { 
 //BA.debugLineNum = 211;BA.debugLine="pnl.AddView(lbl1, PosX, 0, LargeurLabel, PanelHe";
_pnl.AddView((android.view.View)(_lbl1.getObject()),_posx,(int) (0),_largeurlabel,_panelheight);
 }else {
 //BA.debugLineNum = 213;BA.debugLine="pnl.AddView(lbl1, PosX, 0, LargeurLabel, LabelHe";
_pnl.AddView((android.view.View)(_lbl1.getObject()),_posx,(int) (0),_largeurlabel,_labelheight);
 //BA.debugLineNum = 214;BA.debugLine="Dim lbl2 As Label: lbl2.Initialize(\"\")";
_lbl2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 214;BA.debugLine="Dim lbl2 As Label: lbl2.Initialize(\"\")";
_lbl2.Initialize(ba,"");
 //BA.debugLineNum = 215;BA.debugLine="lbl2.Gravity = Gravity.TOP";
_lbl2.setGravity(__c.Gravity.TOP);
 //BA.debugLineNum = 216;BA.debugLine="lbl2.Text = Text2";
_lbl2.setText(BA.ObjectToCharSequence(_text2));
 //BA.debugLineNum = 217;BA.debugLine="lbl2.TextColor = Colors.LightGray";
_lbl2.setTextColor(__c.Colors.LightGray);
 //BA.debugLineNum = 218;BA.debugLine="lbl2.TextSize = 16";
_lbl2.setTextSize((float) (16));
 //BA.debugLineNum = 219;BA.debugLine="lbl2.Typeface = Typeface.DEFAULT";
_lbl2.setTypeface(__c.Typeface.DEFAULT);
 //BA.debugLineNum = 220;BA.debugLine="pnl.AddView(lbl2, PosX, LabelHeight, LargeurLabe";
_pnl.AddView((android.view.View)(_lbl2.getObject()),_posx,_labelheight,_largeurlabel,_labelheight);
 };
 //BA.debugLineNum = 222;BA.debugLine="AddToSV(pnl, PanelHeight, True)";
_addtosv(_pnl,_panelheight,__c.True);
 //BA.debugLineNum = 223;BA.debugLine="PaintBackground(pnl, False)";
_paintbackground(_pnl,__c.False);
 //BA.debugLineNum = 224;BA.debugLine="End Sub";
return "";
}
public String  _addtosv(anywheresoftware.b4a.objects.PanelWrapper _pnl,int _pnlheight,boolean _withevents) throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _pnldiv = null;
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
 //BA.debugLineNum = 251;BA.debugLine="Private Sub AddToSV(pnl As Panel, pnlHeight As Int";
 //BA.debugLineNum = 253;BA.debugLine="If DividerSize > 0 Then";
if (_dividersize>0) { 
 //BA.debugLineNum = 254;BA.debugLine="Dim pnlDiv As Panel";
_pnldiv = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 255;BA.debugLine="pnlDiv.Initialize(\"\")";
_pnldiv.Initialize(ba,"");
 //BA.debugLineNum = 256;BA.debugLine="pnlDiv.Color = DividerColor";
_pnldiv.setColor(_dividercolor);
 //BA.debugLineNum = 257;BA.debugLine="pnl.AddView(pnlDiv, 0, pnlHeight, getWidth, Divi";
_pnl.AddView((android.view.View)(_pnldiv.getObject()),(int) (0),_pnlheight,(int)(Double.parseDouble(_getwidth())),_dividersize);
 //BA.debugLineNum = 258;BA.debugLine="pnlHeight = pnlHeight + DividerSize";
_pnlheight = (int) (_pnlheight+_dividersize);
 };
 //BA.debugLineNum = 262;BA.debugLine="sv.Panel.AddView(pnl, 0, svHeight, getWidth, pnlH";
_sv.getPanel().AddView((android.view.View)(_pnl.getObject()),(int) (0),_svheight,(int)(Double.parseDouble(_getwidth())),_pnlheight);
 //BA.debugLineNum = 263;BA.debugLine="svHeight = svHeight + pnl.Height";
_svheight = (int) (_svheight+_pnl.getHeight());
 //BA.debugLineNum = 266;BA.debugLine="If Not(WithEvents) Then Return";
if (__c.Not(_withevents)) { 
if (true) return "";};
 //BA.debugLineNum = 267;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 268;BA.debugLine="r.Target = pnl";
_r.Target = (Object)(_pnl.getObject());
 //BA.debugLineNum = 269;BA.debugLine="r.SetOnTouchListener(\"pnlSV_Touch\")";
_r.SetOnTouchListener(ba,"pnlSV_Touch");
 //BA.debugLineNum = 270;BA.debugLine="If sub_Click <> \"\" Then r.SetOnClickListener(\"pnl";
if ((_sub_click).equals("") == false) { 
_r.SetOnClickListener(ba,"pnlSV_Click");};
 //BA.debugLineNum = 271;BA.debugLine="If sub_LongClick <> \"\" Then r.SetOnLongClickListe";
if ((_sub_longclick).equals("") == false) { 
_r.SetOnLongClickListener(ba,"pnlSV_LongClick");};
 //BA.debugLineNum = 272;BA.debugLine="End Sub";
return "";
}
public anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper  _adjustbitmap(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _bmp,int _width,int _height) throws Exception{
float _ratiobmp = 0f;
float _ratioiv = 0f;
float _diviseur = 0f;
 //BA.debugLineNum = 90;BA.debugLine="Public Sub AdjustBitmap(bmp As Bitmap, Width As In";
 //BA.debugLineNum = 91;BA.debugLine="Dim RatioBmp, RatioIV As Float";
_ratiobmp = 0f;
_ratioiv = 0f;
 //BA.debugLineNum = 92;BA.debugLine="RatioBmp = bmp.Width / bmp.Height";
_ratiobmp = (float) (_bmp.getWidth()/(double)_bmp.getHeight());
 //BA.debugLineNum = 93;BA.debugLine="RatioIV = Width / Height";
_ratioiv = (float) (_width/(double)_height);
 //BA.debugLineNum = 94;BA.debugLine="Dim Diviseur As Float";
_diviseur = 0f;
 //BA.debugLineNum = 95;BA.debugLine="If RatioIV > RatioBmp Then";
if (_ratioiv>_ratiobmp) { 
 //BA.debugLineNum = 96;BA.debugLine="Diviseur = bmp.Height / Height";
_diviseur = (float) (_bmp.getHeight()/(double)_height);
 //BA.debugLineNum = 97;BA.debugLine="bmp = CreateScaledBitmap(bmp, Round(bmp.Width /";
_bmp = _createscaledbitmap(_bmp,(int) (__c.Round(_bmp.getWidth()/(double)_diviseur/(double)__c.Density)),(int) (__c.Round(_height/(double)__c.Density)));
 }else {
 //BA.debugLineNum = 99;BA.debugLine="Diviseur = bmp.Width / Width";
_diviseur = (float) (_bmp.getWidth()/(double)_width);
 //BA.debugLineNum = 100;BA.debugLine="bmp = CreateScaledBitmap(bmp, Round(Width / Dens";
_bmp = _createscaledbitmap(_bmp,(int) (__c.Round(_width/(double)__c.Density)),(int) (__c.Round(_bmp.getHeight()/(double)_diviseur/(double)__c.Density)));
 };
 //BA.debugLineNum = 102;BA.debugLine="Return bmp";
if (true) return _bmp;
 //BA.debugLineNum = 103;BA.debugLine="End Sub";
return null;
}
public String  _cancelfilter() throws Exception{
int _firstpos = 0;
anywheresoftware.b4a.objects.PanelWrapper _pnl = null;
int _i = 0;
 //BA.debugLineNum = 1245;BA.debugLine="Public Sub CancelFilter()";
 //BA.debugLineNum = 1246;BA.debugLine="Dim FirstPos As Int, pnl As Panel";
_firstpos = 0;
_pnl = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 1247;BA.debugLine="FirstPos = -1";
_firstpos = (int) (-1);
 //BA.debugLineNum = 1248;BA.debugLine="For i = 0 To NumberOfItems - 1";
{
final int step3 = 1;
final int limit3 = (int) ((double)(Double.parseDouble(_numberofitems()))-1);
_i = (int) (0) ;
for (;(step3 > 0 && _i <= limit3) || (step3 < 0 && _i >= limit3) ;_i = ((int)(0 + _i + step3))  ) {
 //BA.debugLineNum = 1249;BA.debugLine="pnl = sv.Panel.GetView(i)";
_pnl.setObject((android.view.ViewGroup)(_sv.getPanel().GetView(_i).getObject()));
 //BA.debugLineNum = 1250;BA.debugLine="If pnl.Visible = False Then";
if (_pnl.getVisible()==__c.False) { 
 //BA.debugLineNum = 1251;BA.debugLine="If FirstPos = -1 Then FirstPos = i";
if (_firstpos==-1) { 
_firstpos = _i;};
 //BA.debugLineNum = 1252;BA.debugLine="pnl.Visible = True";
_pnl.setVisible(__c.True);
 };
 }
};
 //BA.debugLineNum = 1255;BA.debugLine="If FirstPos > -1 Then MoveTopAfterFiltering(First";
if (_firstpos>-1) { 
_movetopafterfiltering(_firstpos);};
 //BA.debugLineNum = 1256;BA.debugLine="End Sub";
return "";
}
public String  _chbsv_checkedchange(boolean _checked) throws Exception{
anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chb = null;
anywheresoftware.b4a.objects.PanelWrapper _pnl = null;
 //BA.debugLineNum = 1153;BA.debugLine="Private Sub chbSV_CheckedChange(Checked As Boolean";
 //BA.debugLineNum = 1154;BA.debugLine="Dim chb As CheckBox, pnl As Panel";
_chb = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
_pnl = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 1155;BA.debugLine="chb = Sender";
_chb.setObject((android.widget.CheckBox)(__c.Sender(ba)));
 //BA.debugLineNum = 1156;BA.debugLine="pnl = FindPanelContaining(chb)";
_pnl = _findpanelcontaining((anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(_chb.getObject())));
 //BA.debugLineNum = 1157;BA.debugLine="PaintBackground(pnl, HasExtraContent And Extended";
_paintbackground(_pnl,_hasextracontent() && (_extendeditemid()).equals(_pnl.getTag()));
 //BA.debugLineNum = 1158;BA.debugLine="If BlockCheckEvent Then Return";
if (_blockcheckevent) { 
if (true) return "";};
 //BA.debugLineNum = 1159;BA.debugLine="If sub_Check <> \"\" Then";
if ((_sub_check).equals("") == false) { 
 //BA.debugLineNum = 1160;BA.debugLine="If SubExists(CallbackMod, sub_Check) Then CallSu";
if (__c.SubExists(ba,_callbackmod,_sub_check)) { 
__c.CallSubNew3(ba,_callbackmod,_sub_check,(Object)(_chb),_pnl.getTag());};
 };
 //BA.debugLineNum = 1162;BA.debugLine="End Sub";
return "";
}
public String  _checkallboxes(boolean _checked) throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _pnl = null;
anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chb = null;
int _i = 0;
int _p = 0;
 //BA.debugLineNum = 839;BA.debugLine="Public Sub CheckAllBoxes(Checked As Boolean)";
 //BA.debugLineNum = 840;BA.debugLine="BlockCheckEvent = True 'We don't want to trigger";
_blockcheckevent = __c.True;
 //BA.debugLineNum = 841;BA.debugLine="Dim pnl As Panel, chb As CheckBox";
_pnl = new anywheresoftware.b4a.objects.PanelWrapper();
_chb = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 842;BA.debugLine="For i = 0 To sv.Panel.NumberOfViews - 1";
{
final int step3 = 1;
final int limit3 = (int) (_sv.getPanel().getNumberOfViews()-1);
_i = (int) (0) ;
for (;(step3 > 0 && _i <= limit3) || (step3 < 0 && _i >= limit3) ;_i = ((int)(0 + _i + step3))  ) {
 //BA.debugLineNum = 843;BA.debugLine="pnl = sv.Panel.GetView(i)";
_pnl.setObject((android.view.ViewGroup)(_sv.getPanel().GetView(_i).getObject()));
 //BA.debugLineNum = 844;BA.debugLine="For p = 0 To pnl.NumberOfViews - 1";
{
final int step5 = 1;
final int limit5 = (int) (_pnl.getNumberOfViews()-1);
_p = (int) (0) ;
for (;(step5 > 0 && _p <= limit5) || (step5 < 0 && _p >= limit5) ;_p = ((int)(0 + _p + step5))  ) {
 //BA.debugLineNum = 845;BA.debugLine="If IsCheckBox(pnl.GetView(p)) Then";
if (_ischeckbox(_pnl.GetView(_p))) { 
 //BA.debugLineNum = 846;BA.debugLine="chb = pnl.GetView(p)";
_chb.setObject((android.widget.CheckBox)(_pnl.GetView(_p).getObject()));
 //BA.debugLineNum = 847;BA.debugLine="chb.Checked = Checked";
_chb.setChecked(_checked);
 //BA.debugLineNum = 848;BA.debugLine="Exit";
if (true) break;
 };
 }
};
 }
};
 //BA.debugLineNum = 852;BA.debugLine="BlockCheckEvent = False";
_blockcheckevent = __c.False;
 //BA.debugLineNum = 853;BA.debugLine="End Sub";
return "";
}
public anywheresoftware.b4a.objects.collections.List  _checkedlist() throws Exception{
 //BA.debugLineNum = 829;BA.debugLine="Public Sub CheckedList As List";
 //BA.debugLineNum = 830;BA.debugLine="Return MakeCheckedList(0)";
if (true) return _makecheckedlist((int) (0));
 //BA.debugLineNum = 831;BA.debugLine="End Sub";
return null;
}
public anywheresoftware.b4a.objects.collections.List  _checkedpanels() throws Exception{
 //BA.debugLineNum = 834;BA.debugLine="Public Sub CheckedPanels As List";
 //BA.debugLineNum = 835;BA.debugLine="Return MakeCheckedList(1)";
if (true) return _makecheckedlist((int) (1));
 //BA.debugLineNum = 836;BA.debugLine="End Sub";
return null;
}
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 2;BA.debugLine="Sub Class_Globals";
 //BA.debugLineNum = 4;BA.debugLine="Public BackgroundColor As Int";
_backgroundcolor = 0;
 //BA.debugLineNum = 5;BA.debugLine="Public CheckedColor As Int";
_checkedcolor = 0;
 //BA.debugLineNum = 6;BA.debugLine="Public DividerColor As Int";
_dividercolor = 0;
 //BA.debugLineNum = 7;BA.debugLine="Public ExtensionColor As Int";
_extensioncolor = 0;
 //BA.debugLineNum = 8;BA.debugLine="Public FilterResult As Boolean";
_filterresult = false;
 //BA.debugLineNum = 10;BA.debugLine="Private sv As ScrollView2D";
_sv = new flm.b4a.scrollview2d.ScrollView2DWrapper();
 //BA.debugLineNum = 11;BA.debugLine="Private svHeight As Int";
_svheight = 0;
 //BA.debugLineNum = 12;BA.debugLine="Private ExtensionIndex As Int";
_extensionindex = 0;
 //BA.debugLineNum = 13;BA.debugLine="Private ExtensionView As View";
_extensionview = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
 //BA.debugLineNum = 14;BA.debugLine="Private CallbackMod As Object";
_callbackmod = new Object();
 //BA.debugLineNum = 15;BA.debugLine="Private sub_Check, sub_Click, sub_LongClick As St";
_sub_check = "";
_sub_click = "";
_sub_longclick = "";
 //BA.debugLineNum = 16;BA.debugLine="Private sub_Paint As String";
_sub_paint = "";
 //BA.debugLineNum = 17;BA.debugLine="Private ModeCheck As Boolean";
_modecheck = false;
 //BA.debugLineNum = 18;BA.debugLine="Private BlockCheckEvent As Boolean";
_blockcheckevent = false;
 //BA.debugLineNum = 19;BA.debugLine="Private PressedPanel As Panel";
_pressedpanel = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private PressedDrawable As Object";
_presseddrawable = new Object();
 //BA.debugLineNum = 21;BA.debugLine="Private OldBackground As Object";
_oldbackground = new Object();
 //BA.debugLineNum = 22;BA.debugLine="Private DividerSize As Int";
_dividersize = 0;
 //BA.debugLineNum = 24;BA.debugLine="Private DragAndDrop, DragAndDropShadow As Panel";
_draganddrop = new anywheresoftware.b4a.objects.PanelWrapper();
_draganddropshadow = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private OriginDDTop, LastY As Int";
_originddtop = 0;
_lasty = 0;
 //BA.debugLineNum = 26;BA.debugLine="Private sub_AfterDrop As String";
_sub_afterdrop = "";
 //BA.debugLineNum = 28;BA.debugLine="Private ChkBoxSize  As Int: ChkBoxSize  = 40dip";
_chkboxsize = 0;
 //BA.debugLineNum = 28;BA.debugLine="Private ChkBoxSize  As Int: ChkBoxSize  = 40dip";
_chkboxsize = __c.DipToCurrent((int) (40));
 //BA.debugLineNum = 29;BA.debugLine="Private BitmapSize As Int: BitmapSize = 50dip";
_bitmapsize = 0;
 //BA.debugLineNum = 29;BA.debugLine="Private BitmapSize As Int: BitmapSize = 50dip";
_bitmapsize = __c.DipToCurrent((int) (50));
 //BA.debugLineNum = 30;BA.debugLine="Private LabelHeight  As Int: LabelHeight  = 25dip";
_labelheight = 0;
 //BA.debugLineNum = 30;BA.debugLine="Private LabelHeight  As Int: LabelHeight  = 25dip";
_labelheight = __c.DipToCurrent((int) (25));
 //BA.debugLineNum = 31;BA.debugLine="Private PanelHeight As Int: PanelHeight = 2 * Lab";
_panelheight = 0;
 //BA.debugLineNum = 31;BA.debugLine="Private PanelHeight As Int: PanelHeight = 2 * Lab";
_panelheight = (int) (2*_labelheight);
 //BA.debugLineNum = 32;BA.debugLine="Private PAINT_DEFAULT As Int: PAINT_DEFAULT = 0";
_paint_default = 0;
 //BA.debugLineNum = 32;BA.debugLine="Private PAINT_DEFAULT As Int: PAINT_DEFAULT = 0";
_paint_default = (int) (0);
 //BA.debugLineNum = 33;BA.debugLine="Private PAINT_EXTENDED As Int: PAINT_EXTENDED = 1";
_paint_extended = 0;
 //BA.debugLineNum = 33;BA.debugLine="Private PAINT_EXTENDED As Int: PAINT_EXTENDED = 1";
_paint_extended = (int) (1);
 //BA.debugLineNum = 34;BA.debugLine="Private PAINT_CHECKED As Int: PAINT_CHECKED = 2";
_paint_checked = 0;
 //BA.debugLineNum = 34;BA.debugLine="Private PAINT_CHECKED As Int: PAINT_CHECKED = 2";
_paint_checked = (int) (2);
 //BA.debugLineNum = 35;BA.debugLine="Private PAINT_PRESSED As Int: PAINT_PRESSED = 4";
_paint_pressed = 0;
 //BA.debugLineNum = 35;BA.debugLine="Private PAINT_PRESSED As Int: PAINT_PRESSED = 4";
_paint_pressed = (int) (4);
 //BA.debugLineNum = 37;BA.debugLine="Type typSortData1(Index As Int, Value As Object)";
;
 //BA.debugLineNum = 38;BA.debugLine="End Sub";
return "";
}
public String  _collapseitem() throws Exception{
int _extensionheight = 0;
anywheresoftware.b4a.objects.PanelWrapper _pnl = null;
anywheresoftware.b4a.objects.PanelWrapper _pnldivider = null;
int _i = 0;
 //BA.debugLineNum = 981;BA.debugLine="Public Sub CollapseItem";
 //BA.debugLineNum = 982;BA.debugLine="If ExtensionIndex <> -1 Then";
if (_extensionindex!=-1) { 
 //BA.debugLineNum = 983;BA.debugLine="Dim ExtensionHeight As Int";
_extensionheight = 0;
 //BA.debugLineNum = 984;BA.debugLine="ExtensionHeight = ExtensionView.Height";
_extensionheight = _extensionview.getHeight();
 //BA.debugLineNum = 985;BA.debugLine="ExtensionView.RemoveView";
_extensionview.RemoveView();
 //BA.debugLineNum = 986;BA.debugLine="Dim pnl As Panel";
_pnl = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 987;BA.debugLine="pnl = sv.Panel.GetView(ExtensionIndex)";
_pnl.setObject((android.view.ViewGroup)(_sv.getPanel().GetView(_extensionindex).getObject()));
 //BA.debugLineNum = 988;BA.debugLine="PaintBackground(pnl, False)";
_paintbackground(_pnl,__c.False);
 //BA.debugLineNum = 989;BA.debugLine="pnl.Height = pnl.Height - ExtensionHeight";
_pnl.setHeight((int) (_pnl.getHeight()-_extensionheight));
 //BA.debugLineNum = 990;BA.debugLine="If DividerSize > 0 Then";
if (_dividersize>0) { 
 //BA.debugLineNum = 992;BA.debugLine="Dim pnlDivider As Panel";
_pnldivider = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 993;BA.debugLine="pnlDivider = pnl.GetView(pnl.NumberOfViews - 1)";
_pnldivider.setObject((android.view.ViewGroup)(_pnl.GetView((int) (_pnl.getNumberOfViews()-1)).getObject()));
 //BA.debugLineNum = 994;BA.debugLine="pnlDivider.Top = pnl.Height - DividerSize";
_pnldivider.setTop((int) (_pnl.getHeight()-_dividersize));
 };
 //BA.debugLineNum = 996;BA.debugLine="svHeight = svHeight - ExtensionHeight";
_svheight = (int) (_svheight-_extensionheight);
 //BA.debugLineNum = 997;BA.debugLine="sv.Panel.Height = svHeight";
_sv.getPanel().setHeight(_svheight);
 //BA.debugLineNum = 998;BA.debugLine="For i = ExtensionIndex + 1 To sv.Panel.NumberOfV";
{
final int step16 = 1;
final int limit16 = (int) (_sv.getPanel().getNumberOfViews()-1);
_i = (int) (_extensionindex+1) ;
for (;(step16 > 0 && _i <= limit16) || (step16 < 0 && _i >= limit16) ;_i = ((int)(0 + _i + step16))  ) {
 //BA.debugLineNum = 999;BA.debugLine="pnl = sv.Panel.GetView(i)";
_pnl.setObject((android.view.ViewGroup)(_sv.getPanel().GetView(_i).getObject()));
 //BA.debugLineNum = 1000;BA.debugLine="pnl.Top = pnl.Top - ExtensionHeight";
_pnl.setTop((int) (_pnl.getTop()-_extensionheight));
 }
};
 //BA.debugLineNum = 1002;BA.debugLine="ExtensionIndex = -1";
_extensionindex = (int) (-1);
 };
 //BA.debugLineNum = 1004;BA.debugLine="ExtensionView = Null";
_extensionview.setObject((android.view.View)(__c.Null));
 //BA.debugLineNum = 1005;BA.debugLine="End Sub";
return "";
}
public String  _commonjump(int _newposition) throws Exception{
int _previousposition = 0;
 //BA.debugLineNum = 735;BA.debugLine="Private Sub CommonJump(NewPosition As Int)";
 //BA.debugLineNum = 737;BA.debugLine="NewPosition = Max(0, NewPosition - 5dip)";
_newposition = (int) (__c.Max(0,_newposition-__c.DipToCurrent((int) (5))));
 //BA.debugLineNum = 740;BA.debugLine="Dim PreviousPosition As Int: PreviousPosition = -";
_previousposition = 0;
 //BA.debugLineNum = 740;BA.debugLine="Dim PreviousPosition As Int: PreviousPosition = -";
_previousposition = (int) (-1);
 //BA.debugLineNum = 741;BA.debugLine="Do Until PreviousPosition = sv.VerticalScrollPosi";
while (!(_previousposition==_sv.getVerticalScrollPosition())) {
 //BA.debugLineNum = 742;BA.debugLine="PreviousPosition = sv.VerticalScrollPosition";
_previousposition = _sv.getVerticalScrollPosition();
 //BA.debugLineNum = 743;BA.debugLine="sv.VerticalScrollPosition = NewPosition";
_sv.setVerticalScrollPosition(_newposition);
 //BA.debugLineNum = 744;BA.debugLine="DoEvents";
__c.DoEvents();
 }
;
 //BA.debugLineNum = 746;BA.debugLine="End Sub";
return "";
}
public anywheresoftware.b4a.objects.collections.List  _createcheckedstatelist(int _firstposition,int _lastposition) throws Exception{
anywheresoftware.b4a.objects.collections.List _liste = null;
anywheresoftware.b4a.objects.PanelWrapper _pnl = null;
anywheresoftware.b4a.objects.ConcreteViewWrapper _v = null;
anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _c = null;
int _i = 0;
int _p = 0;
 //BA.debugLineNum = 542;BA.debugLine="Public Sub CreateCheckedStateList(FirstPosition As";
 //BA.debugLineNum = 543;BA.debugLine="Dim Liste As List: Liste.Initialize";
_liste = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 543;BA.debugLine="Dim Liste As List: Liste.Initialize";
_liste.Initialize();
 //BA.debugLineNum = 544;BA.debugLine="If sv.IsInitialized Then";
if (_sv.IsInitialized()) { 
 //BA.debugLineNum = 545;BA.debugLine="Dim pnl As Panel, v As View, c As CheckBox";
_pnl = new anywheresoftware.b4a.objects.PanelWrapper();
_v = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
_c = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 546;BA.debugLine="For i = FirstPosition To LastPosition";
{
final int step5 = 1;
final int limit5 = _lastposition;
_i = _firstposition ;
for (;(step5 > 0 && _i <= limit5) || (step5 < 0 && _i >= limit5) ;_i = ((int)(0 + _i + step5))  ) {
 //BA.debugLineNum = 547;BA.debugLine="pnl = sv.Panel.GetView(i)";
_pnl.setObject((android.view.ViewGroup)(_sv.getPanel().GetView(_i).getObject()));
 //BA.debugLineNum = 548;BA.debugLine="For p = 0 To pnl.NumberOfViews - 1";
{
final int step7 = 1;
final int limit7 = (int) (_pnl.getNumberOfViews()-1);
_p = (int) (0) ;
for (;(step7 > 0 && _p <= limit7) || (step7 < 0 && _p >= limit7) ;_p = ((int)(0 + _p + step7))  ) {
 //BA.debugLineNum = 549;BA.debugLine="v = pnl.GetView(p)";
_v = _pnl.GetView(_p);
 //BA.debugLineNum = 550;BA.debugLine="If IsCheckBox(v) Then";
if (_ischeckbox(_v)) { 
 //BA.debugLineNum = 551;BA.debugLine="c = v";
_c.setObject((android.widget.CheckBox)(_v.getObject()));
 //BA.debugLineNum = 552;BA.debugLine="Liste.Add(c.Checked)";
_liste.Add((Object)(_c.getChecked()));
 //BA.debugLineNum = 553;BA.debugLine="Exit";
if (true) break;
 };
 }
};
 }
};
 };
 //BA.debugLineNum = 558;BA.debugLine="Return Liste";
if (true) return _liste;
 //BA.debugLineNum = 559;BA.debugLine="End Sub";
return null;
}
public anywheresoftware.b4a.objects.collections.List  _createintegerlist(int _viewindexinpanel,int _firstposition,int _lastposition) throws Exception{
 //BA.debugLineNum = 537;BA.debugLine="Public Sub CreateIntegerList(ViewIndexInPanel As I";
 //BA.debugLineNum = 538;BA.debugLine="Return MakeValuesListWith(ViewIndexInPanel, First";
if (true) return _makevalueslistwith(_viewindexinpanel,_firstposition,_lastposition,(int) (2));
 //BA.debugLineNum = 539;BA.debugLine="End Sub";
return null;
}
public anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper  _createscaledbitmap(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _original,int _width,int _height) throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _b = null;
 //BA.debugLineNum = 80;BA.debugLine="Private Sub CreateScaledBitmap(Original As Bitmap,";
 //BA.debugLineNum = 81;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 82;BA.debugLine="Dim b As Bitmap";
_b = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 83;BA.debugLine="b = r.RunStaticMethod(\"android.graphics.Bitmap\",";
_b.setObject((android.graphics.Bitmap)(_r.RunStaticMethod("android.graphics.Bitmap","createScaledBitmap",new Object[]{(Object)(_original.getObject()),(Object)(_width),(Object)(_height),(Object)(__c.True)},new String[]{"android.graphics.Bitmap","java.lang.int","java.lang.int","java.lang.boolean"})));
 //BA.debugLineNum = 86;BA.debugLine="Return b";
if (true) return _b;
 //BA.debugLineNum = 87;BA.debugLine="End Sub";
return null;
}
public anywheresoftware.b4a.objects.collections.List  _createtaglist(int _viewindexinpanel,int _firstposition,int _lastposition) throws Exception{
 //BA.debugLineNum = 525;BA.debugLine="Public Sub CreateTagList(ViewIndexInPanel As Int,";
 //BA.debugLineNum = 526;BA.debugLine="Return MakeValuesListWith(ViewIndexInPanel, First";
if (true) return _makevalueslistwith(_viewindexinpanel,_firstposition,_lastposition,(int) (0));
 //BA.debugLineNum = 527;BA.debugLine="End Sub";
return null;
}
public anywheresoftware.b4a.objects.collections.List  _createtextlist(int _viewindexinpanel,int _firstposition,int _lastposition) throws Exception{
 //BA.debugLineNum = 531;BA.debugLine="Public Sub CreateTextList(ViewIndexInPanel As Int,";
 //BA.debugLineNum = 532;BA.debugLine="Return MakeValuesListWith(ViewIndexInPanel, First";
if (true) return _makevalueslistwith(_viewindexinpanel,_firstposition,_lastposition,(int) (1));
 //BA.debugLineNum = 533;BA.debugLine="End Sub";
return null;
}
public Object  _extendeditemid() throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _pnl = null;
 //BA.debugLineNum = 1014;BA.debugLine="Public Sub ExtendedItemID As Object";
 //BA.debugLineNum = 1015;BA.debugLine="If ExtensionView.IsInitialized Then";
if (_extensionview.IsInitialized()) { 
 //BA.debugLineNum = 1016;BA.debugLine="Dim pnl As Panel";
_pnl = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 1017;BA.debugLine="pnl = FindPanelContaining(ExtensionView)";
_pnl = _findpanelcontaining(_extensionview);
 //BA.debugLineNum = 1018;BA.debugLine="Return pnl.Tag";
if (true) return _pnl.getTag();
 }else {
 //BA.debugLineNum = 1020;BA.debugLine="Return Null";
if (true) return __c.Null;
 };
 //BA.debugLineNum = 1022;BA.debugLine="End Sub";
return null;
}
public String  _extenditem(Object _itemid,anywheresoftware.b4a.objects.ConcreteViewWrapper _addedcontent,int _contentheight) throws Exception{
boolean _movefollowingpanels = false;
anywheresoftware.b4a.objects.PanelWrapper _pnl = null;
int _i = 0;
anywheresoftware.b4a.objects.PanelWrapper _pnldivider = null;
int _contentbottom = 0;
int _newposition = 0;
 //BA.debugLineNum = 931;BA.debugLine="Public Sub ExtendItem(ItemID As Object, AddedConte";
 //BA.debugLineNum = 932;BA.debugLine="If AddedContent.IsInitialized Then";
if (_addedcontent.IsInitialized()) { 
 //BA.debugLineNum = 933;BA.debugLine="If HasExtraContent Then CollapseItem";
if (_hasextracontent()) { 
_collapseitem();};
 //BA.debugLineNum = 934;BA.debugLine="Dim MoveFollowingPanels As Boolean";
_movefollowingpanels = false;
 //BA.debugLineNum = 935;BA.debugLine="MoveFollowingPanels = False";
_movefollowingpanels = __c.False;
 //BA.debugLineNum = 936;BA.debugLine="Dim pnl As Panel";
_pnl = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 937;BA.debugLine="For i = 0 To sv.Panel.NumberOfViews - 1";
{
final int step6 = 1;
final int limit6 = (int) (_sv.getPanel().getNumberOfViews()-1);
_i = (int) (0) ;
for (;(step6 > 0 && _i <= limit6) || (step6 < 0 && _i >= limit6) ;_i = ((int)(0 + _i + step6))  ) {
 //BA.debugLineNum = 938;BA.debugLine="pnl = sv.Panel.GetView(i)";
_pnl.setObject((android.view.ViewGroup)(_sv.getPanel().GetView(_i).getObject()));
 //BA.debugLineNum = 939;BA.debugLine="If MoveFollowingPanels Then";
if (_movefollowingpanels) { 
 //BA.debugLineNum = 940;BA.debugLine="pnl.Top = pnl.Top + ContentHeight";
_pnl.setTop((int) (_pnl.getTop()+_contentheight));
 }else if(_pnl.getTag()== null) { 
 //BA.debugLineNum = 942;BA.debugLine="Continue ' It's a header";
if (true) continue;
 }else if((_pnl.getTag()).equals(_itemid)) { 
 //BA.debugLineNum = 944;BA.debugLine="ExtensionIndex = i";
_extensionindex = _i;
 //BA.debugLineNum = 945;BA.debugLine="ExtensionView = AddedContent";
_extensionview = _addedcontent;
 //BA.debugLineNum = 946;BA.debugLine="PaintBackground(pnl, True)";
_paintbackground(_pnl,__c.True);
 //BA.debugLineNum = 947;BA.debugLine="If DividerSize > 0 Then";
if (_dividersize>0) { 
 //BA.debugLineNum = 949;BA.debugLine="Dim pnlDivider As Panel";
_pnldivider = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 950;BA.debugLine="pnlDivider = pnl.GetView(pnl.NumberOfViews -";
_pnldivider.setObject((android.view.ViewGroup)(_pnl.GetView((int) (_pnl.getNumberOfViews()-1)).getObject()));
 //BA.debugLineNum = 951;BA.debugLine="pnlDivider.Top = pnl.Height + ContentHeight -";
_pnldivider.setTop((int) (_pnl.getHeight()+_contentheight-_dividersize));
 };
 //BA.debugLineNum = 953;BA.debugLine="If IsCheckBox(pnl.GetView(0)) Then";
if (_ischeckbox(_pnl.GetView((int) (0)))) { 
 //BA.debugLineNum = 955;BA.debugLine="pnl.AddView(AddedContent, ChkBoxSize, pnl.Hei";
_pnl.AddView((android.view.View)(_addedcontent.getObject()),_chkboxsize,(int) (_pnl.getHeight()-_dividersize),(int) ((double)(Double.parseDouble(_getwidth()))-_chkboxsize),_contentheight);
 }else {
 //BA.debugLineNum = 957;BA.debugLine="pnl.AddView(AddedContent, 0, pnl.Height - Div";
_pnl.AddView((android.view.View)(_addedcontent.getObject()),(int) (0),(int) (_pnl.getHeight()-_dividersize),(int)(Double.parseDouble(_getwidth())),_contentheight);
 };
 //BA.debugLineNum = 959;BA.debugLine="pnl.Height = pnl.Height + ContentHeight";
_pnl.setHeight((int) (_pnl.getHeight()+_contentheight));
 //BA.debugLineNum = 960;BA.debugLine="svHeight = svHeight + ContentHeight";
_svheight = (int) (_svheight+_contentheight);
 //BA.debugLineNum = 961;BA.debugLine="sv.Panel.Height = svHeight";
_sv.getPanel().setHeight(_svheight);
 //BA.debugLineNum = 963;BA.debugLine="Dim ContentBottom As Int";
_contentbottom = 0;
 //BA.debugLineNum = 964;BA.debugLine="ContentBottom = pnl.Top + pnl.Height";
_contentbottom = (int) (_pnl.getTop()+_pnl.getHeight());
 //BA.debugLineNum = 965;BA.debugLine="If ContentBottom > sv.VerticalScrollPosition +";
if (_contentbottom>_sv.getVerticalScrollPosition()+(double)(Double.parseDouble(_getheight()))) { 
 //BA.debugLineNum = 967;BA.debugLine="Dim NewPosition As Int";
_newposition = 0;
 //BA.debugLineNum = 968;BA.debugLine="DoEvents";
__c.DoEvents();
 //BA.debugLineNum = 969;BA.debugLine="NewPosition = sv.VerticalScrollPosition + Min";
_newposition = (int) (_sv.getVerticalScrollPosition()+__c.Min(_contentbottom-_sv.getVerticalScrollPosition()-(double)(Double.parseDouble(_getheight())),(double)(Double.parseDouble(_getheight())))+__c.DipToCurrent((int) (15)));
 //BA.debugLineNum = 970;BA.debugLine="CommonJump(NewPosition)";
_commonjump(_newposition);
 };
 //BA.debugLineNum = 973;BA.debugLine="AddedContent.RequestFocus";
_addedcontent.RequestFocus();
 //BA.debugLineNum = 974;BA.debugLine="MoveFollowingPanels = True";
_movefollowingpanels = __c.True;
 };
 }
};
 };
 //BA.debugLineNum = 978;BA.debugLine="End Sub";
return "";
}
public int  _filter(int _viewindexinpanel,String _comparisonfunction,int _filtertype) throws Exception{
anywheresoftware.b4a.objects.collections.List _l = null;
int _firstpos = 0;
int _count = 0;
String _s = "";
anywheresoftware.b4a.objects.PanelWrapper _pnl = null;
int _i = 0;
 //BA.debugLineNum = 1186;BA.debugLine="Private Sub Filter(ViewIndexInPanel As Int, Compar";
 //BA.debugLineNum = 1191;BA.debugLine="If Not(SubExists(CallbackMod, ComparisonFunction)";
if (__c.Not(__c.SubExists(ba,_callbackmod,_comparisonfunction))) { 
 //BA.debugLineNum = 1192;BA.debugLine="Log(ComparisonFunction & \" not found\")";
__c.Log(_comparisonfunction+" not found");
 //BA.debugLineNum = 1193;BA.debugLine="Return 0";
if (true) return (int) (0);
 };
 //BA.debugLineNum = 1195;BA.debugLine="Dim L As List";
_l = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 1196;BA.debugLine="If FilterType = 0 Then";
if (_filtertype==0) { 
 //BA.debugLineNum = 1197;BA.debugLine="L = CreateTagList(ViewIndexInPanel, 0, NumberOfI";
_l = _createtaglist(_viewindexinpanel,(int) (0),(int) ((double)(Double.parseDouble(_numberofitems()))-1));
 }else {
 //BA.debugLineNum = 1199;BA.debugLine="L = CreateTextList(ViewIndexInPanel, 0, NumberOf";
_l = _createtextlist(_viewindexinpanel,(int) (0),(int) ((double)(Double.parseDouble(_numberofitems()))-1));
 };
 //BA.debugLineNum = 1201;BA.debugLine="Dim FirstPos, Count As Int";
_firstpos = 0;
_count = 0;
 //BA.debugLineNum = 1202;BA.debugLine="FirstPos = -1";
_firstpos = (int) (-1);
 //BA.debugLineNum = 1203;BA.debugLine="Dim S As String, pnl As Panel";
_s = "";
_pnl = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 1204;BA.debugLine="For i = 0 To L.Size - 1";
{
final int step14 = 1;
final int limit14 = (int) (_l.getSize()-1);
_i = (int) (0) ;
for (;(step14 > 0 && _i <= limit14) || (step14 < 0 && _i >= limit14) ;_i = ((int)(0 + _i + step14))  ) {
 //BA.debugLineNum = 1205;BA.debugLine="S = L.Get(i)";
_s = BA.ObjectToString(_l.Get(_i));
 //BA.debugLineNum = 1206;BA.debugLine="pnl = sv.Panel.GetView(i)";
_pnl.setObject((android.view.ViewGroup)(_sv.getPanel().GetView(_i).getObject()));
 //BA.debugLineNum = 1207;BA.debugLine="CallSub3(CallbackMod, ComparisonFunction, pnl.Ta";
__c.CallSubNew3(ba,_callbackmod,_comparisonfunction,_pnl.getTag(),(Object)(_s));
 //BA.debugLineNum = 1208;BA.debugLine="If FilterResult = True Then";
if (_filterresult==__c.True) { 
 //BA.debugLineNum = 1209;BA.debugLine="Count = Count + 1";
_count = (int) (_count+1);
 //BA.debugLineNum = 1210;BA.debugLine="If pnl.Visible = False Then";
if (_pnl.getVisible()==__c.False) { 
 //BA.debugLineNum = 1211;BA.debugLine="If FirstPos = -1 Then FirstPos = i";
if (_firstpos==-1) { 
_firstpos = _i;};
 //BA.debugLineNum = 1212;BA.debugLine="pnl.Visible = True";
_pnl.setVisible(__c.True);
 };
 }else {
 //BA.debugLineNum = 1215;BA.debugLine="If FirstPos = -1 Then FirstPos = i";
if (_firstpos==-1) { 
_firstpos = _i;};
 //BA.debugLineNum = 1216;BA.debugLine="pnl.Visible = False";
_pnl.setVisible(__c.False);
 };
 }
};
 //BA.debugLineNum = 1219;BA.debugLine="If FirstPos > -1 Then MoveTopAfterFiltering(First";
if (_firstpos>-1) { 
_movetopafterfiltering(_firstpos);};
 //BA.debugLineNum = 1220;BA.debugLine="Return Count";
if (true) return _count;
 //BA.debugLineNum = 1221;BA.debugLine="End Sub";
return 0;
}
public int  _filtertag(int _viewindexinpanel,String _comparisonfunction) throws Exception{
 //BA.debugLineNum = 1227;BA.debugLine="Public Sub FilterTag(ViewIndexInPanel As Int, Comp";
 //BA.debugLineNum = 1228;BA.debugLine="Return Filter(ViewIndexInPanel, ComparisonFunctio";
if (true) return _filter(_viewindexinpanel,_comparisonfunction,(int) (0));
 //BA.debugLineNum = 1229;BA.debugLine="End Sub";
return 0;
}
public int  _filtertext(int _viewindexinpanel,String _comparisonfunction) throws Exception{
 //BA.debugLineNum = 1235;BA.debugLine="Public Sub FilterText(ViewIndexInPanel As Int, Com";
 //BA.debugLineNum = 1236;BA.debugLine="Return Filter(ViewIndexInPanel, ComparisonFunctio";
if (true) return _filter(_viewindexinpanel,_comparisonfunction,(int) (1));
 //BA.debugLineNum = 1237;BA.debugLine="End Sub";
return 0;
}
public anywheresoftware.b4a.objects.PanelWrapper  _findpanelcontaining(anywheresoftware.b4a.objects.ConcreteViewWrapper _myview) throws Exception{
 //BA.debugLineNum = 714;BA.debugLine="Public Sub FindPanelContaining(MyView As View) As";
 //BA.debugLineNum = 715;BA.debugLine="If sv.IsInitialized Then";
if (_sv.IsInitialized()) { 
 //BA.debugLineNum = 716;BA.debugLine="Return GetParentPanel(MyView)";
if (true) return (anywheresoftware.b4a.objects.PanelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.PanelWrapper(), (android.view.ViewGroup)(_getparentpanel(_myview).getObject()));
 };
 //BA.debugLineNum = 718;BA.debugLine="End Sub";
return null;
}
public Object  _findpanelwithid(Object _id) throws Exception{
int _i = 0;
anywheresoftware.b4a.objects.PanelWrapper _pnl = null;
 //BA.debugLineNum = 723;BA.debugLine="Public Sub FindPanelWithID(ID As Object) As Object";
 //BA.debugLineNum = 724;BA.debugLine="If sv.IsInitialized Then";
if (_sv.IsInitialized()) { 
 //BA.debugLineNum = 725;BA.debugLine="For i = 0 To NumberOfItems - 1";
{
final int step2 = 1;
final int limit2 = (int) ((double)(Double.parseDouble(_numberofitems()))-1);
_i = (int) (0) ;
for (;(step2 > 0 && _i <= limit2) || (step2 < 0 && _i >= limit2) ;_i = ((int)(0 + _i + step2))  ) {
 //BA.debugLineNum = 726;BA.debugLine="Dim pnl As Panel";
_pnl = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 727;BA.debugLine="pnl = sv.Panel.GetView(i)";
_pnl.setObject((android.view.ViewGroup)(_sv.getPanel().GetView(_i).getObject()));
 //BA.debugLineNum = 728;BA.debugLine="If pnl.Tag = ID Then Return pnl";
if ((_pnl.getTag()).equals(_id)) { 
if (true) return (Object)(_pnl.getObject());};
 }
};
 };
 //BA.debugLineNum = 731;BA.debugLine="Return Null";
if (true) return __c.Null;
 //BA.debugLineNum = 732;BA.debugLine="End Sub";
return null;
}
public String  _getfirsttext(Object _id) throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _pnl = null;
int _i = 0;
int _p = 0;
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
 //BA.debugLineNum = 680;BA.debugLine="Public Sub GetFirstText(ID As Object) As String";
 //BA.debugLineNum = 681;BA.debugLine="Dim pnl As Panel";
_pnl = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 682;BA.debugLine="For i = 0 To sv.Panel.NumberOfViews - 1";
{
final int step2 = 1;
final int limit2 = (int) (_sv.getPanel().getNumberOfViews()-1);
_i = (int) (0) ;
for (;(step2 > 0 && _i <= limit2) || (step2 < 0 && _i >= limit2) ;_i = ((int)(0 + _i + step2))  ) {
 //BA.debugLineNum = 683;BA.debugLine="pnl = sv.Panel.GetView(i)";
_pnl.setObject((android.view.ViewGroup)(_sv.getPanel().GetView(_i).getObject()));
 //BA.debugLineNum = 684;BA.debugLine="If pnl.Tag = Null Then Continue";
if (_pnl.getTag()== null) { 
if (true) continue;};
 //BA.debugLineNum = 685;BA.debugLine="If pnl.Tag = ID Then";
if ((_pnl.getTag()).equals(_id)) { 
 //BA.debugLineNum = 686;BA.debugLine="For p = 0 To pnl.NumberOfViews - 1";
{
final int step6 = 1;
final int limit6 = (int) (_pnl.getNumberOfViews()-1);
_p = (int) (0) ;
for (;(step6 > 0 && _p <= limit6) || (step6 < 0 && _p >= limit6) ;_p = ((int)(0 + _p + step6))  ) {
 //BA.debugLineNum = 687;BA.debugLine="If IsLabel(pnl.GetView(p)) Then";
if (_islabel(_pnl.GetView(_p))) { 
 //BA.debugLineNum = 688;BA.debugLine="Dim lbl As Label";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 689;BA.debugLine="lbl = pnl.GetView(p)";
_lbl.setObject((android.widget.TextView)(_pnl.GetView(_p).getObject()));
 //BA.debugLineNum = 690;BA.debugLine="Return lbl.Text";
if (true) return _lbl.getText();
 };
 }
};
 //BA.debugLineNum = 693;BA.debugLine="Exit";
if (true) break;
 };
 }
};
 //BA.debugLineNum = 696;BA.debugLine="Return \"\"";
if (true) return "";
 //BA.debugLineNum = 697;BA.debugLine="End Sub";
return "";
}
public String  _getheight() throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
int _hauteur = 0;
 //BA.debugLineNum = 653;BA.debugLine="Public Sub getHeight";
 //BA.debugLineNum = 654;BA.debugLine="If sv.Height < 0 Then";
if (_sv.getHeight()<0) { 
 //BA.debugLineNum = 655;BA.debugLine="Dim r As Reflector, Hauteur As Int";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
_hauteur = 0;
 //BA.debugLineNum = 656;BA.debugLine="r.Target = sv";
_r.Target = (Object)(_sv.getObject());
 //BA.debugLineNum = 657;BA.debugLine="Hauteur = r.RunMethod(\"getHeight\")";
_hauteur = (int)(BA.ObjectToNumber(_r.RunMethod("getHeight")));
 //BA.debugLineNum = 658;BA.debugLine="If Hauteur = 0 Then";
if (_hauteur==0) { 
 //BA.debugLineNum = 659;BA.debugLine="DoEvents";
__c.DoEvents();
 //BA.debugLineNum = 660;BA.debugLine="Hauteur = r.RunMethod(\"getHeight\")";
_hauteur = (int)(BA.ObjectToNumber(_r.RunMethod("getHeight")));
 };
 //BA.debugLineNum = 662;BA.debugLine="Return Hauteur";
if (true) return BA.NumberToString(_hauteur);
 }else {
 //BA.debugLineNum = 664;BA.debugLine="Return sv.Height";
if (true) return BA.NumberToString(_sv.getHeight());
 };
 //BA.debugLineNum = 666;BA.debugLine="End Sub";
return "";
}
public anywheresoftware.b4a.objects.ConcreteViewWrapper  _getparentpanel(anywheresoftware.b4a.objects.ConcreteViewWrapper _myview) throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
anywheresoftware.b4a.objects.ConcreteViewWrapper _v = null;
anywheresoftware.b4a.objects.ConcreteViewWrapper _parent = null;
 //BA.debugLineNum = 699;BA.debugLine="Private Sub GetParentPanel(MyView As View) As View";
 //BA.debugLineNum = 700;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 701;BA.debugLine="Dim v, Parent As View";
_v = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
_parent = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
 //BA.debugLineNum = 702;BA.debugLine="r.Target = MyView";
_r.Target = (Object)(_myview.getObject());
 //BA.debugLineNum = 703;BA.debugLine="v = r.Target";
_v.setObject((android.view.View)(_r.Target));
 //BA.debugLineNum = 704;BA.debugLine="Parent = r.RunMethod(\"getParent\")";
_parent.setObject((android.view.View)(_r.RunMethod("getParent")));
 //BA.debugLineNum = 705;BA.debugLine="Do While Parent <> sv.Panel";
while ((_parent).equals((android.view.View)(_sv.getPanel().getObject())) == false) {
 //BA.debugLineNum = 706;BA.debugLine="r.Target = Parent";
_r.Target = (Object)(_parent.getObject());
 //BA.debugLineNum = 707;BA.debugLine="v = r.Target";
_v.setObject((android.view.View)(_r.Target));
 //BA.debugLineNum = 708;BA.debugLine="Parent = r.RunMethod(\"getParent\")";
_parent.setObject((android.view.View)(_r.RunMethod("getParent")));
 }
;
 //BA.debugLineNum = 710;BA.debugLine="Return v";
if (true) return _v;
 //BA.debugLineNum = 711;BA.debugLine="End Sub";
return null;
}
public String  _getwidth() throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
int _largeur = 0;
 //BA.debugLineNum = 636;BA.debugLine="Public Sub getWidth";
 //BA.debugLineNum = 637;BA.debugLine="If sv.Width < 0 Then";
if (_sv.getWidth()<0) { 
 //BA.debugLineNum = 638;BA.debugLine="Dim r As Reflector, Largeur As Int";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
_largeur = 0;
 //BA.debugLineNum = 639;BA.debugLine="r.Target = sv";
_r.Target = (Object)(_sv.getObject());
 //BA.debugLineNum = 640;BA.debugLine="Largeur = r.RunMethod(\"getWidth\")";
_largeur = (int)(BA.ObjectToNumber(_r.RunMethod("getWidth")));
 //BA.debugLineNum = 641;BA.debugLine="If Largeur = 0 Then";
if (_largeur==0) { 
 //BA.debugLineNum = 642;BA.debugLine="DoEvents";
__c.DoEvents();
 //BA.debugLineNum = 643;BA.debugLine="Largeur = r.RunMethod(\"getWidth\")";
_largeur = (int)(BA.ObjectToNumber(_r.RunMethod("getWidth")));
 };
 //BA.debugLineNum = 645;BA.debugLine="Return Largeur";
if (true) return BA.NumberToString(_largeur);
 }else {
 //BA.debugLineNum = 647;BA.debugLine="Return sv.Width";
if (true) return BA.NumberToString(_sv.getWidth());
 };
 //BA.debugLineNum = 649;BA.debugLine="End Sub";
return "";
}
public boolean  _hasextracontent() throws Exception{
 //BA.debugLineNum = 1008;BA.debugLine="Public Sub HasExtraContent As Boolean";
 //BA.debugLineNum = 1009;BA.debugLine="Return (ExtensionIndex <> -1)";
if (true) return (_extensionindex!=-1);
 //BA.debugLineNum = 1010;BA.debugLine="End Sub";
return false;
}
public String  _initialize(anywheresoftware.b4a.BA _ba,Object _module,flm.b4a.scrollview2d.ScrollView2DWrapper _svlist,String _subcheck,String _subclick,String _sublongclick,int _dividerheight) throws Exception{
innerInitialize(_ba);
int _i = 0;
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
int _id_presseddrawable = 0;
 //BA.debugLineNum = 45;BA.debugLine="Public Sub Initialize(Module As Object, svList As";
 //BA.debugLineNum = 46;BA.debugLine="sv = svList";
_sv = _svlist;
 //BA.debugLineNum = 47;BA.debugLine="For i = sv.Panel.NumberOfViews-1 To 0 Step -1";
{
final int step2 = (int) (-1);
final int limit2 = (int) (0);
_i = (int) (_sv.getPanel().getNumberOfViews()-1) ;
for (;(step2 > 0 && _i <= limit2) || (step2 < 0 && _i >= limit2) ;_i = ((int)(0 + _i + step2))  ) {
 //BA.debugLineNum = 48;BA.debugLine="sv.Panel.RemoveViewAt(i)";
_sv.getPanel().RemoveViewAt(_i);
 }
};
 //BA.debugLineNum = 50;BA.debugLine="sv.Panel.Height = 0";
_sv.getPanel().setHeight((int) (0));
 //BA.debugLineNum = 51;BA.debugLine="svHeight = 0";
_svheight = (int) (0);
 //BA.debugLineNum = 52;BA.debugLine="CallbackMod = Module";
_callbackmod = _module;
 //BA.debugLineNum = 53;BA.debugLine="sub_Check = subCheck";
_sub_check = _subcheck;
 //BA.debugLineNum = 54;BA.debugLine="sub_Click = subClick";
_sub_click = _subclick;
 //BA.debugLineNum = 55;BA.debugLine="sub_LongClick = subLongClick";
_sub_longclick = _sublongclick;
 //BA.debugLineNum = 56;BA.debugLine="ModeCheck = (sub_Click = \"\")";
_modecheck = ((_sub_click).equals(""));
 //BA.debugLineNum = 57;BA.debugLine="BlockCheckEvent = False";
_blockcheckevent = __c.False;
 //BA.debugLineNum = 58;BA.debugLine="DividerSize = DividerHeight";
_dividersize = _dividerheight;
 //BA.debugLineNum = 59;BA.debugLine="DividerColor = Colors.ARGB(128, 200, 200, 200)";
_dividercolor = __c.Colors.ARGB((int) (128),(int) (200),(int) (200),(int) (200));
 //BA.debugLineNum = 60;BA.debugLine="BackgroundColor = Colors.Transparent";
_backgroundcolor = __c.Colors.Transparent;
 //BA.debugLineNum = 61;BA.debugLine="CheckedColor = Colors.Transparent";
_checkedcolor = __c.Colors.Transparent;
 //BA.debugLineNum = 62;BA.debugLine="ExtensionColor = Colors.Transparent";
_extensioncolor = __c.Colors.Transparent;
 //BA.debugLineNum = 63;BA.debugLine="ExtensionIndex = -1";
_extensionindex = (int) (-1);
 //BA.debugLineNum = 64;BA.debugLine="ExtensionView = Null";
_extensionview.setObject((android.view.View)(__c.Null));
 //BA.debugLineNum = 65;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 66;BA.debugLine="r.Target = r.GetContext";
_r.Target = (Object)(_r.GetContext(ba));
 //BA.debugLineNum = 67;BA.debugLine="r.Target = r.RunMethod(\"getResources\")";
_r.Target = _r.RunMethod("getResources");
 //BA.debugLineNum = 68;BA.debugLine="r.Target = r.RunMethod(\"getSystem\")";
_r.Target = _r.RunMethod("getSystem");
 //BA.debugLineNum = 69;BA.debugLine="Dim ID_PressedDrawable As Int";
_id_presseddrawable = 0;
 //BA.debugLineNum = 70;BA.debugLine="ID_PressedDrawable = r.RunMethod4(\"getIdentifier\"";
_id_presseddrawable = (int)(BA.ObjectToNumber(_r.RunMethod4("getIdentifier",new Object[]{(Object)("list_selector_background_pressed"),(Object)("drawable"),(Object)("android")},new String[]{"java.lang.String","java.lang.String","java.lang.String"})));
 //BA.debugLineNum = 72;BA.debugLine="r.Target = r.GetContext";
_r.Target = (Object)(_r.GetContext(ba));
 //BA.debugLineNum = 73;BA.debugLine="r.Target = r.RunMethod(\"getResources\")";
_r.Target = _r.RunMethod("getResources");
 //BA.debugLineNum = 74;BA.debugLine="PressedDrawable = r.RunMethod2(\"getDrawable\", ID_";
_presseddrawable = _r.RunMethod2("getDrawable",BA.NumberToString(_id_presseddrawable),"java.lang.int");
 //BA.debugLineNum = 75;BA.debugLine="DragAndDrop = Null";
_draganddrop.setObject((android.view.ViewGroup)(__c.Null));
 //BA.debugLineNum = 76;BA.debugLine="End Sub";
return "";
}
public boolean  _ischeckbox(anywheresoftware.b4a.objects.ConcreteViewWrapper _myview) throws Exception{
 //BA.debugLineNum = 798;BA.debugLine="Private Sub IsCheckBox(MyView As View) As Boolean";
 //BA.debugLineNum = 799;BA.debugLine="Return (GetType(MyView) = \"android.widget.CheckBo";
if (true) return ((__c.GetType((Object)(_myview.getObject()))).equals("android.widget.CheckBox"));
 //BA.debugLineNum = 800;BA.debugLine="End Sub";
return false;
}
public boolean  _isedittext(anywheresoftware.b4a.objects.ConcreteViewWrapper _myview) throws Exception{
 //BA.debugLineNum = 460;BA.debugLine="Private Sub IsEditText(MyView As View) As Boolean";
 //BA.debugLineNum = 461;BA.debugLine="Return (GetType(MyView) = \"android.widget.EditTex";
if (true) return ((__c.GetType((Object)(_myview.getObject()))).equals("android.widget.EditText"));
 //BA.debugLineNum = 462;BA.debugLine="End Sub";
return false;
}
public boolean  _isfiltered(int _position) throws Exception{
 //BA.debugLineNum = 1240;BA.debugLine="Public Sub IsFiltered(Position As Int) As Boolean";
 //BA.debugLineNum = 1241;BA.debugLine="Return sv.Panel.GetView(Position).Visible";
if (true) return _sv.getPanel().GetView(_position).getVisible();
 //BA.debugLineNum = 1242;BA.debugLine="End Sub";
return false;
}
public boolean  _islabel(anywheresoftware.b4a.objects.ConcreteViewWrapper _myview) throws Exception{
 //BA.debugLineNum = 464;BA.debugLine="Private Sub IsLabel(MyView As View) As Boolean";
 //BA.debugLineNum = 465;BA.debugLine="Return (GetType(MyView) = \"android.widget.TextVie";
if (true) return ((__c.GetType((Object)(_myview.getObject()))).equals("android.widget.TextView"));
 //BA.debugLineNum = 466;BA.debugLine="End Sub";
return false;
}
public anywheresoftware.b4a.objects.PanelWrapper  _jumptoitem(Object _id) throws Exception{
int _totalheight = 0;
int _i = 0;
anywheresoftware.b4a.objects.PanelWrapper _pnl = null;
 //BA.debugLineNum = 767;BA.debugLine="Public Sub JumpToItem(ID As Object) As Panel";
 //BA.debugLineNum = 768;BA.debugLine="If sv.IsInitialized Then";
if (_sv.IsInitialized()) { 
 //BA.debugLineNum = 769;BA.debugLine="Dim TotalHeight As Int";
_totalheight = 0;
 //BA.debugLineNum = 770;BA.debugLine="For i = 0 To NumberOfItems - 1";
{
final int step3 = 1;
final int limit3 = (int) ((double)(Double.parseDouble(_numberofitems()))-1);
_i = (int) (0) ;
for (;(step3 > 0 && _i <= limit3) || (step3 < 0 && _i >= limit3) ;_i = ((int)(0 + _i + step3))  ) {
 //BA.debugLineNum = 771;BA.debugLine="Dim pnl As Panel";
_pnl = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 772;BA.debugLine="pnl = sv.Panel.GetView(i)";
_pnl.setObject((android.view.ViewGroup)(_sv.getPanel().GetView(_i).getObject()));
 //BA.debugLineNum = 773;BA.debugLine="If pnl.Tag <> Null Then";
if (_pnl.getTag()!= null) { 
 //BA.debugLineNum = 774;BA.debugLine="If pnl.Tag = ID Then";
if ((_pnl.getTag()).equals(_id)) { 
 //BA.debugLineNum = 775;BA.debugLine="CommonJump(TotalHeight)";
_commonjump(_totalheight);
 //BA.debugLineNum = 776;BA.debugLine="Return pnl";
if (true) return _pnl;
 };
 };
 //BA.debugLineNum = 779;BA.debugLine="TotalHeight = TotalHeight + pnl.Height";
_totalheight = (int) (_totalheight+_pnl.getHeight());
 }
};
 };
 //BA.debugLineNum = 782;BA.debugLine="Return Null";
if (true) return (anywheresoftware.b4a.objects.PanelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.PanelWrapper(), (android.view.ViewGroup)(__c.Null));
 //BA.debugLineNum = 783;BA.debugLine="End Sub";
return null;
}
public anywheresoftware.b4a.objects.PanelWrapper  _jumptoposition(int _position) throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _pnl = null;
 //BA.debugLineNum = 752;BA.debugLine="Public Sub JumpToPosition(Position As Int) As Pane";
 //BA.debugLineNum = 753;BA.debugLine="If sv.IsInitialized Then";
if (_sv.IsInitialized()) { 
 //BA.debugLineNum = 754;BA.debugLine="Position = Max(Position, 0)";
_position = (int) (__c.Max(_position,0));
 //BA.debugLineNum = 755;BA.debugLine="Position = Min(Position, NumberOfItems - 1)";
_position = (int) (__c.Min(_position,(double)(Double.parseDouble(_numberofitems()))-1));
 //BA.debugLineNum = 756;BA.debugLine="Dim pnl As Panel";
_pnl = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 757;BA.debugLine="pnl = sv.Panel.GetView(Position)";
_pnl.setObject((android.view.ViewGroup)(_sv.getPanel().GetView(_position).getObject()));
 //BA.debugLineNum = 758;BA.debugLine="CommonJump(pnl.Top)";
_commonjump(_pnl.getTop());
 //BA.debugLineNum = 759;BA.debugLine="Return pnl";
if (true) return _pnl;
 };
 //BA.debugLineNum = 761;BA.debugLine="Return Null";
if (true) return (anywheresoftware.b4a.objects.PanelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.PanelWrapper(), (android.view.ViewGroup)(__c.Null));
 //BA.debugLineNum = 762;BA.debugLine="End Sub";
return null;
}
public anywheresoftware.b4a.objects.PanelWrapper  _jumptoview(anywheresoftware.b4a.objects.ConcreteViewWrapper _myview) throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _pnl = null;
 //BA.debugLineNum = 787;BA.debugLine="Public Sub JumpToView(MyView As View) As Panel";
 //BA.debugLineNum = 788;BA.debugLine="If sv.IsInitialized Then";
if (_sv.IsInitialized()) { 
 //BA.debugLineNum = 789;BA.debugLine="Dim pnl As Panel";
_pnl = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 790;BA.debugLine="pnl = GetParentPanel(MyView)";
_pnl.setObject((android.view.ViewGroup)(_getparentpanel(_myview).getObject()));
 //BA.debugLineNum = 791;BA.debugLine="CommonJump(pnl.Top)";
_commonjump(_pnl.getTop());
 //BA.debugLineNum = 792;BA.debugLine="Return pnl";
if (true) return _pnl;
 };
 //BA.debugLineNum = 794;BA.debugLine="End Sub";
return null;
}
public anywheresoftware.b4a.objects.collections.List  _makecheckedlist(int _listtype) throws Exception{
anywheresoftware.b4a.objects.collections.List _listecoches = null;
int _i = 0;
anywheresoftware.b4a.objects.PanelWrapper _pnl = null;
int _p = 0;
anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chk = null;
 //BA.debugLineNum = 802;BA.debugLine="Private Sub MakeCheckedList(ListType As Int) As Li";
 //BA.debugLineNum = 803;BA.debugLine="Dim ListeCoches As List";
_listecoches = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 804;BA.debugLine="ListeCoches.Initialize";
_listecoches.Initialize();
 //BA.debugLineNum = 805;BA.debugLine="If sv.IsInitialized Then";
if (_sv.IsInitialized()) { 
 //BA.debugLineNum = 806;BA.debugLine="For i = 0 To sv.Panel.NumberOfViews-1";
{
final int step4 = 1;
final int limit4 = (int) (_sv.getPanel().getNumberOfViews()-1);
_i = (int) (0) ;
for (;(step4 > 0 && _i <= limit4) || (step4 < 0 && _i >= limit4) ;_i = ((int)(0 + _i + step4))  ) {
 //BA.debugLineNum = 807;BA.debugLine="Dim pnl As Panel";
_pnl = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 808;BA.debugLine="pnl = sv.Panel.GetView(i)";
_pnl.setObject((android.view.ViewGroup)(_sv.getPanel().GetView(_i).getObject()));
 //BA.debugLineNum = 809;BA.debugLine="For p = 0 To pnl.NumberOfViews - 1";
{
final int step7 = 1;
final int limit7 = (int) (_pnl.getNumberOfViews()-1);
_p = (int) (0) ;
for (;(step7 > 0 && _p <= limit7) || (step7 < 0 && _p >= limit7) ;_p = ((int)(0 + _p + step7))  ) {
 //BA.debugLineNum = 810;BA.debugLine="If IsCheckBox(pnl.GetView(p)) Then";
if (_ischeckbox(_pnl.GetView(_p))) { 
 //BA.debugLineNum = 811;BA.debugLine="Dim chk As CheckBox";
_chk = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 812;BA.debugLine="chk = pnl.GetView(p)";
_chk.setObject((android.widget.CheckBox)(_pnl.GetView(_p).getObject()));
 //BA.debugLineNum = 813;BA.debugLine="If chk.Checked Then";
if (_chk.getChecked()) { 
 //BA.debugLineNum = 814;BA.debugLine="If ListType = 0 Then";
if (_listtype==0) { 
 //BA.debugLineNum = 815;BA.debugLine="ListeCoches.Add(pnl.Tag)";
_listecoches.Add(_pnl.getTag());
 }else {
 //BA.debugLineNum = 817;BA.debugLine="ListeCoches.Add(pnl)";
_listecoches.Add((Object)(_pnl.getObject()));
 };
 };
 //BA.debugLineNum = 820;BA.debugLine="Exit";
if (true) break;
 };
 }
};
 }
};
 };
 //BA.debugLineNum = 825;BA.debugLine="Return ListeCoches";
if (true) return _listecoches;
 //BA.debugLineNum = 826;BA.debugLine="End Sub";
return null;
}
public anywheresoftware.b4a.objects.collections.List  _makevalueslistwith(int _viewindexinpanel,int _firstposition,int _lastposition,int _typeliste) throws Exception{
anywheresoftware.b4a.objects.collections.List _liste = null;
anywheresoftware.b4a.objects.PanelWrapper _pnl = null;
anywheresoftware.b4a.objects.ConcreteViewWrapper _v = null;
anywheresoftware.b4a.objects.LabelWrapper _l = null;
anywheresoftware.b4a.objects.EditTextWrapper _e = null;
int _entier = 0;
int _i = 0;
 //BA.debugLineNum = 469;BA.debugLine="Private Sub MakeValuesListWith(ViewIndexInPanel As";
 //BA.debugLineNum = 470;BA.debugLine="Dim Liste As List: Liste.Initialize";
_liste = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 470;BA.debugLine="Dim Liste As List: Liste.Initialize";
_liste.Initialize();
 //BA.debugLineNum = 471;BA.debugLine="If sv.IsInitialized Then";
if (_sv.IsInitialized()) { 
 //BA.debugLineNum = 472;BA.debugLine="If LastPosition > NumberOfItems - 1 Then";
if (_lastposition>(double)(Double.parseDouble(_numberofitems()))-1) { 
 //BA.debugLineNum = 473;BA.debugLine="Log(\"LastPosition is beyond the last item\")";
__c.Log("LastPosition is beyond the last item");
 //BA.debugLineNum = 474;BA.debugLine="Return";
if (true) return null;
 };
 //BA.debugLineNum = 476;BA.debugLine="Dim pnl As Panel, v As View";
_pnl = new anywheresoftware.b4a.objects.PanelWrapper();
_v = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
 //BA.debugLineNum = 477;BA.debugLine="Dim l As Label, e As EditText";
_l = new anywheresoftware.b4a.objects.LabelWrapper();
_e = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 478;BA.debugLine="Dim entier As Int";
_entier = 0;
 //BA.debugLineNum = 479;BA.debugLine="For i = FirstPosition To LastPosition";
{
final int step11 = 1;
final int limit11 = _lastposition;
_i = _firstposition ;
for (;(step11 > 0 && _i <= limit11) || (step11 < 0 && _i >= limit11) ;_i = ((int)(0 + _i + step11))  ) {
 //BA.debugLineNum = 480;BA.debugLine="pnl = sv.Panel.GetView(i)";
_pnl.setObject((android.view.ViewGroup)(_sv.getPanel().GetView(_i).getObject()));
 //BA.debugLineNum = 481;BA.debugLine="v = pnl.GetView(ViewIndexInPanel)";
_v = _pnl.GetView(_viewindexinpanel);
 //BA.debugLineNum = 482;BA.debugLine="If TypeListe = 0 Then";
if (_typeliste==0) { 
 //BA.debugLineNum = 484;BA.debugLine="Liste.Add(v.Tag)";
_liste.Add(_v.getTag());
 }else if(_typeliste==1) { 
 //BA.debugLineNum = 487;BA.debugLine="If Not(v.IsInitialized) Then";
if (__c.Not(_v.IsInitialized())) { 
 //BA.debugLineNum = 488;BA.debugLine="Liste.Add(\"\")";
_liste.Add((Object)(""));
 }else if(_islabel(_v)) { 
 //BA.debugLineNum = 490;BA.debugLine="l = v";
_l.setObject((android.widget.TextView)(_v.getObject()));
 //BA.debugLineNum = 491;BA.debugLine="Liste.Add(l.Text)";
_liste.Add((Object)(_l.getText()));
 }else if(_isedittext(_v)) { 
 //BA.debugLineNum = 493;BA.debugLine="e = v";
_e.setObject((android.widget.EditText)(_v.getObject()));
 //BA.debugLineNum = 494;BA.debugLine="Liste.Add(e.Text)";
_liste.Add((Object)(_e.getText()));
 };
 }else {
 //BA.debugLineNum = 498;BA.debugLine="If Not(v.IsInitialized) Then";
if (__c.Not(_v.IsInitialized())) { 
 //BA.debugLineNum = 499;BA.debugLine="Liste.Add(0)";
_liste.Add((Object)(0));
 }else if(_islabel(_v)) { 
 //BA.debugLineNum = 501;BA.debugLine="l = v";
_l.setObject((android.widget.TextView)(_v.getObject()));
 //BA.debugLineNum = 502;BA.debugLine="If IsNumber(l.Text) Then";
if (__c.IsNumber(_l.getText())) { 
 //BA.debugLineNum = 503;BA.debugLine="entier = Floor(l.Text)";
_entier = (int) (__c.Floor((double)(Double.parseDouble(_l.getText()))));
 //BA.debugLineNum = 504;BA.debugLine="Liste.Add(entier)";
_liste.Add((Object)(_entier));
 }else {
 //BA.debugLineNum = 506;BA.debugLine="Liste.Add(0)";
_liste.Add((Object)(0));
 };
 }else if(_isedittext(_v)) { 
 //BA.debugLineNum = 509;BA.debugLine="e = v";
_e.setObject((android.widget.EditText)(_v.getObject()));
 //BA.debugLineNum = 510;BA.debugLine="If IsNumber(e.Text) Then";
if (__c.IsNumber(_e.getText())) { 
 //BA.debugLineNum = 511;BA.debugLine="entier = Floor(e.Text)";
_entier = (int) (__c.Floor((double)(Double.parseDouble(_e.getText()))));
 //BA.debugLineNum = 512;BA.debugLine="Liste.Add(entier)";
_liste.Add((Object)(_entier));
 }else {
 //BA.debugLineNum = 514;BA.debugLine="Liste.Add(0)";
_liste.Add((Object)(0));
 };
 };
 };
 }
};
 };
 //BA.debugLineNum = 520;BA.debugLine="Return Liste";
if (true) return _liste;
 //BA.debugLineNum = 521;BA.debugLine="End Sub";
return null;
}
public String  _moveddpanel(int _y) throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
 //BA.debugLineNum = 1056;BA.debugLine="Private Sub MoveDDPanel(Y As Int)";
 //BA.debugLineNum = 1058;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 1059;BA.debugLine="r.Target = sv";
_r.Target = (Object)(_sv.getObject());
 //BA.debugLineNum = 1060;BA.debugLine="r.RunMethod2(\"requestDisallowInterceptTouchEvent\"";
_r.RunMethod2("requestDisallowInterceptTouchEvent",BA.ObjectToString(__c.True),"java.lang.boolean");
 //BA.debugLineNum = 1061;BA.debugLine="DragAndDrop.Top = Min(Max(0, DragAndDrop.Top + Y";
_draganddrop.setTop((int) (__c.Min(__c.Max(0,_draganddrop.getTop()+_y-_lasty),_sv.getPanel().getHeight()-_draganddrop.getHeight())));
 //BA.debugLineNum = 1062;BA.debugLine="If DragAndDrop.Top < sv.VerticalScrollPosition Th";
if (_draganddrop.getTop()<_sv.getVerticalScrollPosition()) { 
_sv.setVerticalScrollPosition(_draganddrop.getTop());};
 //BA.debugLineNum = 1063;BA.debugLine="If DragAndDrop.Top + DragAndDrop.Height > sv.Vert";
if (_draganddrop.getTop()+_draganddrop.getHeight()>_sv.getVerticalScrollPosition()+(double)(Double.parseDouble(_getheight()))) { 
 //BA.debugLineNum = 1064;BA.debugLine="sv.VerticalScrollPosition = DragAndDrop.Top + Dr";
_sv.setVerticalScrollPosition((int) (_draganddrop.getTop()+_draganddrop.getHeight()-(double)(Double.parseDouble(_getheight()))));
 };
 //BA.debugLineNum = 1066;BA.debugLine="End Sub";
return "";
}
public String  _moveitem(int _startposition,int _endposition) throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _pnltomove = null;
int _pnlheight = 0;
int _newtop = 0;
boolean _movefollowingpanels = false;
anywheresoftware.b4a.objects.PanelWrapper _pnl = null;
int _i = 0;
int _cptbtf = 0;
int _cptstb = 0;
 //BA.debugLineNum = 286;BA.debugLine="Public Sub MoveItem(StartPosition As Int, EndPosit";
 //BA.debugLineNum = 287;BA.debugLine="If sv.IsInitialized And StartPosition <> EndPosit";
if (_sv.IsInitialized() && _startposition!=_endposition) { 
 //BA.debugLineNum = 288;BA.debugLine="If HasExtraContent Then CollapseItem";
if (_hasextracontent()) { 
_collapseitem();};
 //BA.debugLineNum = 289;BA.debugLine="StartPosition = Max(0, Min(StartPosition, Number";
_startposition = (int) (__c.Max(0,__c.Min(_startposition,(double)(Double.parseDouble(_numberofitems()))-1)));
 //BA.debugLineNum = 290;BA.debugLine="EndPosition = Max(0, Min(EndPosition, NumberOfIt";
_endposition = (int) (__c.Max(0,__c.Min(_endposition,(double)(Double.parseDouble(_numberofitems())))));
 //BA.debugLineNum = 291;BA.debugLine="Dim pnlToMove As Panel";
_pnltomove = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 292;BA.debugLine="pnlToMove = sv.Panel.GetView(StartPosition)";
_pnltomove.setObject((android.view.ViewGroup)(_sv.getPanel().GetView(_startposition).getObject()));
 //BA.debugLineNum = 293;BA.debugLine="Dim PnlHeight, NewTop As Int";
_pnlheight = 0;
_newtop = 0;
 //BA.debugLineNum = 294;BA.debugLine="PnlHeight = pnlToMove.Height";
_pnlheight = _pnltomove.getHeight();
 //BA.debugLineNum = 295;BA.debugLine="NewTop = pnlToMove.Top";
_newtop = _pnltomove.getTop();
 //BA.debugLineNum = 296;BA.debugLine="Dim MoveFollowingPanels As Boolean";
_movefollowingpanels = false;
 //BA.debugLineNum = 297;BA.debugLine="MoveFollowingPanels = False";
_movefollowingpanels = __c.False;
 //BA.debugLineNum = 298;BA.debugLine="Dim pnl As Panel";
_pnl = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 299;BA.debugLine="For i = 0 To NumberOfItems - 1";
{
final int step13 = 1;
final int limit13 = (int) ((double)(Double.parseDouble(_numberofitems()))-1);
_i = (int) (0) ;
for (;(step13 > 0 && _i <= limit13) || (step13 < 0 && _i >= limit13) ;_i = ((int)(0 + _i + step13))  ) {
 //BA.debugLineNum = 300;BA.debugLine="pnl = sv.Panel.GetView(i)";
_pnl.setObject((android.view.ViewGroup)(_sv.getPanel().GetView(_i).getObject()));
 //BA.debugLineNum = 301;BA.debugLine="If i = StartPosition Then";
if (_i==_startposition) { 
 //BA.debugLineNum = 302;BA.debugLine="MoveFollowingPanels = (EndPosition > StartPosi";
_movefollowingpanels = (_endposition>_startposition);
 //BA.debugLineNum = 303;BA.debugLine="If Not(MoveFollowingPanels) Then Exit 'Move do";
if (__c.Not(_movefollowingpanels)) { 
if (true) break;};
 //BA.debugLineNum = 304;BA.debugLine="PnlHeight = -PnlHeight 'Panels are moved upwar";
_pnlheight = (int) (-_pnlheight);
 }else if(_i==_endposition) { 
 //BA.debugLineNum = 306;BA.debugLine="MoveFollowingPanels = (EndPosition < StartPosi";
_movefollowingpanels = (_endposition<_startposition);
 //BA.debugLineNum = 307;BA.debugLine="If Not(MoveFollowingPanels) Then Exit 'Move do";
if (__c.Not(_movefollowingpanels)) { 
if (true) break;};
 //BA.debugLineNum = 308;BA.debugLine="NewTop = pnl.Top";
_newtop = _pnl.getTop();
 //BA.debugLineNum = 309;BA.debugLine="pnl.Top = pnl.Top + PnlHeight";
_pnl.setTop((int) (_pnl.getTop()+_pnlheight));
 }else if(_movefollowingpanels) { 
 //BA.debugLineNum = 311;BA.debugLine="If i = EndPosition - 1 Then NewTop = pnl.Top +";
if (_i==_endposition-1) { 
_newtop = (int) (_pnl.getTop()+_pnl.getHeight()+_pnlheight);};
 //BA.debugLineNum = 312;BA.debugLine="pnl.Top = pnl.Top + PnlHeight";
_pnl.setTop((int) (_pnl.getTop()+_pnlheight));
 };
 }
};
 //BA.debugLineNum = 315;BA.debugLine="pnlToMove.Top = NewTop";
_pnltomove.setTop(_newtop);
 //BA.debugLineNum = 319;BA.debugLine="Dim CptBtF, CptStB As Int";
_cptbtf = 0;
_cptstb = 0;
 //BA.debugLineNum = 320;BA.debugLine="CptBtF = sv.Panel.NumberOfViews - EndPosition -";
_cptbtf = (int) (_sv.getPanel().getNumberOfViews()-_endposition-1);
 //BA.debugLineNum = 321;BA.debugLine="CptStB = EndPosition - 1";
_cptstb = (int) (_endposition-1);
 //BA.debugLineNum = 322;BA.debugLine="If Abs(CptBtF) < Abs(CptStB) Then";
if (__c.Abs(_cptbtf)<__c.Abs(_cptstb)) { 
 //BA.debugLineNum = 323;BA.debugLine="If StartPosition < EndPosition Then";
if (_startposition<_endposition) { 
 //BA.debugLineNum = 324;BA.debugLine="CptBtF = CptBtF + 1";
_cptbtf = (int) (_cptbtf+1);
 //BA.debugLineNum = 325;BA.debugLine="EndPosition = EndPosition - 1";
_endposition = (int) (_endposition-1);
 };
 //BA.debugLineNum = 327;BA.debugLine="pnlToMove.BringToFront";
_pnltomove.BringToFront();
 //BA.debugLineNum = 328;BA.debugLine="For i = 1 To CptBtF";
{
final int step39 = 1;
final int limit39 = _cptbtf;
_i = (int) (1) ;
for (;(step39 > 0 && _i <= limit39) || (step39 < 0 && _i >= limit39) ;_i = ((int)(0 + _i + step39))  ) {
 //BA.debugLineNum = 329;BA.debugLine="pnl = sv.Panel.GetView(EndPosition)";
_pnl.setObject((android.view.ViewGroup)(_sv.getPanel().GetView(_endposition).getObject()));
 //BA.debugLineNum = 330;BA.debugLine="pnl.BringToFront";
_pnl.BringToFront();
 }
};
 }else {
 //BA.debugLineNum = 333;BA.debugLine="If StartPosition > EndPosition Then";
if (_startposition>_endposition) { 
 //BA.debugLineNum = 334;BA.debugLine="CptStB = CptStB + 1";
_cptstb = (int) (_cptstb+1);
 //BA.debugLineNum = 335;BA.debugLine="EndPosition = EndPosition + 1";
_endposition = (int) (_endposition+1);
 };
 //BA.debugLineNum = 337;BA.debugLine="pnlToMove.SendToBack";
_pnltomove.SendToBack();
 //BA.debugLineNum = 338;BA.debugLine="For i = 1 To CptStB";
{
final int step49 = 1;
final int limit49 = _cptstb;
_i = (int) (1) ;
for (;(step49 > 0 && _i <= limit49) || (step49 < 0 && _i >= limit49) ;_i = ((int)(0 + _i + step49))  ) {
 //BA.debugLineNum = 339;BA.debugLine="pnl = sv.Panel.GetView(EndPosition - 1)";
_pnl.setObject((android.view.ViewGroup)(_sv.getPanel().GetView((int) (_endposition-1)).getObject()));
 //BA.debugLineNum = 340;BA.debugLine="pnl.SendToBack";
_pnl.SendToBack();
 }
};
 };
 };
 //BA.debugLineNum = 344;BA.debugLine="End Sub";
return "";
}
public String  _movetodropposition(int _y) throws Exception{
int _startposition = 0;
int _endposition = 0;
anywheresoftware.b4a.objects.PanelWrapper _pnl = null;
int _i = 0;
 //BA.debugLineNum = 1068;BA.debugLine="Private Sub MoveToDropPosition(Y As Int)";
 //BA.debugLineNum = 1070;BA.debugLine="DragAndDrop.Top = Min(Max(0, DragAndDrop.Top + Y";
_draganddrop.setTop((int) (__c.Min(__c.Max(0,_draganddrop.getTop()+_y-_lasty),_sv.getPanel().getHeight()-_draganddrop.getHeight())));
 //BA.debugLineNum = 1073;BA.debugLine="Dim StartPosition, EndPosition As Int";
_startposition = 0;
_endposition = 0;
 //BA.debugLineNum = 1074;BA.debugLine="StartPosition = -1";
_startposition = (int) (-1);
 //BA.debugLineNum = 1075;BA.debugLine="EndPosition = -1";
_endposition = (int) (-1);
 //BA.debugLineNum = 1076;BA.debugLine="Dim pnl As Panel";
_pnl = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 1077;BA.debugLine="For i = 0 To NumberOfItems - 1";
{
final int step6 = 1;
final int limit6 = (int) ((double)(Double.parseDouble(_numberofitems()))-1);
_i = (int) (0) ;
for (;(step6 > 0 && _i <= limit6) || (step6 < 0 && _i >= limit6) ;_i = ((int)(0 + _i + step6))  ) {
 //BA.debugLineNum = 1078;BA.debugLine="pnl = sv.Panel.GetView(i)";
_pnl.setObject((android.view.ViewGroup)(_sv.getPanel().GetView(_i).getObject()));
 //BA.debugLineNum = 1079;BA.debugLine="If pnl.Top = OriginDDTop Then";
if (_pnl.getTop()==_originddtop) { 
 //BA.debugLineNum = 1080;BA.debugLine="StartPosition = i";
_startposition = _i;
 //BA.debugLineNum = 1081;BA.debugLine="If EndPosition <> -1 Then Exit";
if (_endposition!=-1) { 
if (true) break;};
 };
 //BA.debugLineNum = 1083;BA.debugLine="If DragAndDrop.Top >= pnl.Top And DragAndDrop.To";
if (_draganddrop.getTop()>=_pnl.getTop() && _draganddrop.getTop()<_pnl.getTop()+_pnl.getHeight()) { 
 //BA.debugLineNum = 1084;BA.debugLine="EndPosition = i";
_endposition = _i;
 //BA.debugLineNum = 1085;BA.debugLine="If StartPosition <> -1 Then Exit";
if (_startposition!=-1) { 
if (true) break;};
 };
 }
};
 //BA.debugLineNum = 1090;BA.debugLine="MoveItem(StartPosition, EndPosition)";
_moveitem(_startposition,_endposition);
 //BA.debugLineNum = 1091;BA.debugLine="If EndPosition > StartPosition Then EndPosition =";
if (_endposition>_startposition) { 
_endposition = (int) (_endposition-1);};
 //BA.debugLineNum = 1092;BA.debugLine="If SubExists(CallbackMod, sub_AfterDrop) Then Cal";
if (__c.SubExists(ba,_callbackmod,_sub_afterdrop)) { 
__c.CallSubNew3(ba,_callbackmod,_sub_afterdrop,(Object)(_startposition),(Object)(_endposition));};
 //BA.debugLineNum = 1095;BA.debugLine="AbortDragAndDrop";
_abortdraganddrop();
 //BA.debugLineNum = 1096;BA.debugLine="End Sub";
return "";
}
public String  _movetopafterfiltering(int _firstpos) throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _pnl = null;
int _newtop = 0;
int _i = 0;
 //BA.debugLineNum = 1166;BA.debugLine="Private Sub MoveTopAfterFiltering(FirstPos As Int)";
 //BA.debugLineNum = 1168;BA.debugLine="Dim pnl As Panel, NewTop As Int";
_pnl = new anywheresoftware.b4a.objects.PanelWrapper();
_newtop = 0;
 //BA.debugLineNum = 1169;BA.debugLine="pnl = sv.Panel.GetView(FirstPos)";
_pnl.setObject((android.view.ViewGroup)(_sv.getPanel().GetView(_firstpos).getObject()));
 //BA.debugLineNum = 1170;BA.debugLine="If pnl.Visible Then";
if (_pnl.getVisible()) { 
 //BA.debugLineNum = 1171;BA.debugLine="NewTop = pnl.Top + pnl.Height";
_newtop = (int) (_pnl.getTop()+_pnl.getHeight());
 }else {
 //BA.debugLineNum = 1173;BA.debugLine="NewTop = pnl.Top";
_newtop = _pnl.getTop();
 };
 //BA.debugLineNum = 1175;BA.debugLine="For i = FirstPos + 1 To NumberOfItems - 1";
{
final int step8 = 1;
final int limit8 = (int) ((double)(Double.parseDouble(_numberofitems()))-1);
_i = (int) (_firstpos+1) ;
for (;(step8 > 0 && _i <= limit8) || (step8 < 0 && _i >= limit8) ;_i = ((int)(0 + _i + step8))  ) {
 //BA.debugLineNum = 1176;BA.debugLine="pnl = sv.Panel.GetView(i)";
_pnl.setObject((android.view.ViewGroup)(_sv.getPanel().GetView(_i).getObject()));
 //BA.debugLineNum = 1177;BA.debugLine="If pnl.Visible Then";
if (_pnl.getVisible()) { 
 //BA.debugLineNum = 1178;BA.debugLine="pnl.Top = NewTop";
_pnl.setTop(_newtop);
 //BA.debugLineNum = 1179;BA.debugLine="NewTop = pnl.Top + pnl.Height";
_newtop = (int) (_pnl.getTop()+_pnl.getHeight());
 };
 }
};
 //BA.debugLineNum = 1182;BA.debugLine="svHeight = NewTop";
_svheight = _newtop;
 //BA.debugLineNum = 1183;BA.debugLine="sv.Panel.Height = svHeight";
_sv.getPanel().setHeight(_svheight);
 //BA.debugLineNum = 1184;BA.debugLine="End Sub";
return "";
}
public String  _numberofitems() throws Exception{
 //BA.debugLineNum = 669;BA.debugLine="Public Sub NumberOfItems";
 //BA.debugLineNum = 670;BA.debugLine="If sv.IsInitialized Then";
if (_sv.IsInitialized()) { 
 //BA.debugLineNum = 671;BA.debugLine="Return sv.Panel.NumberOfViews";
if (true) return BA.NumberToString(_sv.getPanel().getNumberOfViews());
 }else {
 //BA.debugLineNum = 673;BA.debugLine="Return 0";
if (true) return BA.NumberToString(0);
 };
 //BA.debugLineNum = 675;BA.debugLine="End Sub";
return "";
}
public String  _paintbackground(anywheresoftware.b4a.objects.PanelWrapper _pnl,boolean _extended) throws Exception{
int _state = 0;
int _p = 0;
anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chk = null;
 //BA.debugLineNum = 893;BA.debugLine="Private Sub PaintBackground(pnl As Panel, Extended";
 //BA.debugLineNum = 894;BA.debugLine="Dim State As Int";
_state = 0;
 //BA.debugLineNum = 895;BA.debugLine="If Extended Then";
if (_extended) { 
 //BA.debugLineNum = 896;BA.debugLine="State = PAINT_EXTENDED";
_state = _paint_extended;
 }else {
 //BA.debugLineNum = 898;BA.debugLine="State = PAINT_DEFAULT";
_state = _paint_default;
 };
 //BA.debugLineNum = 900;BA.debugLine="For p = 0 To pnl.NumberOfViews - 1";
{
final int step7 = 1;
final int limit7 = (int) (_pnl.getNumberOfViews()-1);
_p = (int) (0) ;
for (;(step7 > 0 && _p <= limit7) || (step7 < 0 && _p >= limit7) ;_p = ((int)(0 + _p + step7))  ) {
 //BA.debugLineNum = 901;BA.debugLine="If IsCheckBox(pnl.GetView(p)) Then";
if (_ischeckbox(_pnl.GetView(_p))) { 
 //BA.debugLineNum = 902;BA.debugLine="Dim chk As CheckBox";
_chk = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 903;BA.debugLine="chk = pnl.GetView(p)";
_chk.setObject((android.widget.CheckBox)(_pnl.GetView(_p).getObject()));
 //BA.debugLineNum = 904;BA.debugLine="If chk.Checked Then State = State + PAINT_CHECK";
if (_chk.getChecked()) { 
_state = (int) (_state+_paint_checked);};
 //BA.debugLineNum = 905;BA.debugLine="Exit";
if (true) break;
 };
 }
};
 //BA.debugLineNum = 908;BA.debugLine="If SubExists(CallbackMod, sub_Paint) Then";
if (__c.SubExists(ba,_callbackmod,_sub_paint)) { 
 //BA.debugLineNum = 909;BA.debugLine="CallSub3(CallbackMod, sub_Paint, pnl, State)";
__c.CallSubNew3(ba,_callbackmod,_sub_paint,(Object)(_pnl),(Object)(_state));
 }else {
 //BA.debugLineNum = 911;BA.debugLine="Select State";
switch (BA.switchObjectToInt(_state,_paint_extended,_paint_checked,(int) (_paint_checked+_paint_extended))) {
case 0: {
 //BA.debugLineNum = 913;BA.debugLine="pnl.Color = ExtensionColor";
_pnl.setColor(_extensioncolor);
 break; }
case 1: {
 //BA.debugLineNum = 915;BA.debugLine="pnl.Color = CheckedColor";
_pnl.setColor(_checkedcolor);
 break; }
case 2: {
 //BA.debugLineNum = 917;BA.debugLine="If CheckedColor = Colors.Transparent Then";
if (_checkedcolor==__c.Colors.Transparent) { 
 //BA.debugLineNum = 918;BA.debugLine="pnl.Color = ExtensionColor";
_pnl.setColor(_extensioncolor);
 }else {
 //BA.debugLineNum = 920;BA.debugLine="pnl.Color = CheckedColor";
_pnl.setColor(_checkedcolor);
 };
 break; }
default: {
 //BA.debugLineNum = 923;BA.debugLine="pnl.Color = BackgroundColor";
_pnl.setColor(_backgroundcolor);
 break; }
}
;
 };
 //BA.debugLineNum = 926;BA.debugLine="End Sub";
return "";
}
public boolean  _pnlsv_click(Object _viewtag) throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _pnl = null;
 //BA.debugLineNum = 1139;BA.debugLine="Private Sub pnlSV_Click(ViewTag As Object) As Bool";
 //BA.debugLineNum = 1140;BA.debugLine="Dim pnl As Panel";
_pnl = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 1141;BA.debugLine="pnl = Sender";
_pnl.setObject((android.view.ViewGroup)(__c.Sender(ba)));
 //BA.debugLineNum = 1142;BA.debugLine="If SubExists(CallbackMod, sub_Click) Then CallSub";
if (__c.SubExists(ba,_callbackmod,_sub_click)) { 
__c.CallSubNew3(ba,_callbackmod,_sub_click,(Object)(_pnl),_pnl.getTag());};
 //BA.debugLineNum = 1143;BA.debugLine="Return True";
if (true) return __c.True;
 //BA.debugLineNum = 1144;BA.debugLine="End Sub";
return false;
}
public boolean  _pnlsv_longclick(Object _viewtag) throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _pnl = null;
 //BA.debugLineNum = 1146;BA.debugLine="Private Sub pnlSV_LongClick(ViewTag As Object) As";
 //BA.debugLineNum = 1147;BA.debugLine="Dim pnl As Panel";
_pnl = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 1148;BA.debugLine="pnl = Sender";
_pnl.setObject((android.view.ViewGroup)(__c.Sender(ba)));
 //BA.debugLineNum = 1149;BA.debugLine="If SubExists(CallbackMod, sub_LongClick) Then Cal";
if (__c.SubExists(ba,_callbackmod,_sub_longclick)) { 
__c.CallSubNew3(ba,_callbackmod,_sub_longclick,(Object)(_pnl),_pnl.getTag());};
 //BA.debugLineNum = 1150;BA.debugLine="Return True";
if (true) return __c.True;
 //BA.debugLineNum = 1151;BA.debugLine="End Sub";
return false;
}
public boolean  _pnlsv_touch(Object _viewtag,int _action,float _x,float _y,Object _motionevent) throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _pnl = null;
int _p = 0;
anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chb = null;
 //BA.debugLineNum = 1100;BA.debugLine="Private Sub pnlSV_Touch(ViewTag As Object, Action";
 //BA.debugLineNum = 1101;BA.debugLine="Dim pnl As Panel";
_pnl = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 1102;BA.debugLine="pnl = Sender";
_pnl.setObject((android.view.ViewGroup)(__c.Sender(ba)));
 //BA.debugLineNum = 1103;BA.debugLine="If Action <> 2 Then		' <> ACTION_MOVE";
if (_action!=2) { 
 //BA.debugLineNum = 1104;BA.debugLine="If PressedPanel.IsInitialized Then";
if (_pressedpanel.IsInitialized()) { 
 //BA.debugLineNum = 1105;BA.debugLine="PressedPanel.Background = OldBackground";
_pressedpanel.setBackground((android.graphics.drawable.Drawable)(_oldbackground));
 //BA.debugLineNum = 1106;BA.debugLine="PressedPanel = Null";
_pressedpanel.setObject((android.view.ViewGroup)(__c.Null));
 };
 };
 //BA.debugLineNum = 1109;BA.debugLine="If Action = 0 Then		' = ACTION_DOWN";
if (_action==0) { 
 //BA.debugLineNum = 1110;BA.debugLine="OldBackground = pnl.Background";
_oldbackground = (Object)(_pnl.getBackground());
 //BA.debugLineNum = 1111;BA.debugLine="PressedPanel = pnl";
_pressedpanel = _pnl;
 //BA.debugLineNum = 1112;BA.debugLine="If SubExists(CallbackMod, sub_Paint) Then";
if (__c.SubExists(ba,_callbackmod,_sub_paint)) { 
 //BA.debugLineNum = 1113;BA.debugLine="CallSub3(CallbackMod, sub_Paint, pnl, PAINT_PRE";
__c.CallSubNew3(ba,_callbackmod,_sub_paint,(Object)(_pnl),(Object)(_paint_pressed));
 }else {
 //BA.debugLineNum = 1115;BA.debugLine="PressedPanel.Background = PressedDrawable";
_pressedpanel.setBackground((android.graphics.drawable.Drawable)(_presseddrawable));
 };
 }else if(_action==1) { 
 //BA.debugLineNum = 1118;BA.debugLine="If DragAndDrop.IsInitialized Then";
if (_draganddrop.IsInitialized()) { 
 //BA.debugLineNum = 1120;BA.debugLine="MoveToDropPosition(Y)";
_movetodropposition((int) (_y));
 }else if(_modecheck) { 
 //BA.debugLineNum = 1123;BA.debugLine="For p = 0 To pnl.NumberOfViews - 1";
{
final int step21 = 1;
final int limit21 = (int) (_pnl.getNumberOfViews()-1);
_p = (int) (0) ;
for (;(step21 > 0 && _p <= limit21) || (step21 < 0 && _p >= limit21) ;_p = ((int)(0 + _p + step21))  ) {
 //BA.debugLineNum = 1124;BA.debugLine="If IsCheckBox(pnl.GetView(p)) Then";
if (_ischeckbox(_pnl.GetView(_p))) { 
 //BA.debugLineNum = 1125;BA.debugLine="Dim chb As CheckBox";
_chb = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 1126;BA.debugLine="chb = pnl.GetView(p)";
_chb.setObject((android.widget.CheckBox)(_pnl.GetView(_p).getObject()));
 //BA.debugLineNum = 1127;BA.debugLine="chb.Checked = Not(chb.Checked)";
_chb.setChecked(__c.Not(_chb.getChecked()));
 //BA.debugLineNum = 1128;BA.debugLine="Exit";
if (true) break;
 };
 }
};
 };
 }else if(_action==2) { 
 //BA.debugLineNum = 1133;BA.debugLine="If DragAndDrop.IsInitialized Then MoveDDPanel(Y)";
if (_draganddrop.IsInitialized()) { 
_moveddpanel((int) (_y));};
 };
 //BA.debugLineNum = 1135;BA.debugLine="LastY = Y";
_lasty = (int) (_y);
 //BA.debugLineNum = 1136;BA.debugLine="Return (sub_Click = \"\" And sub_LongClick = \"\")";
if (true) return ((_sub_click).equals("") && (_sub_longclick).equals(""));
 //BA.debugLineNum = 1137;BA.debugLine="End Sub";
return false;
}
public String  _removeitemat(int _position) throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _pnl = null;
int _removedheight = 0;
int _i = 0;
 //BA.debugLineNum = 424;BA.debugLine="Public Sub RemoveItemAt(Position As Int)";
 //BA.debugLineNum = 425;BA.debugLine="If sv.IsInitialized And Position < NumberOfItems";
if (_sv.IsInitialized() && _position<(double)(Double.parseDouble(_numberofitems()))) { 
 //BA.debugLineNum = 426;BA.debugLine="Dim pnl As Panel";
_pnl = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 427;BA.debugLine="pnl = sv.Panel.GetView(Position)";
_pnl.setObject((android.view.ViewGroup)(_sv.getPanel().GetView(_position).getObject()));
 //BA.debugLineNum = 428;BA.debugLine="If HasExtraContent And ExtendedItemID = pnl.Tag";
if (_hasextracontent() && (_extendeditemid()).equals(_pnl.getTag())) { 
 //BA.debugLineNum = 429;BA.debugLine="ExtensionIndex = -1";
_extensionindex = (int) (-1);
 //BA.debugLineNum = 430;BA.debugLine="ExtensionView.RemoveView";
_extensionview.RemoveView();
 //BA.debugLineNum = 431;BA.debugLine="ExtensionView = Null";
_extensionview.setObject((android.view.View)(__c.Null));
 };
 //BA.debugLineNum = 433;BA.debugLine="Dim RemovedHeight As Int";
_removedheight = 0;
 //BA.debugLineNum = 434;BA.debugLine="RemovedHeight = pnl.Height";
_removedheight = _pnl.getHeight();
 //BA.debugLineNum = 435;BA.debugLine="pnl.RemoveView";
_pnl.RemoveView();
 //BA.debugLineNum = 436;BA.debugLine="For i = Position To sv.Panel.NumberOfViews - 1";
{
final int step12 = 1;
final int limit12 = (int) (_sv.getPanel().getNumberOfViews()-1);
_i = _position ;
for (;(step12 > 0 && _i <= limit12) || (step12 < 0 && _i >= limit12) ;_i = ((int)(0 + _i + step12))  ) {
 //BA.debugLineNum = 437;BA.debugLine="pnl = sv.Panel.GetView(i)";
_pnl.setObject((android.view.ViewGroup)(_sv.getPanel().GetView(_i).getObject()));
 //BA.debugLineNum = 438;BA.debugLine="pnl.Top = pnl.Top - RemovedHeight";
_pnl.setTop((int) (_pnl.getTop()-_removedheight));
 }
};
 //BA.debugLineNum = 440;BA.debugLine="svHeight = svHeight - RemovedHeight";
_svheight = (int) (_svheight-_removedheight);
 //BA.debugLineNum = 441;BA.debugLine="sv.Panel.Height = svHeight";
_sv.getPanel().setHeight(_svheight);
 };
 //BA.debugLineNum = 443;BA.debugLine="End Sub";
return "";
}
public String  _removeitemid(Object _id) throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _pnl = null;
int _i = 0;
 //BA.debugLineNum = 446;BA.debugLine="Public Sub RemoveItemID(ID As Object)";
 //BA.debugLineNum = 447;BA.debugLine="If sv.IsInitialized Then";
if (_sv.IsInitialized()) { 
 //BA.debugLineNum = 448;BA.debugLine="Dim pnl As Panel";
_pnl = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 449;BA.debugLine="For i = 0 To sv.Panel.NumberOfViews - 1";
{
final int step3 = 1;
final int limit3 = (int) (_sv.getPanel().getNumberOfViews()-1);
_i = (int) (0) ;
for (;(step3 > 0 && _i <= limit3) || (step3 < 0 && _i >= limit3) ;_i = ((int)(0 + _i + step3))  ) {
 //BA.debugLineNum = 450;BA.debugLine="pnl = sv.Panel.GetView(i)";
_pnl.setObject((android.view.ViewGroup)(_sv.getPanel().GetView(_i).getObject()));
 //BA.debugLineNum = 451;BA.debugLine="If pnl.Tag = Null Then Continue";
if (_pnl.getTag()== null) { 
if (true) continue;};
 //BA.debugLineNum = 452;BA.debugLine="If pnl.Tag = ID Then";
if ((_pnl.getTag()).equals(_id)) { 
 //BA.debugLineNum = 453;BA.debugLine="RemoveItemAt(i)";
_removeitemat(_i);
 //BA.debugLineNum = 454;BA.debugLine="Exit";
if (true) break;
 };
 }
};
 };
 //BA.debugLineNum = 458;BA.debugLine="End Sub";
return "";
}
public String  _repaint(anywheresoftware.b4a.objects.PanelWrapper _pnlitem) throws Exception{
boolean _extended = false;
anywheresoftware.b4a.objects.PanelWrapper _pnldivider = null;
 //BA.debugLineNum = 858;BA.debugLine="Public Sub Repaint(pnlItem As Panel)";
 //BA.debugLineNum = 859;BA.debugLine="Dim Extended As Boolean";
_extended = false;
 //BA.debugLineNum = 860;BA.debugLine="Extended = HasExtraContent And ExtendedItemID = p";
_extended = _hasextracontent() && (_extendeditemid()).equals(_pnlitem.getTag());
 //BA.debugLineNum = 861;BA.debugLine="PaintBackground(pnlItem, Extended)";
_paintbackground(_pnlitem,_extended);
 //BA.debugLineNum = 862;BA.debugLine="If DividerSize > 0 Then";
if (_dividersize>0) { 
 //BA.debugLineNum = 863;BA.debugLine="Dim pnlDivider As Panel";
_pnldivider = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 864;BA.debugLine="If Extended Then";
if (_extended) { 
 //BA.debugLineNum = 865;BA.debugLine="pnlDivider = pnlItem.GetView(pnlItem.NumberOfVi";
_pnldivider.setObject((android.view.ViewGroup)(_pnlitem.GetView((int) (_pnlitem.getNumberOfViews()-2)).getObject()));
 }else {
 //BA.debugLineNum = 867;BA.debugLine="pnlDivider = pnlItem.GetView(pnlItem.NumberOfVi";
_pnldivider.setObject((android.view.ViewGroup)(_pnlitem.GetView((int) (_pnlitem.getNumberOfViews()-1)).getObject()));
 };
 //BA.debugLineNum = 869;BA.debugLine="pnlDivider.Color = DividerColor";
_pnldivider.setColor(_dividercolor);
 };
 //BA.debugLineNum = 871;BA.debugLine="End Sub";
return "";
}
public String  _repaintallitems() throws Exception{
int _i = 0;
 //BA.debugLineNum = 874;BA.debugLine="Public Sub RepaintAllItems";
 //BA.debugLineNum = 875;BA.debugLine="For i = 0 To NumberOfItems - 1";
{
final int step1 = 1;
final int limit1 = (int) ((double)(Double.parseDouble(_numberofitems()))-1);
_i = (int) (0) ;
for (;(step1 > 0 && _i <= limit1) || (step1 < 0 && _i >= limit1) ;_i = ((int)(0 + _i + step1))  ) {
 //BA.debugLineNum = 876;BA.debugLine="Repaint(sv.Panel.GetView(i))";
_repaint((anywheresoftware.b4a.objects.PanelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.PanelWrapper(), (android.view.ViewGroup)(_sv.getPanel().GetView(_i).getObject())));
 }
};
 //BA.debugLineNum = 878;BA.debugLine="End Sub";
return "";
}
public String  _replaceitem(int _position,anywheresoftware.b4a.objects.PanelWrapper _newpanel,int _newheight) throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _oldpnl = null;
 //BA.debugLineNum = 407;BA.debugLine="Public Sub ReplaceItem(Position As Int, NewPanel A";
 //BA.debugLineNum = 409;BA.debugLine="If sv.IsInitialized And NewPanel.IsInitialized Th";
if (_sv.IsInitialized() && _newpanel.IsInitialized()) { 
 //BA.debugLineNum = 410;BA.debugLine="If HasExtraContent Then CollapseItem";
if (_hasextracontent()) { 
_collapseitem();};
 //BA.debugLineNum = 411;BA.debugLine="Dim OldPnl As Panel";
_oldpnl = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 412;BA.debugLine="OldPnl = sv.Panel.GetView(Position)";
_oldpnl.setObject((android.view.ViewGroup)(_sv.getPanel().GetView(_position).getObject()));
 //BA.debugLineNum = 413;BA.debugLine="NewPanel.Tag = OldPnl.Tag";
_newpanel.setTag(_oldpnl.getTag());
 //BA.debugLineNum = 414;BA.debugLine="If NewHeight = -1 Then NewHeight = OldPnl.Height";
if (_newheight==-1) { 
_newheight = _oldpnl.getHeight();};
 //BA.debugLineNum = 415;BA.debugLine="AddToSV(NewPanel, NewHeight, NewPanel.Tag <> Nul";
_addtosv(_newpanel,_newheight,_newpanel.getTag()!= null);
 //BA.debugLineNum = 416;BA.debugLine="RemoveItemAt(Position)";
_removeitemat(_position);
 //BA.debugLineNum = 417;BA.debugLine="MoveItem(NumberOfItems - 1, Position)";
_moveitem((int) ((double)(Double.parseDouble(_numberofitems()))-1),_position);
 };
 //BA.debugLineNum = 419;BA.debugLine="End Sub";
return "";
}
public String  _resizepanel() throws Exception{
 //BA.debugLineNum = 277;BA.debugLine="Public Sub ResizePanel()";
 //BA.debugLineNum = 278;BA.debugLine="CollapseItem";
_collapseitem();
 //BA.debugLineNum = 279;BA.debugLine="sv.Panel.Height = svHeight";
_sv.getPanel().setHeight(_svheight);
 //BA.debugLineNum = 280;BA.debugLine="End Sub";
return "";
}
public String  _setonpaintlistener(String _subname) throws Exception{
 //BA.debugLineNum = 889;BA.debugLine="Public Sub SetOnPaintListener(SubName As String)";
 //BA.debugLineNum = 890;BA.debugLine="sub_Paint = SubName";
_sub_paint = _subname;
 //BA.debugLineNum = 891;BA.debugLine="End Sub";
return "";
}
public String  _sortitems(anywheresoftware.b4a.objects.collections.List _valuestosort,int _firstposition,int _lastposition,boolean _ascendingorder) throws Exception{
anywheresoftware.b4a.objects.collections.List _sortlist = null;
int _i = 0;
com.apkbackup.de.clspaklist._typsortdata _sortdata = null;
int _firsttosort = 0;
int _lasttosort = 0;
anywheresoftware.b4a.objects.collections.List _panellist = null;
int _totalheight = 0;
anywheresoftware.b4a.objects.PanelWrapper _pnl = null;
 //BA.debugLineNum = 565;BA.debugLine="Public Sub SortItems(ValuesToSort As List, FirstPo";
 //BA.debugLineNum = 566;BA.debugLine="If ValuesToSort.Size < 2 Or FirstPosition = LastP";
if (_valuestosort.getSize()<2 || _firstposition==_lastposition) { 
if (true) return "";};
 //BA.debugLineNum = 567;BA.debugLine="If LastPosition - FirstPosition + 1 <> ValuesToSo";
if (_lastposition-_firstposition+1!=_valuestosort.getSize()) { 
 //BA.debugLineNum = 568;BA.debugLine="Log(\"Mismatch: \" & ValuesToSort.Size & \" values";
__c.Log("Mismatch: "+BA.NumberToString(_valuestosort.getSize())+" values in ValuesToSort and "+BA.NumberToString((_lastposition-_firstposition+1))+" positions to sort");
 //BA.debugLineNum = 569;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 571;BA.debugLine="Dim SortList As List: SortList.Initialize";
_sortlist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 571;BA.debugLine="Dim SortList As List: SortList.Initialize";
_sortlist.Initialize();
 //BA.debugLineNum = 572;BA.debugLine="For i = FirstPosition To LastPosition";
{
final int step8 = 1;
final int limit8 = _lastposition;
_i = _firstposition ;
for (;(step8 > 0 && _i <= limit8) || (step8 < 0 && _i >= limit8) ;_i = ((int)(0 + _i + step8))  ) {
 //BA.debugLineNum = 573;BA.debugLine="Dim SortData As typSortData";
_sortdata = new com.apkbackup.de.clspaklist._typsortdata();
 //BA.debugLineNum = 574;BA.debugLine="SortData.Initialize";
_sortdata.Initialize();
 //BA.debugLineNum = 575;BA.debugLine="SortData.Index = i";
_sortdata.Index = _i;
 //BA.debugLineNum = 576;BA.debugLine="SortData.Value = ValuesToSort.Get(i - FirstPosit";
_sortdata.Value = _valuestosort.Get((int) (_i-_firstposition));
 //BA.debugLineNum = 577;BA.debugLine="SortList.Add(SortData)";
_sortlist.Add((Object)(_sortdata));
 }
};
 //BA.debugLineNum = 579;BA.debugLine="SortList.SortType(\"Value\", AscendingOrder)";
_sortlist.SortType("Value",_ascendingorder);
 //BA.debugLineNum = 583;BA.debugLine="Dim FirstToSort, LastToSort As Int";
_firsttosort = 0;
_lasttosort = 0;
 //BA.debugLineNum = 584;BA.debugLine="FirstToSort = -1";
_firsttosort = (int) (-1);
 //BA.debugLineNum = 585;BA.debugLine="LastToSort = -1";
_lasttosort = (int) (-1);
 //BA.debugLineNum = 586;BA.debugLine="Dim PanelList As List: PanelList.Initialize";
_panellist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 586;BA.debugLine="Dim PanelList As List: PanelList.Initialize";
_panellist.Initialize();
 //BA.debugLineNum = 587;BA.debugLine="For i = 0 To FirstPosition - 1";
{
final int step21 = 1;
final int limit21 = (int) (_firstposition-1);
_i = (int) (0) ;
for (;(step21 > 0 && _i <= limit21) || (step21 < 0 && _i >= limit21) ;_i = ((int)(0 + _i + step21))  ) {
 //BA.debugLineNum = 588;BA.debugLine="PanelList.Add(sv.Panel.GetView(i))";
_panellist.Add((Object)(_sv.getPanel().GetView(_i).getObject()));
 }
};
 //BA.debugLineNum = 590;BA.debugLine="For i = FirstPosition To LastPosition";
{
final int step24 = 1;
final int limit24 = _lastposition;
_i = _firstposition ;
for (;(step24 > 0 && _i <= limit24) || (step24 < 0 && _i >= limit24) ;_i = ((int)(0 + _i + step24))  ) {
 //BA.debugLineNum = 591;BA.debugLine="Dim SortData As typSortData";
_sortdata = new com.apkbackup.de.clspaklist._typsortdata();
 //BA.debugLineNum = 592;BA.debugLine="SortData = SortList.Get(i - FirstPosition)";
_sortdata = (com.apkbackup.de.clspaklist._typsortdata)(_sortlist.Get((int) (_i-_firstposition)));
 //BA.debugLineNum = 593;BA.debugLine="If SortData.Index <> i Then";
if (_sortdata.Index!=_i) { 
 //BA.debugLineNum = 594;BA.debugLine="If FirstToSort = -1 Then FirstToSort = i";
if (_firsttosort==-1) { 
_firsttosort = _i;};
 //BA.debugLineNum = 595;BA.debugLine="LastToSort = i";
_lasttosort = _i;
 };
 //BA.debugLineNum = 597;BA.debugLine="PanelList.Add(sv.Panel.GetView(SortData.Index))";
_panellist.Add((Object)(_sv.getPanel().GetView(_sortdata.Index).getObject()));
 }
};
 //BA.debugLineNum = 599;BA.debugLine="If FirstToSort = -1 Then Return";
if (_firsttosort==-1) { 
if (true) return "";};
 //BA.debugLineNum = 600;BA.debugLine="For i = LastPosition + 1 To NumberOfItems - 1";
{
final int step34 = 1;
final int limit34 = (int) ((double)(Double.parseDouble(_numberofitems()))-1);
_i = (int) (_lastposition+1) ;
for (;(step34 > 0 && _i <= limit34) || (step34 < 0 && _i >= limit34) ;_i = ((int)(0 + _i + step34))  ) {
 //BA.debugLineNum = 601;BA.debugLine="PanelList.Add(sv.Panel.GetView(i))";
_panellist.Add((Object)(_sv.getPanel().GetView(_i).getObject()));
 }
};
 //BA.debugLineNum = 605;BA.debugLine="If HasExtraContent Then CollapseItem";
if (_hasextracontent()) { 
_collapseitem();};
 //BA.debugLineNum = 606;BA.debugLine="Dim TotalHeight As Int";
_totalheight = 0;
 //BA.debugLineNum = 607;BA.debugLine="Dim pnl As Panel";
_pnl = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 608;BA.debugLine="If FirstToSort > 0 Then";
if (_firsttosort>0) { 
 //BA.debugLineNum = 609;BA.debugLine="pnl = sv.Panel.GetView(FirstToSort - 1)";
_pnl.setObject((android.view.ViewGroup)(_sv.getPanel().GetView((int) (_firsttosort-1)).getObject()));
 //BA.debugLineNum = 610;BA.debugLine="TotalHeight = pnl.Top + pnl.Height";
_totalheight = (int) (_pnl.getTop()+_pnl.getHeight());
 };
 //BA.debugLineNum = 612;BA.debugLine="For i = FirstToSort To LastToSort";
{
final int step44 = 1;
final int limit44 = _lasttosort;
_i = _firsttosort ;
for (;(step44 > 0 && _i <= limit44) || (step44 < 0 && _i >= limit44) ;_i = ((int)(0 + _i + step44))  ) {
 //BA.debugLineNum = 613;BA.debugLine="pnl = PanelList.Get(i)";
_pnl.setObject((android.view.ViewGroup)(_panellist.Get(_i)));
 //BA.debugLineNum = 614;BA.debugLine="pnl.Top = TotalHeight";
_pnl.setTop(_totalheight);
 //BA.debugLineNum = 615;BA.debugLine="TotalHeight = pnl.Top + pnl.Height";
_totalheight = (int) (_pnl.getTop()+_pnl.getHeight());
 }
};
 //BA.debugLineNum = 619;BA.debugLine="If LastToSort < NumberOfItems - FirstToSort Then";
if (_lasttosort<(double)(Double.parseDouble(_numberofitems()))-_firsttosort) { 
 //BA.debugLineNum = 621;BA.debugLine="For i = 0 To LastToSort";
{
final int step50 = 1;
final int limit50 = _lasttosort;
_i = (int) (0) ;
for (;(step50 > 0 && _i <= limit50) || (step50 < 0 && _i >= limit50) ;_i = ((int)(0 + _i + step50))  ) {
 //BA.debugLineNum = 622;BA.debugLine="pnl = PanelList.Get(LastToSort - i)";
_pnl.setObject((android.view.ViewGroup)(_panellist.Get((int) (_lasttosort-_i))));
 //BA.debugLineNum = 623;BA.debugLine="pnl.SendToBack";
_pnl.SendToBack();
 }
};
 }else {
 //BA.debugLineNum = 627;BA.debugLine="For i = FirstToSort To NumberOfItems - 1";
{
final int step55 = 1;
final int limit55 = (int) ((double)(Double.parseDouble(_numberofitems()))-1);
_i = _firsttosort ;
for (;(step55 > 0 && _i <= limit55) || (step55 < 0 && _i >= limit55) ;_i = ((int)(0 + _i + step55))  ) {
 //BA.debugLineNum = 628;BA.debugLine="pnl = PanelList.Get(i)";
_pnl.setObject((android.view.ViewGroup)(_panellist.Get(_i)));
 //BA.debugLineNum = 629;BA.debugLine="pnl.BringToFront";
_pnl.BringToFront();
 }
};
 };
 //BA.debugLineNum = 632;BA.debugLine="End Sub";
return "";
}
public String  _startdraganddrop(anywheresoftware.b4a.objects.PanelWrapper _pnl,String _subafterdrop) throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
 //BA.debugLineNum = 1029;BA.debugLine="Public Sub StartDragAndDrop(pnl As Panel, subAfter";
 //BA.debugLineNum = 1030;BA.debugLine="If Not(DragAndDrop.IsInitialized) Then";
if (__c.Not(_draganddrop.IsInitialized())) { 
 //BA.debugLineNum = 1031;BA.debugLine="If HasExtraContent Then CollapseItem";
if (_hasextracontent()) { 
_collapseitem();};
 //BA.debugLineNum = 1032;BA.debugLine="DragAndDropShadow.Initialize(\"\")";
_draganddropshadow.Initialize(ba,"");
 //BA.debugLineNum = 1033;BA.debugLine="DragAndDropShadow.Color = Colors.ARGB(128, 128,";
_draganddropshadow.setColor(__c.Colors.ARGB((int) (128),(int) (128),(int) (128),(int) (128)));
 //BA.debugLineNum = 1034;BA.debugLine="sv.Panel.AddView(DragAndDropShadow, pnl.Left, pn";
_sv.getPanel().AddView((android.view.View)(_draganddropshadow.getObject()),_pnl.getLeft(),_pnl.getTop(),_pnl.getWidth(),_pnl.getHeight());
 //BA.debugLineNum = 1035;BA.debugLine="DragAndDrop.Initialize(\"\")";
_draganddrop.Initialize(ba,"");
 //BA.debugLineNum = 1036;BA.debugLine="DragAndDrop.SetBackgroundImage(ViewToBmp(pnl))";
_draganddrop.SetBackgroundImage((android.graphics.Bitmap)(_viewtobmp((anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(_pnl.getObject()))).getObject()));
 //BA.debugLineNum = 1037;BA.debugLine="sv.Panel.AddView(DragAndDrop, pnl.Left, pnl.Top,";
_sv.getPanel().AddView((android.view.View)(_draganddrop.getObject()),_pnl.getLeft(),_pnl.getTop(),_pnl.getWidth(),_pnl.getHeight());
 //BA.debugLineNum = 1038;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 1039;BA.debugLine="r.Target = DragAndDrop";
_r.Target = (Object)(_draganddrop.getObject());
 //BA.debugLineNum = 1040;BA.debugLine="r.SetOnTouchListener(\"pnlSV_Touch\")";
_r.SetOnTouchListener(ba,"pnlSV_Touch");
 //BA.debugLineNum = 1041;BA.debugLine="OriginDDTop = pnl.Top";
_originddtop = _pnl.getTop();
 //BA.debugLineNum = 1042;BA.debugLine="sub_AfterDrop = subAfterDrop";
_sub_afterdrop = _subafterdrop;
 };
 //BA.debugLineNum = 1044;BA.debugLine="End Sub";
return "";
}
public String  _swapitems(int _positem1,int _positem2) throws Exception{
int _posmin = 0;
int _posmax = 0;
anywheresoftware.b4a.objects.PanelWrapper _pmin = null;
anywheresoftware.b4a.objects.PanelWrapper _pmax = null;
anywheresoftware.b4a.objects.PanelWrapper _pnl = null;
int _tmptop = 0;
int _i = 0;
int _delta = 0;
 //BA.debugLineNum = 347;BA.debugLine="Public Sub SwapItems(PosItem1 As Int, PosItem2 As";
 //BA.debugLineNum = 348;BA.debugLine="If sv.IsInitialized And PosItem1 <> PosItem2 Then";
if (_sv.IsInitialized() && _positem1!=_positem2) { 
 //BA.debugLineNum = 349;BA.debugLine="If HasExtraContent Then CollapseItem";
if (_hasextracontent()) { 
_collapseitem();};
 //BA.debugLineNum = 350;BA.debugLine="Dim PosMin, PosMax As Int";
_posmin = 0;
_posmax = 0;
 //BA.debugLineNum = 351;BA.debugLine="PosMin = Min(PosItem1, PosItem2)";
_posmin = (int) (__c.Min(_positem1,_positem2));
 //BA.debugLineNum = 352;BA.debugLine="PosMax = Max(PosItem1, PosItem2)";
_posmax = (int) (__c.Max(_positem1,_positem2));
 //BA.debugLineNum = 353;BA.debugLine="If PosMax > NumberOfItems - 1 Then";
if (_posmax>(double)(Double.parseDouble(_numberofitems()))-1) { 
 //BA.debugLineNum = 354;BA.debugLine="Log(\"Error: position after the last item\")";
__c.Log("Error: position after the last item");
 //BA.debugLineNum = 355;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 357;BA.debugLine="Dim pMin, pMax, pnl As Panel";
_pmin = new anywheresoftware.b4a.objects.PanelWrapper();
_pmax = new anywheresoftware.b4a.objects.PanelWrapper();
_pnl = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 358;BA.debugLine="pMin = sv.Panel.GetView(PosMin)";
_pmin.setObject((android.view.ViewGroup)(_sv.getPanel().GetView(_posmin).getObject()));
 //BA.debugLineNum = 359;BA.debugLine="pMax = sv.Panel.GetView(PosMax)";
_pmax.setObject((android.view.ViewGroup)(_sv.getPanel().GetView(_posmax).getObject()));
 //BA.debugLineNum = 360;BA.debugLine="Dim tmpTop As Int";
_tmptop = 0;
 //BA.debugLineNum = 361;BA.debugLine="tmpTop = pMin.Top";
_tmptop = _pmin.getTop();
 //BA.debugLineNum = 362;BA.debugLine="pMin.Top = pMax.Top";
_pmin.setTop(_pmax.getTop());
 //BA.debugLineNum = 363;BA.debugLine="pMax.Top = tmpTop";
_pmax.setTop(_tmptop);
 //BA.debugLineNum = 368;BA.debugLine="If PosMax < NumberOfItems - PosMax Then";
if (_posmax<(double)(Double.parseDouble(_numberofitems()))-_posmax) { 
 //BA.debugLineNum = 369;BA.debugLine="pMin.SendToBack";
_pmin.SendToBack();
 //BA.debugLineNum = 370;BA.debugLine="For i = PosMin + 1 To PosMax - 1";
{
final int step19 = 1;
final int limit19 = (int) (_posmax-1);
_i = (int) (_posmin+1) ;
for (;(step19 > 0 && _i <= limit19) || (step19 < 0 && _i >= limit19) ;_i = ((int)(0 + _i + step19))  ) {
 //BA.debugLineNum = 371;BA.debugLine="pnl = sv.Panel.GetView(PosMax - 1)";
_pnl.setObject((android.view.ViewGroup)(_sv.getPanel().GetView((int) (_posmax-1)).getObject()));
 //BA.debugLineNum = 372;BA.debugLine="pnl.SendToBack";
_pnl.SendToBack();
 }
};
 //BA.debugLineNum = 374;BA.debugLine="pMax.SendToBack";
_pmax.SendToBack();
 //BA.debugLineNum = 375;BA.debugLine="For i = 0 To PosMin - 1";
{
final int step24 = 1;
final int limit24 = (int) (_posmin-1);
_i = (int) (0) ;
for (;(step24 > 0 && _i <= limit24) || (step24 < 0 && _i >= limit24) ;_i = ((int)(0 + _i + step24))  ) {
 //BA.debugLineNum = 376;BA.debugLine="pnl = sv.Panel.GetView(PosMax)";
_pnl.setObject((android.view.ViewGroup)(_sv.getPanel().GetView(_posmax).getObject()));
 //BA.debugLineNum = 377;BA.debugLine="pnl.SendToBack";
_pnl.SendToBack();
 }
};
 }else {
 //BA.debugLineNum = 380;BA.debugLine="pMax.BringToFront";
_pmax.BringToFront();
 //BA.debugLineNum = 381;BA.debugLine="For i = PosMin + 1 To PosMax - 1";
{
final int step30 = 1;
final int limit30 = (int) (_posmax-1);
_i = (int) (_posmin+1) ;
for (;(step30 > 0 && _i <= limit30) || (step30 < 0 && _i >= limit30) ;_i = ((int)(0 + _i + step30))  ) {
 //BA.debugLineNum = 382;BA.debugLine="pnl = sv.Panel.GetView(PosMin + 1)";
_pnl.setObject((android.view.ViewGroup)(_sv.getPanel().GetView((int) (_posmin+1)).getObject()));
 //BA.debugLineNum = 383;BA.debugLine="pnl.BringToFront";
_pnl.BringToFront();
 }
};
 //BA.debugLineNum = 385;BA.debugLine="pMin.BringToFront";
_pmin.BringToFront();
 //BA.debugLineNum = 386;BA.debugLine="For i = PosMax + 1 To NumberOfItems - 1";
{
final int step35 = 1;
final int limit35 = (int) ((double)(Double.parseDouble(_numberofitems()))-1);
_i = (int) (_posmax+1) ;
for (;(step35 > 0 && _i <= limit35) || (step35 < 0 && _i >= limit35) ;_i = ((int)(0 + _i + step35))  ) {
 //BA.debugLineNum = 387;BA.debugLine="pnl = sv.Panel.GetView(PosMin)";
_pnl.setObject((android.view.ViewGroup)(_sv.getPanel().GetView(_posmin).getObject()));
 //BA.debugLineNum = 388;BA.debugLine="pnl.BringToFront";
_pnl.BringToFront();
 }
};
 };
 //BA.debugLineNum = 393;BA.debugLine="Dim Delta As Int";
_delta = 0;
 //BA.debugLineNum = 394;BA.debugLine="If pMin.Height <> pMax.Height Then";
if (_pmin.getHeight()!=_pmax.getHeight()) { 
 //BA.debugLineNum = 395;BA.debugLine="Delta = pMin.Height - pMax.Height";
_delta = (int) (_pmin.getHeight()-_pmax.getHeight());
 //BA.debugLineNum = 396;BA.debugLine="For i = PosMin + 1 To PosMax";
{
final int step43 = 1;
final int limit43 = _posmax;
_i = (int) (_posmin+1) ;
for (;(step43 > 0 && _i <= limit43) || (step43 < 0 && _i >= limit43) ;_i = ((int)(0 + _i + step43))  ) {
 //BA.debugLineNum = 397;BA.debugLine="pnl = sv.Panel.GetView(i)";
_pnl.setObject((android.view.ViewGroup)(_sv.getPanel().GetView(_i).getObject()));
 //BA.debugLineNum = 398;BA.debugLine="pnl.Top = pnl.Top - Delta";
_pnl.setTop((int) (_pnl.getTop()-_delta));
 }
};
 };
 };
 //BA.debugLineNum = 402;BA.debugLine="End Sub";
return "";
}
public anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper  _viewtobmp(anywheresoftware.b4a.objects.ConcreteViewWrapper _myview) throws Exception{
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _bmp = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper _c = null;
anywheresoftware.b4a.agraham.reflection.Reflection _r1 = null;
anywheresoftware.b4a.agraham.reflection.Reflection _r2 = null;
Object[] _args = null;
String[] _types = null;
 //BA.debugLineNum = 106;BA.debugLine="Private Sub ViewToBmp(MyView As View) As Bitmap";
 //BA.debugLineNum = 108;BA.debugLine="Dim bmp As Bitmap";
_bmp = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 109;BA.debugLine="Dim c As Canvas";
_c = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
 //BA.debugLineNum = 110;BA.debugLine="bmp.InitializeMutable(MyView.Width, MyView.Height";
_bmp.InitializeMutable(_myview.getWidth(),_myview.getHeight());
 //BA.debugLineNum = 111;BA.debugLine="c.Initialize2(bmp)";
_c.Initialize2((android.graphics.Bitmap)(_bmp.getObject()));
 //BA.debugLineNum = 113;BA.debugLine="Dim r1, r2 As Reflector";
_r1 = new anywheresoftware.b4a.agraham.reflection.Reflection();
_r2 = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 114;BA.debugLine="r1.Target = MyView";
_r1.Target = (Object)(_myview.getObject());
 //BA.debugLineNum = 115;BA.debugLine="r2.Target = c";
_r2.Target = (Object)(_c);
 //BA.debugLineNum = 116;BA.debugLine="r2.Target = r2.GetField(\"canvas\")";
_r2.Target = _r2.GetField("canvas");
 //BA.debugLineNum = 118;BA.debugLine="Dim args(1) As Object";
_args = new Object[(int) (1)];
{
int d0 = _args.length;
for (int i0 = 0;i0 < d0;i0++) {
_args[i0] = new Object();
}
}
;
 //BA.debugLineNum = 119;BA.debugLine="Dim types(1) As String";
_types = new String[(int) (1)];
java.util.Arrays.fill(_types,"");
 //BA.debugLineNum = 120;BA.debugLine="args(0) = r2.Target";
_args[(int) (0)] = _r2.Target;
 //BA.debugLineNum = 121;BA.debugLine="types(0) = \"android.graphics.Canvas\"";
_types[(int) (0)] = "android.graphics.Canvas";
 //BA.debugLineNum = 122;BA.debugLine="r1.RunMethod4(\"draw\", args, types)";
_r1.RunMethod4("draw",_args,_types);
 //BA.debugLineNum = 124;BA.debugLine="Return c.Bitmap";
if (true) return _c.getBitmap();
 //BA.debugLineNum = 125;BA.debugLine="End Sub";
return null;
}
public Object callSub(String sub, Object sender, Object[] args) throws Exception {
BA.senderHolder.set(sender);
return BA.SubDelegator.SubNotFound;
}
}

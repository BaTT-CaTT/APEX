package com.apex;


import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class sett extends Activity implements B4AActivity{
	public static sett mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = false;
	public static final boolean includeTitle = true;
    public static WeakReference<Activity> previousOne;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (isFirst) {
			processBA = new BA(this.getApplicationContext(), null, null, "com.apex", "com.apex.sett");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (sett).");
				p.finish();
			}
		}
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		mostCurrent = this;
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
        WaitForLayout wl = new WaitForLayout();
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(processBA, wl, false))
		    BA.handler.postDelayed(wl, 5);

	}
	static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
            
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "com.apex", "com.apex.sett");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "com.apex.sett", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (sett) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (sett) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
        try {
            if (processBA.subExists("activity_actionbarhomeclick")) {
                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
                    getClass().getMethod("getActionBar").invoke(this), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processBA.runHook("oncreateoptionsmenu", this, new Object[] {menu}))
            return true;
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
        
		return true;
	}   
 @Override
 public boolean onOptionsItemSelected(android.view.MenuItem item) {
    if (item.getItemId() == 16908332) {
        processBA.raiseEvent(null, "activity_actionbarhomeclick");
        return true;
    }
    else
        return super.onOptionsItemSelected(item); 
}
@Override
 public boolean onPrepareOptionsMenu(android.view.Menu menu) {
    super.onPrepareOptionsMenu(menu);
    processBA.runHook("onprepareoptionsmenu", this, new Object[] {menu});
    return true;
    
 }
 protected void onStart() {
    super.onStart();
    processBA.runHook("onstart", this, null);
}
 protected void onStop() {
    super.onStop();
    processBA.runHook("onstop", this, null);
}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (processBA.subExists("activity_windowfocuschanged"))
           processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEventFromUI(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return sett.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeydown", this, new Object[] {keyCode, event}))
            return true;
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK &&
					android.os.Build.VERSION.SDK_INT >= 18) {
				HandleKeyDelayed hk = new HandleKeyDelayed();
				hk.kc = keyCode;
				BA.handler.post(hk);
				return true;
			}
			else {
				boolean res = new HandleKeyDelayed().runDirectly(keyCode);
				if (res)
					return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private class HandleKeyDelayed implements Runnable {
		int kc;
		public void run() {
			runDirectly(kc);
		}
		public boolean runDirectly(int keyCode) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true) {
                return true;
            }
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
            return false;
		}
		
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeyup", this, new Object[] {keyCode, event}))
            return true;
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
		this.setIntent(intent);
        processBA.runHook("onnewintent", this, new Object[] {intent});
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null) //workaround for emulator bug (Issue 2423)
            return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        BA.LogInfo("** Activity (sett) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        processBA.setActivityPaused(true);
        mostCurrent = null;
        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        processBA.runHook("onpause", this, null);
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
        processBA.runHook("ondestroy", this, null);
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
        processBA.runHook("onresume", this, null);
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
			if (mostCurrent == null || mostCurrent != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (sett) Resume **");
		    processBA.raiseEvent(mostCurrent._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
        processBA.runHook("onactivityresult", this, new Object[] {requestCode, resultCode});
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}
    public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
        for (int i = 0;i < permissions.length;i++) {
            Object[] o = new Object[] {permissions[i], grantResults[i] == 0};
            processBA.raiseEventFromDifferentThread(null,null, 0, "activity_permissionresult", true, o);
        }
            
    }

public anywheresoftware.b4a.keywords.Common __c = null;
public com.tchart.materialcolors.MaterialColors _mcl = null;
public anywheresoftware.b4a.objects.LabelWrapper _label1 = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel2 = null;
public com.apex.keyvaluestore _datas = null;
public static String _dir = "";
public static String _dir1 = "";
public static String _path = "";
public static String _intern = "";
public static String _sd = "";
public MLfiles.Fileslib.MLfiles _files = null;
public anywheresoftware.b4a.objects.LabelWrapper _label2 = null;
public adr.fileselectlib.fileselect _fils = null;
public com.apex.clsexplorer _dlgfileexpl = null;
public anywheresoftware.b4a.phone.PackageManagerWrapper _pak = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _axt = null;
public anywheresoftware.b4a.objects.ButtonWrapper _b1 = null;
public b4a.community.donmanfred.widget.bcCheckedTextView _mct = null;
public anywheresoftware.b4a.objects.ListViewWrapper _loglist = null;
public b4a.community.donmanfred.widget.bcCheckedTextView _mst = null;
public com.apex.main _main = null;
public com.apex.starter _starter = null;
public com.apex.animator _animator = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static boolean  _activity_backkeypressed() throws Exception{
 //BA.debugLineNum = 95;BA.debugLine="Sub Activity_BackKeyPressed As Boolean";
 //BA.debugLineNum = 96;BA.debugLine="If dlgFileExpl.IsInitialized Then";
if (mostCurrent._dlgfileexpl.IsInitialized()) { 
 //BA.debugLineNum = 97;BA.debugLine="If dlgFileExpl.IsActive Then";
if (mostCurrent._dlgfileexpl._isactive()) { 
 //BA.debugLineNum = 98;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 };
 //BA.debugLineNum = 101;BA.debugLine="End Sub";
return false;
}
public static String  _activity_create(boolean _firsttime) throws Exception{
String _sdready = "";
anywheresoftware.b4a.objects.LabelWrapper _l1 = null;
anywheresoftware.b4a.objects.LabelWrapper _l2 = null;
 //BA.debugLineNum = 35;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 37;BA.debugLine="Activity.LoadLayout(\"sett\")";
mostCurrent._activity.LoadLayout("sett",mostCurrent.activityBA);
 //BA.debugLineNum = 38;BA.debugLine="Activity.AddMenuItem3(\"Licenses\",\"liz\",LoadBitmap";
mostCurrent._activity.AddMenuItem3(BA.ObjectToCharSequence("Licenses"),"liz",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"street-1.png").getObject()),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 39;BA.debugLine="Activity.Color=mcl.md_blue_grey_800";
mostCurrent._activity.setColor(mostCurrent._mcl.getmd_blue_grey_800());
 //BA.debugLineNum = 40;BA.debugLine="datas.Initialize(File.DirDefaultExternal,\"datasto";
mostCurrent._datas._initialize(processBA,anywheresoftware.b4a.keywords.Common.File.getDirDefaultExternal(),"datastore_1");
 //BA.debugLineNum = 42;BA.debugLine="axt.Color=Colors.ARGB(110,255,255,255)";
mostCurrent._axt.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (110),(int) (255),(int) (255),(int) (255)));
 //BA.debugLineNum = 43;BA.debugLine="axt.Clear";
mostCurrent._axt.Clear();
 //BA.debugLineNum = 44;BA.debugLine="axt.Prompt=datas.GetSimple(\"0\")";
mostCurrent._axt.setPrompt(BA.ObjectToCharSequence(mostCurrent._datas._getsimple("0")));
 //BA.debugLineNum = 46;BA.debugLine="axt.Color=Colors.Transparent";
mostCurrent._axt.setColor(anywheresoftware.b4a.keywords.Common.Colors.Transparent);
 //BA.debugLineNum = 47;BA.debugLine="axt.TextSize=14";
mostCurrent._axt.setTextSize((float) (14));
 //BA.debugLineNum = 48;BA.debugLine="sd=files.GetExtSd";
mostCurrent._sd = mostCurrent._files.GetExtSd();
 //BA.debugLineNum = 49;BA.debugLine="intern=File.DirRootExternal";
mostCurrent._intern = anywheresoftware.b4a.keywords.Common.File.getDirRootExternal();
 //BA.debugLineNum = 50;BA.debugLine="Label1.TextSize=18";
mostCurrent._label1.setTextSize((float) (18));
 //BA.debugLineNum = 51;BA.debugLine="Label1.Text=\"Set your Backup Path:\"";
mostCurrent._label1.setText(BA.ObjectToCharSequence("Set your Backup Path:"));
 //BA.debugLineNum = 52;BA.debugLine="Dim sdready As String = files.SdcardReady";
_sdready = mostCurrent._files.SdcardReady();
 //BA.debugLineNum = 53;BA.debugLine="If sdready=\"mounted\" Then";
if ((_sdready).equals("mounted")) { 
 //BA.debugLineNum = 54;BA.debugLine="mst.Text=files.GetDiskstats(sd)";
mostCurrent._mst.setText(mostCurrent._files.GetDiskstats(mostCurrent._sd));
 }else {
 //BA.debugLineNum = 56;BA.debugLine="mst.Text=files.GetDiskstats(intern)";
mostCurrent._mst.setText(mostCurrent._files.GetDiskstats(mostCurrent._intern));
 };
 //BA.debugLineNum = 59;BA.debugLine="Dim l1,l2 As Label";
_l1 = new anywheresoftware.b4a.objects.LabelWrapper();
_l2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 60;BA.debugLine="l1.Initialize(\"l1\")";
_l1.Initialize(mostCurrent.activityBA,"l1");
 //BA.debugLineNum = 61;BA.debugLine="l2.Initialize(\"l2\")";
_l2.Initialize(mostCurrent.activityBA,"l2");
 //BA.debugLineNum = 62;BA.debugLine="l1=loglist.TwoLinesAndBitmap.Label";
_l1 = mostCurrent._loglist.getTwoLinesAndBitmap().Label;
 //BA.debugLineNum = 63;BA.debugLine="l2=loglist.TwoLinesAndBitmap.SecondLabel";
_l2 = mostCurrent._loglist.getTwoLinesAndBitmap().SecondLabel;
 //BA.debugLineNum = 64;BA.debugLine="l1.TextSize=15";
_l1.setTextSize((float) (15));
 //BA.debugLineNum = 65;BA.debugLine="l1.TextColor=mcl.md_amber_A400";
_l1.setTextColor(mostCurrent._mcl.getmd_amber_A400());
 //BA.debugLineNum = 66;BA.debugLine="l2.TextColor=mcl.md_white_1000";
_l2.setTextColor(mostCurrent._mcl.getmd_white_1000());
 //BA.debugLineNum = 67;BA.debugLine="l2.TextSize=13";
_l2.setTextSize((float) (13));
 //BA.debugLineNum = 68;BA.debugLine="loglist.TwoLinesAndBitmap.ItemHeight=50dip";
mostCurrent._loglist.getTwoLinesAndBitmap().setItemHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
 //BA.debugLineNum = 69;BA.debugLine="loglist.TwoLinesAndBitmap.ImageView.Height=36dip";
mostCurrent._loglist.getTwoLinesAndBitmap().ImageView.setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (36)));
 //BA.debugLineNum = 70;BA.debugLine="loglist.TwoLinesAndBitmap.ImageView.Width=36dip";
mostCurrent._loglist.getTwoLinesAndBitmap().ImageView.setWidth(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (36)));
 //BA.debugLineNum = 71;BA.debugLine="loglist.AddTwoLinesAndBitmap(\"System Info:\",\"avia";
mostCurrent._loglist.AddTwoLinesAndBitmap(BA.ObjectToCharSequence("System Info:"),BA.ObjectToCharSequence("aviable on next Update...."),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"blueprint.png").getObject()));
 //BA.debugLineNum = 72;BA.debugLine="loglist.AddTwoLinesAndBitmap2(\"License Info:\",\"AP";
mostCurrent._loglist.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("License Info:"),BA.ObjectToCharSequence("APEX & Google License-Agreement"),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"street-1.png").getObject()),(Object)(3));
 //BA.debugLineNum = 77;BA.debugLine="mst.TextColor=mcl.md_amber_A400";
mostCurrent._mst.setTextColor(mostCurrent._mcl.getmd_amber_A400());
 //BA.debugLineNum = 78;BA.debugLine="mst.Textsize=13.5";
mostCurrent._mst.setTextsize((int) (13.5));
 //BA.debugLineNum = 79;BA.debugLine="mst.Checked=True";
mostCurrent._mst.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 80;BA.debugLine="data_check";
_data_check();
 //BA.debugLineNum = 81;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 87;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 88;BA.debugLine="If dlgFileExpl.IsInitialized Then";
if (mostCurrent._dlgfileexpl.IsInitialized()) { 
 //BA.debugLineNum = 89;BA.debugLine="If dlgFileExpl.IsActive Then";
if (mostCurrent._dlgfileexpl._isactive()) { 
 //BA.debugLineNum = 90;BA.debugLine="Return";
if (true) return "";
 };
 };
 //BA.debugLineNum = 93;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 83;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 84;BA.debugLine="data_check";
_data_check();
 //BA.debugLineNum = 85;BA.debugLine="End Sub";
return "";
}
public static String  _axt_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 145;BA.debugLine="Sub axt_ItemClick (Position As Int, Value As Objec";
 //BA.debugLineNum = 146;BA.debugLine="Panel2.Visible=False";
mostCurrent._panel2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 147;BA.debugLine="mst.SetVisibleAnimated(80,False)";
mostCurrent._mst.SetVisibleAnimated((int) (80),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 148;BA.debugLine="b1.SetVisibleAnimated(80,False)";
mostCurrent._b1.SetVisibleAnimated((int) (80),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 149;BA.debugLine="loglist.SetVisibleAnimated(80,False)";
mostCurrent._loglist.SetVisibleAnimated((int) (80),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 150;BA.debugLine="call_start";
_call_start();
 //BA.debugLineNum = 151;BA.debugLine="End Sub";
return "";
}
public static String  _axt_textchanged(String _old,String _new) throws Exception{
 //BA.debugLineNum = 141;BA.debugLine="Sub axt_TextChanged (Old As String, New As String)";
 //BA.debugLineNum = 143;BA.debugLine="End Sub";
return "";
}
public static String  _b1_click() throws Exception{
 //BA.debugLineNum = 153;BA.debugLine="Sub b1_Click";
 //BA.debugLineNum = 155;BA.debugLine="StartActivity(Main)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._main.getObject()));
 //BA.debugLineNum = 156;BA.debugLine="Animator.SetAnimati(\"slide_from_left\",\"slide_to_l";
mostCurrent._animator._setanimati(mostCurrent.activityBA,"slide_from_left","slide_to_left");
 //BA.debugLineNum = 157;BA.debugLine="End Sub";
return "";
}
public static String  _call_start() throws Exception{
 //BA.debugLineNum = 103;BA.debugLine="Sub call_start";
 //BA.debugLineNum = 104;BA.debugLine="Panel2.Visible=False";
mostCurrent._panel2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 105;BA.debugLine="mst.SetVisibleAnimated(80,False)";
mostCurrent._mst.SetVisibleAnimated((int) (80),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 107;BA.debugLine="b1.SetVisibleAnimated(80,False)";
mostCurrent._b1.SetVisibleAnimated((int) (80),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 108;BA.debugLine="loglist.SetVisibleAnimated(80,False)";
mostCurrent._loglist.SetVisibleAnimated((int) (80),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 109;BA.debugLine="file_set";
_file_set();
 //BA.debugLineNum = 110;BA.debugLine="End Sub";
return "";
}
public static String  _data_check() throws Exception{
String _fpath = "";
String _s = "";
 //BA.debugLineNum = 132;BA.debugLine="Sub data_check";
 //BA.debugLineNum = 133;BA.debugLine="axt.Clear";
mostCurrent._axt.Clear();
 //BA.debugLineNum = 134;BA.debugLine="Dim fpath As String";
_fpath = "";
 //BA.debugLineNum = 135;BA.debugLine="For Each s As String In datas.ListKeys";
final anywheresoftware.b4a.BA.IterableList group3 = mostCurrent._datas._listkeys();
final int groupLen3 = group3.getSize();
for (int index3 = 0;index3 < groupLen3 ;index3++){
_s = BA.ObjectToString(group3.Get(index3));
 //BA.debugLineNum = 136;BA.debugLine="fpath=datas.GetSimple(s)";
_fpath = mostCurrent._datas._getsimple(_s);
 }
;
 //BA.debugLineNum = 138;BA.debugLine="axt.Add(fpath)";
mostCurrent._axt.Add(_fpath);
 //BA.debugLineNum = 139;BA.debugLine="End Sub";
return "";
}
public static String  _file_set() throws Exception{
String _pathname = "";
 //BA.debugLineNum = 112;BA.debugLine="Sub file_set";
 //BA.debugLineNum = 114;BA.debugLine="dlgFileExpl.Initialize(Activity,intern, Null,True";
mostCurrent._dlgfileexpl._initialize(mostCurrent.activityBA,mostCurrent._activity,mostCurrent._intern,BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.True,anywheresoftware.b4a.keywords.Common.True,"Select");
 //BA.debugLineNum = 115;BA.debugLine="dlgFileExpl.FastScrollEnabled = True";
mostCurrent._dlgfileexpl._fastscrollenabled = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 116;BA.debugLine="dlgFileExpl.Explorer2(True)";
mostCurrent._dlgfileexpl._explorer2(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 117;BA.debugLine="Dim pathname As String=dlgFileExpl.Selection.Chos";
_pathname = mostCurrent._dlgfileexpl._selection.ChosenPath;
 //BA.debugLineNum = 118;BA.debugLine="If Not(dlgFileExpl.Selection.Canceled Or dlgFileE";
if (anywheresoftware.b4a.keywords.Common.Not(mostCurrent._dlgfileexpl._selection.Canceled || (mostCurrent._dlgfileexpl._selection.ChosenPath).equals(""))) { 
 //BA.debugLineNum = 119;BA.debugLine="Log(\"User Path: \"&dlgFileExpl.Selection.ChosenPa";
anywheresoftware.b4a.keywords.Common.Log("User Path: "+mostCurrent._dlgfileexpl._selection.ChosenPath);
 //BA.debugLineNum = 120;BA.debugLine="datas.DeleteAll";
mostCurrent._datas._deleteall();
 //BA.debugLineNum = 121;BA.debugLine="axt.Clear";
mostCurrent._axt.Clear();
 //BA.debugLineNum = 122;BA.debugLine="datas.PutSimple(\"0\",pathname)";
mostCurrent._datas._putsimple("0",(Object)(_pathname));
 //BA.debugLineNum = 123;BA.debugLine="axt.Add(datas.GetSimple(\"0\"))";
mostCurrent._axt.Add(mostCurrent._datas._getsimple("0"));
 //BA.debugLineNum = 124;BA.debugLine="Panel2.Visible=True";
mostCurrent._panel2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 126;BA.debugLine="b1.SetVisibleAnimated(80,True)";
mostCurrent._b1.SetVisibleAnimated((int) (80),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 127;BA.debugLine="mst.SetVisibleAnimated(80,True)";
mostCurrent._mst.SetVisibleAnimated((int) (80),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 128;BA.debugLine="loglist.SetVisibleAnimated(80,True)";
mostCurrent._loglist.SetVisibleAnimated((int) (80),anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 130;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 15;BA.debugLine="Private mcl As MaterialColors";
mostCurrent._mcl = new com.tchart.materialcolors.MaterialColors();
 //BA.debugLineNum = 16;BA.debugLine="Private Label1 As Label";
mostCurrent._label1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private Panel2 As Panel";
mostCurrent._panel2 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private datas As KeyValueStore";
mostCurrent._datas = new com.apex.keyvaluestore();
 //BA.debugLineNum = 19;BA.debugLine="Dim dir As String = File.DirRootExternal&\"/APEX/i";
mostCurrent._dir = anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/APEX/id";
 //BA.debugLineNum = 20;BA.debugLine="Dim dir1 As String =File.DirRootExternal&\"/APEX\"";
mostCurrent._dir1 = anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/APEX";
 //BA.debugLineNum = 21;BA.debugLine="Dim path As String";
mostCurrent._path = "";
 //BA.debugLineNum = 22;BA.debugLine="Dim intern,sd As String";
mostCurrent._intern = "";
mostCurrent._sd = "";
 //BA.debugLineNum = 23;BA.debugLine="Private files As MLfiles";
mostCurrent._files = new MLfiles.Fileslib.MLfiles();
 //BA.debugLineNum = 24;BA.debugLine="Private Label2 As Label";
mostCurrent._label2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Dim fils As FileSelect";
mostCurrent._fils = new adr.fileselectlib.fileselect();
 //BA.debugLineNum = 26;BA.debugLine="Dim dlgFileExpl As ClsExplorer";
mostCurrent._dlgfileexpl = new com.apex.clsexplorer();
 //BA.debugLineNum = 27;BA.debugLine="Dim pak As PackageManager";
mostCurrent._pak = new anywheresoftware.b4a.phone.PackageManagerWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private axt As Spinner";
mostCurrent._axt = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private b1 As Button";
mostCurrent._b1 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private mct As msCheckedTextView";
mostCurrent._mct = new b4a.community.donmanfred.widget.bcCheckedTextView();
 //BA.debugLineNum = 31;BA.debugLine="Private loglist As ListView";
mostCurrent._loglist = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private mst As msCheckedTextView";
mostCurrent._mst = new b4a.community.donmanfred.widget.bcCheckedTextView();
 //BA.debugLineNum = 33;BA.debugLine="End Sub";
return "";
}
public static String  _loglist_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 159;BA.debugLine="Sub loglist_ItemClick (Position As Int, Value As O";
 //BA.debugLineNum = 160;BA.debugLine="If Value=3 Then";
if ((_value).equals((Object)(3))) { 
 //BA.debugLineNum = 161;BA.debugLine="Msgbox(pak.GetApplicationLabel(\"com.apex\")&\", Ve";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence(mostCurrent._pak.GetApplicationLabel("com.apex")+", Version: "+mostCurrent._pak.GetVersionName("com.apex")+" | Build: "+BA.NumberToString(mostCurrent._pak.GetVersionCode("com.apex"))+"| Written in: B4A. Java is a Open-Source Software and is subject to the free GNU Public license. Android is under the google license, all associated names and content are protected by the Google Inc. Software agreement. For more information, visit www.google.com/policies/terms . All rights To the code And the design are reserved To BaTTCaTT And its owners.Code by D. Trojan, published by SuloMedia™ www.battcatt.bplaced.net For Recent Infos. All Rights Reserved APEX ©2017"),BA.ObjectToCharSequence("License Info:"),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 163;BA.debugLine="End Sub";
return "";
}
public static String  _mst_onclick(Object _v) throws Exception{
 //BA.debugLineNum = 165;BA.debugLine="Sub mst_onClick(v As Object)";
 //BA.debugLineNum = 167;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
}

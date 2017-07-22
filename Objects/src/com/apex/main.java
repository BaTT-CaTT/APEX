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

public class main extends de.amberhome.materialdialogs.MaterialDialogsActivity implements B4AActivity{
	public static main mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "com.apex", "com.apex.main");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (main).");
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
		activityBA = new BA(this, layout, processBA, "com.apex", "com.apex.main");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "com.apex.main", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (main) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (main) Resume **");
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
		return main.class;
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
        BA.LogInfo("** Activity (main) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (main) Resume **");
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
public static anywheresoftware.b4a.phone.PackageManagerWrapper _pak = null;
public static anywheresoftware.b4a.objects.Timer _tim = null;
public anywheresoftware.b4a.objects.collections.List _applist = null;
public anywheresoftware.b4a.objects.collections.List _extra = null;
public anywheresoftware.b4a.objects.collections.List _extra2 = null;
public anywheresoftware.b4a.objects.collections.List _finish = null;
public de.amberhome.objects.appcompat.ACToolbarDarkWrapper _toolbar = null;
public de.amberhome.objects.appcompat.ACActionBar _abhelper = null;
public de.amberhome.objects.appcompat.AppCompatBase _ac = null;
public anywheresoftware.b4a.objects.ListViewWrapper _listview1 = null;
public Object[] _args = null;
public anywheresoftware.b4a.agraham.reflection.Reflection _obj1 = null;
public anywheresoftware.b4a.agraham.reflection.Reflection _obj2 = null;
public anywheresoftware.b4a.agraham.reflection.Reflection _obj3 = null;
public static int _size = 0;
public static int _flags = 0;
public static String _name = "";
public static String _apath = "";
public static String _l = "";
public static String[] _types = null;
public static String _packname = "";
public anywheresoftware.b4a.objects.drawable.BitmapDrawable _icon = null;
public com.tchart.materialcolors.MaterialColors _mcl = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _ab1 = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _ab2 = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label1 = null;
public de.donmanfred.LVLineWithTextWrapper _lvt = null;
public de.donmanfred.LVLineWithTextWrapper _lvt2 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _iv1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label3 = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel2 = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _ab3 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label5 = null;
public static int _count = 0;
public de.amberhome.materialdialogs.MaterialDialogBuilderWrapper _md = null;
public anywheresoftware.b4a.objects.LabelWrapper _label6 = null;
public anywheresoftware.b4a.objects.LabelWrapper _bname = null;
public flm.b4a.betterdialogs.BetterDialogs _bdia = null;
public anywheresoftware.b4a.agraham.dialogs.InputDialog.CustomDialog2 _dias = null;
public anywheresoftware.b4a.objects.LabelWrapper _label4 = null;
public anywheresoftware.b4a.objects.LabelWrapper _bpath = null;
public anywheresoftware.b4a.objects.LabelWrapper _bcontent = null;
public b4a.example.intentid _ie = null;
public com.apex.clsexplorer _dlgfileexpl = null;
public static String _dir = "";
public com.apex.keyvaluestore _datas = null;
public anywheresoftware.b4a.objects.PanelWrapper _apannel = null;
public anywheresoftware.b4a.objects.PanelWrapper _bpannel = null;
public anywheresoftware.b4a.objects.ButtonWrapper _b1 = null;
public com.apex.sett _sett = null;
public com.apex.starter _starter = null;

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
vis = vis | (sett.mostCurrent != null);
return vis;}
public static String  _ab1_click() throws Exception{
int _res = 0;
 //BA.debugLineNum = 404;BA.debugLine="Sub ab1_Click";
 //BA.debugLineNum = 405;BA.debugLine="Dim res As Int";
_res = 0;
 //BA.debugLineNum = 406;BA.debugLine="Bname.Enabled=True";
mostCurrent._bname.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 407;BA.debugLine="Bpath.Enabled=True";
mostCurrent._bpath.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 409;BA.debugLine="Bname.Text=extra2.Get(0)";
mostCurrent._bname.setText(BA.ObjectToCharSequence(mostCurrent._extra2.Get((int) (0))));
 //BA.debugLineNum = 410;BA.debugLine="Bpath.Text=\"to: \"&datas.GetSimple(\"0\")";
mostCurrent._bpath.setText(BA.ObjectToCharSequence("to: "+mostCurrent._datas._getsimple("0")));
 //BA.debugLineNum = 411;BA.debugLine="Bcontent.Text=Label5.text&\", \"&extra2.Get(1)";
mostCurrent._bcontent.setText(BA.ObjectToCharSequence(mostCurrent._label5.getText()+", "+BA.ObjectToString(mostCurrent._extra2.Get((int) (1)))));
 //BA.debugLineNum = 412;BA.debugLine="dias.AddView(bpannel,90%x,20%y)";
mostCurrent._dias.AddView((android.view.View)(mostCurrent._bpannel.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (20),mostCurrent.activityBA));
 //BA.debugLineNum = 413;BA.debugLine="dias.Show(\"Backup the app?\",\"Yes\",\"\",\"Cancel\",ico";
mostCurrent._dias.Show("Backup the app?","Yes","","Cancel",mostCurrent.activityBA,mostCurrent._icon.getBitmap());
 //BA.debugLineNum = 414;BA.debugLine="If dias.Response = DialogResponse.POSITIVE Then";
if (mostCurrent._dias.getResponse()==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 415;BA.debugLine="file_check";
_file_check();
 }else {
 //BA.debugLineNum = 417;BA.debugLine="ex_pan";
_ex_pan();
 };
 //BA.debugLineNum = 420;BA.debugLine="End Sub";
return "";
}
public static String  _ab2_click() throws Exception{
 //BA.debugLineNum = 382;BA.debugLine="Sub ab2_Click";
 //BA.debugLineNum = 383;BA.debugLine="ex_pan";
_ex_pan();
 //BA.debugLineNum = 384;BA.debugLine="End Sub";
return "";
}
public static String  _ab3_click() throws Exception{
 //BA.debugLineNum = 436;BA.debugLine="Sub ab3_Click";
 //BA.debugLineNum = 437;BA.debugLine="tim.Enabled=False";
_tim.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 438;BA.debugLine="Panel2.Visible=False";
mostCurrent._panel2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 439;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_backkeypressed() throws Exception{
 //BA.debugLineNum = 170;BA.debugLine="Sub Activity_BackKeyPressed As Boolean";
 //BA.debugLineNum = 171;BA.debugLine="bdia.CloseDialog(DialogResponse.CANCEL)";
mostCurrent._bdia.CloseDialog(anywheresoftware.b4a.keywords.Common.DialogResponse.CANCEL);
 //BA.debugLineNum = 172;BA.debugLine="If dlgFileExpl.IsInitialized Then";
if (mostCurrent._dlgfileexpl.IsInitialized()) { 
 //BA.debugLineNum = 173;BA.debugLine="If dlgFileExpl.IsActive Then Return True";
if (mostCurrent._dlgfileexpl._isactive()) { 
if (true) return anywheresoftware.b4a.keywords.Common.True;};
 };
 //BA.debugLineNum = 175;BA.debugLine="Return bdia";
if (true) return BA.ObjectToBoolean(mostCurrent._bdia);
 //BA.debugLineNum = 176;BA.debugLine="End Sub";
return false;
}
public static String  _activity_create(boolean _firsttime) throws Exception{
anywheresoftware.b4a.objects.drawable.BitmapDrawable _bd = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _bd1 = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _bd2 = null;
anywheresoftware.b4a.objects.LabelWrapper _l1 = null;
anywheresoftware.b4a.objects.LabelWrapper _l2 = null;
 //BA.debugLineNum = 72;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 74;BA.debugLine="Activity.LoadLayout(\"2\")";
mostCurrent._activity.LoadLayout("2",mostCurrent.activityBA);
 //BA.debugLineNum = 75;BA.debugLine="toolbar.SetAsActionBar";
mostCurrent._toolbar.SetAsActionBar(mostCurrent.activityBA);
 //BA.debugLineNum = 78;BA.debugLine="toolbar.PopupTheme=toolbar.THEME_DARK";
mostCurrent._toolbar.setPopupTheme(mostCurrent._toolbar.THEME_DARK);
 //BA.debugLineNum = 79;BA.debugLine="toolbar.Title=pak.GetApplicationLabel(\"com.apex\")";
mostCurrent._toolbar.setTitle(BA.ObjectToCharSequence(_pak.GetApplicationLabel("com.apex")));
 //BA.debugLineNum = 80;BA.debugLine="toolbar.SubTitle=\"Version \"&pak.GetVersionName(\"c";
mostCurrent._toolbar.setSubTitle(BA.ObjectToCharSequence("Version "+_pak.GetVersionName("com.apex")));
 //BA.debugLineNum = 81;BA.debugLine="ABHelper.Initialize";
mostCurrent._abhelper.Initialize(mostCurrent.activityBA);
 //BA.debugLineNum = 82;BA.debugLine="extra.Initialize";
mostCurrent._extra.Initialize();
 //BA.debugLineNum = 83;BA.debugLine="extra2.Initialize";
mostCurrent._extra2.Initialize();
 //BA.debugLineNum = 84;BA.debugLine="finish.Initialize";
mostCurrent._finish.Initialize();
 //BA.debugLineNum = 85;BA.debugLine="md.Initialize(\"md\")";
mostCurrent._md.Initialize(mostCurrent.activityBA,"md");
 //BA.debugLineNum = 86;BA.debugLine="ie.Initialize";
mostCurrent._ie._initialize(processBA);
 //BA.debugLineNum = 87;BA.debugLine="b1.Initialize(\"b1\")";
mostCurrent._b1.Initialize(mostCurrent.activityBA,"b1");
 //BA.debugLineNum = 88;BA.debugLine="lvt2.Initialize(\"lvt2\")";
mostCurrent._lvt2.Initialize(processBA,"lvt2");
 //BA.debugLineNum = 89;BA.debugLine="Bname.Initialize(\"bname\")";
mostCurrent._bname.Initialize(mostCurrent.activityBA,"bname");
 //BA.debugLineNum = 90;BA.debugLine="Bpath.Initialize(\"Bpath\")";
mostCurrent._bpath.Initialize(mostCurrent.activityBA,"Bpath");
 //BA.debugLineNum = 91;BA.debugLine="Bcontent.Initialize(\"bcontent\")";
mostCurrent._bcontent.Initialize(mostCurrent.activityBA,"bcontent");
 //BA.debugLineNum = 92;BA.debugLine="datas.Initialize(File.DirDefaultExternal,\"datasto";
mostCurrent._datas._initialize(processBA,anywheresoftware.b4a.keywords.Common.File.getDirDefaultExternal(),"datastore_1");
 //BA.debugLineNum = 93;BA.debugLine="If FirstTime Then";
if (_firsttime) { 
 //BA.debugLineNum = 94;BA.debugLine="File.MakeDir(File.DirRootExternal,\"APEX/id\")";
anywheresoftware.b4a.keywords.Common.File.MakeDir(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"APEX/id");
 //BA.debugLineNum = 95;BA.debugLine="datas.PutSimple(\"0\",dir)";
mostCurrent._datas._putsimple("0",(Object)(mostCurrent._dir));
 };
 //BA.debugLineNum = 99;BA.debugLine="Dim bd,bd1,bd2 As BitmapDrawable";
_bd = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
_bd1 = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
_bd2 = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 100;BA.debugLine="bd1.Initialize(LoadBitmap(File.DirAssets,\"menu-1.";
_bd1.Initialize((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"menu-1.png").getObject()));
 //BA.debugLineNum = 102;BA.debugLine="bd.Initialize(LoadBitmap(File.DirAssets,\"info.png";
_bd.Initialize((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"info.png").getObject()));
 //BA.debugLineNum = 103;BA.debugLine="Activity.AddMenuItem3(\"Info\", \"inf\", bd.Bitmap,Tr";
mostCurrent._activity.AddMenuItem3(BA.ObjectToCharSequence("Info"),"inf",_bd.getBitmap(),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 104;BA.debugLine="Activity.AddMenuItem(\"Settings\",\"sett\")";
mostCurrent._activity.AddMenuItem(BA.ObjectToCharSequence("Settings"),"sett");
 //BA.debugLineNum = 105;BA.debugLine="bd2.Initialize(LoadBitmap(File.DirAssets,\"Minus R";
_bd2.Initialize((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"Minus Red Button.png").getObject()));
 //BA.debugLineNum = 106;BA.debugLine="Activity.AddMenuItem3(\"Exit\", \"close\",bd2.Bitmap,";
mostCurrent._activity.AddMenuItem3(BA.ObjectToCharSequence("Exit"),"close",_bd2.getBitmap(),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 108;BA.debugLine="Dim l1,l2 As Label";
_l1 = new anywheresoftware.b4a.objects.LabelWrapper();
_l2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 109;BA.debugLine="l1=ListView1.TwoLinesAndBitmap.Label";
_l1 = mostCurrent._listview1.getTwoLinesAndBitmap().Label;
 //BA.debugLineNum = 110;BA.debugLine="l2=ListView1.TwoLinesAndBitmap.SecondLabel";
_l2 = mostCurrent._listview1.getTwoLinesAndBitmap().SecondLabel;
 //BA.debugLineNum = 111;BA.debugLine="l1.TextSize=19";
_l1.setTextSize((float) (19));
 //BA.debugLineNum = 112;BA.debugLine="l1.TextColor=mcl.md_amber_A400";
_l1.setTextColor(mostCurrent._mcl.getmd_amber_A400());
 //BA.debugLineNum = 113;BA.debugLine="l2.TextSize=14";
_l2.setTextSize((float) (14));
 //BA.debugLineNum = 114;BA.debugLine="l2.TextColor=mcl.md_white_1000";
_l2.setTextColor(mostCurrent._mcl.getmd_white_1000());
 //BA.debugLineNum = 115;BA.debugLine="ListView1.TwoLinesAndBitmap.ItemHeight=65dip";
mostCurrent._listview1.getTwoLinesAndBitmap().setItemHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (65)));
 //BA.debugLineNum = 116;BA.debugLine="ListView1.TwoLinesAndBitmap.ImageView.Height=50di";
mostCurrent._listview1.getTwoLinesAndBitmap().ImageView.setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
 //BA.debugLineNum = 117;BA.debugLine="ListView1.Color=Colors.Transparent";
mostCurrent._listview1.setColor(anywheresoftware.b4a.keywords.Common.Colors.Transparent);
 //BA.debugLineNum = 118;BA.debugLine="SetDivider(ListView1, Colors.ARGB(255,255,255,255";
_setdivider(mostCurrent._listview1,anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (255),(int) (255),(int) (255)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1)));
 //BA.debugLineNum = 120;BA.debugLine="Panel1.Visible=False";
mostCurrent._panel1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 121;BA.debugLine="Panel2.Visible=False";
mostCurrent._panel2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 122;BA.debugLine="lvt.Visible=False";
mostCurrent._lvt.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 123;BA.debugLine="lvt.Color=Colors.Transparent";
mostCurrent._lvt.setColor(anywheresoftware.b4a.keywords.Common.Colors.Transparent);
 //BA.debugLineNum = 124;BA.debugLine="Panel2.Color=mcl.md_grey_800";
mostCurrent._panel2.setColor(mostCurrent._mcl.getmd_grey_800());
 //BA.debugLineNum = 125;BA.debugLine="ab1.Text=\"Export\"";
mostCurrent._ab1.setText(BA.ObjectToCharSequence("Export"));
 //BA.debugLineNum = 126;BA.debugLine="ab1.ButtonColor=mcl.md_amber_A700";
mostCurrent._ab1.setButtonColor(mostCurrent._mcl.getmd_amber_A700());
 //BA.debugLineNum = 127;BA.debugLine="ab1.TextColor=mcl.md_black_1000";
mostCurrent._ab1.setTextColor(mostCurrent._mcl.getmd_black_1000());
 //BA.debugLineNum = 128;BA.debugLine="ab2.Text=\"Cancel\"";
mostCurrent._ab2.setText(BA.ObjectToCharSequence("Cancel"));
 //BA.debugLineNum = 130;BA.debugLine="ab2.TextColor=mcl.md_black_1000";
mostCurrent._ab2.setTextColor(mostCurrent._mcl.getmd_black_1000());
 //BA.debugLineNum = 131;BA.debugLine="ab3.Text=\"cancel\"";
mostCurrent._ab3.setText(BA.ObjectToCharSequence("cancel"));
 //BA.debugLineNum = 132;BA.debugLine="ab3.TextColor=mcl.md_red_700";
mostCurrent._ab3.setTextColor(mostCurrent._mcl.getmd_red_700());
 //BA.debugLineNum = 133;BA.debugLine="ab3.ButtonColor=Colors.ARGB(200,255,255,255)";
mostCurrent._ab3.setButtonColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (200),(int) (255),(int) (255),(int) (255)));
 //BA.debugLineNum = 134;BA.debugLine="Activity.Color=Colors.ARGB(255,55,71,79)";
mostCurrent._activity.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (55),(int) (71),(int) (79)));
 //BA.debugLineNum = 138;BA.debugLine="bpannel.Initialize(\"bpannel\")";
mostCurrent._bpannel.Initialize(mostCurrent.activityBA,"bpannel");
 //BA.debugLineNum = 139;BA.debugLine="bpannel.Color=mcl.md_blue_grey_700";
mostCurrent._bpannel.setColor(mostCurrent._mcl.getmd_blue_grey_700());
 //BA.debugLineNum = 140;BA.debugLine="bpannel.AddView(Bname,0dip,1dip,100%x,60dip)";
mostCurrent._bpannel.AddView((android.view.View)(mostCurrent._bname.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 141;BA.debugLine="bpannel.AddView(Bcontent,0dip,40dip,100%x,40dip)";
mostCurrent._bpannel.AddView((android.view.View)(mostCurrent._bcontent.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 //BA.debugLineNum = 142;BA.debugLine="bpannel.AddView(Bpath,0dip,80dip,100%x,50dip)";
mostCurrent._bpannel.AddView((android.view.View)(mostCurrent._bpath.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
 //BA.debugLineNum = 145;BA.debugLine="Bname.TextSize=18";
mostCurrent._bname.setTextSize((float) (18));
 //BA.debugLineNum = 146;BA.debugLine="Bname.TextColor=mcl.md_amber_500";
mostCurrent._bname.setTextColor(mostCurrent._mcl.getmd_amber_500());
 //BA.debugLineNum = 147;BA.debugLine="Bpath.TextSize=18";
mostCurrent._bpath.setTextSize((float) (18));
 //BA.debugLineNum = 148;BA.debugLine="Bpath.Gravity=Gravity.LEFT";
mostCurrent._bpath.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.LEFT);
 //BA.debugLineNum = 149;BA.debugLine="Bpath.TextColor=mcl.md_cyan_A200";
mostCurrent._bpath.setTextColor(mostCurrent._mcl.getmd_cyan_A200());
 //BA.debugLineNum = 151;BA.debugLine="Bcontent.TextColor=mcl.md_white_1000";
mostCurrent._bcontent.setTextColor(mostCurrent._mcl.getmd_white_1000());
 //BA.debugLineNum = 152;BA.debugLine="Bcontent.Gravity=Gravity.LEFT";
mostCurrent._bcontent.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.LEFT);
 //BA.debugLineNum = 153;BA.debugLine="Bcontent.TextSize=15";
mostCurrent._bcontent.setTextSize((float) (15));
 //BA.debugLineNum = 154;BA.debugLine="Bcontent.Enabled=True";
mostCurrent._bcontent.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 158;BA.debugLine="tim.Initialize(\"tim\",1000)";
_tim.Initialize(processBA,"tim",(long) (1000));
 //BA.debugLineNum = 159;BA.debugLine="count=0";
_count = (int) (0);
 //BA.debugLineNum = 160;BA.debugLine="apps_start";
_apps_start();
 //BA.debugLineNum = 161;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 167;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 169;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 163;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 165;BA.debugLine="End Sub";
return "";
}
public static String  _apps_start() throws Exception{
int _i = 0;
String _total = "";
 //BA.debugLineNum = 463;BA.debugLine="Sub apps_start";
 //BA.debugLineNum = 465;BA.debugLine="ListView1.Clear";
mostCurrent._listview1.Clear();
 //BA.debugLineNum = 466;BA.debugLine="extra.Clear";
mostCurrent._extra.Clear();
 //BA.debugLineNum = 467;BA.debugLine="applist=pak.GetInstalledPackages";
mostCurrent._applist = _pak.GetInstalledPackages();
 //BA.debugLineNum = 468;BA.debugLine="Obj1.Target = Obj1.GetContext";
mostCurrent._obj1.Target = (Object)(mostCurrent._obj1.GetContext(processBA));
 //BA.debugLineNum = 469;BA.debugLine="Obj1.Target = Obj1.RunMethod(\"getPackageManager\")";
mostCurrent._obj1.Target = mostCurrent._obj1.RunMethod("getPackageManager");
 //BA.debugLineNum = 470;BA.debugLine="Obj2.Target = Obj1.RunMethod2(\"getInstalledPackag";
mostCurrent._obj2.Target = mostCurrent._obj1.RunMethod2("getInstalledPackages",BA.NumberToString(0),"java.lang.int");
 //BA.debugLineNum = 471;BA.debugLine="size = Obj2.RunMethod(\"size\")";
_size = (int)(BA.ObjectToNumber(mostCurrent._obj2.RunMethod("size")));
 //BA.debugLineNum = 472;BA.debugLine="For i = 0 To size -1";
{
final int step8 = 1;
final int limit8 = (int) (_size-1);
for (_i = (int) (0) ; (step8 > 0 && _i <= limit8) || (step8 < 0 && _i >= limit8); _i = ((int)(0 + _i + step8)) ) {
 //BA.debugLineNum = 473;BA.debugLine="Obj3.Target = Obj2.RunMethod2(\"get\", i, \"java.la";
mostCurrent._obj3.Target = mostCurrent._obj2.RunMethod2("get",BA.NumberToString(_i),"java.lang.int");
 //BA.debugLineNum = 474;BA.debugLine="size = Obj2.RunMethod(\"size\")";
_size = (int)(BA.ObjectToNumber(mostCurrent._obj2.RunMethod("size")));
 //BA.debugLineNum = 475;BA.debugLine="Obj3.Target = Obj3.GetField(\"applicationInfo\") '";
mostCurrent._obj3.Target = mostCurrent._obj3.GetField("applicationInfo");
 //BA.debugLineNum = 476;BA.debugLine="flags = Obj3.GetField(\"flags\")";
_flags = (int)(BA.ObjectToNumber(mostCurrent._obj3.GetField("flags")));
 //BA.debugLineNum = 477;BA.debugLine="packName = Obj3.GetField(\"packageName\")";
mostCurrent._packname = BA.ObjectToString(mostCurrent._obj3.GetField("packageName"));
 //BA.debugLineNum = 478;BA.debugLine="If Bit.And(flags, 1)  = 0 Then";
if (anywheresoftware.b4a.keywords.Common.Bit.And(_flags,(int) (1))==0) { 
 //BA.debugLineNum = 480;BA.debugLine="args(0) = Obj3.Target";
mostCurrent._args[(int) (0)] = mostCurrent._obj3.Target;
 //BA.debugLineNum = 481;BA.debugLine="Types(0) = \"android.content.pm.ApplicationInfo\"";
mostCurrent._types[(int) (0)] = "android.content.pm.ApplicationInfo";
 //BA.debugLineNum = 482;BA.debugLine="name = Obj1.RunMethod4(\"getApplicationLabel\", a";
mostCurrent._name = BA.ObjectToString(mostCurrent._obj1.RunMethod4("getApplicationLabel",mostCurrent._args,mostCurrent._types));
 //BA.debugLineNum = 483;BA.debugLine="icon = Obj1.RunMethod4(\"getApplicationIcon\", ar";
mostCurrent._icon.setObject((android.graphics.drawable.BitmapDrawable)(mostCurrent._obj1.RunMethod4("getApplicationIcon",mostCurrent._args,mostCurrent._types)));
 //BA.debugLineNum = 484;BA.debugLine="apath= GetParentPath(GetSourceDir(GetActivities";
mostCurrent._apath = _getparentpath(_getsourcedir(_getactivitiesinfo(mostCurrent._packname)));
 //BA.debugLineNum = 485;BA.debugLine="Dim total As String";
_total = "";
 //BA.debugLineNum = 486;BA.debugLine="total = File.Size(GetParentPath(GetSourceDir(Ge";
_total = BA.NumberToString(anywheresoftware.b4a.keywords.Common.File.Size(_getparentpath(_getsourcedir(_getactivitiesinfo(mostCurrent._packname))),_getfilename(_getsourcedir(_getactivitiesinfo(mostCurrent._packname)))));
 //BA.debugLineNum = 487;BA.debugLine="ListView1.AddTwoLinesAndBitmap2(name,packName&\"";
mostCurrent._listview1.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence(mostCurrent._name),BA.ObjectToCharSequence(mostCurrent._packname+", "+_formatfilesize((float)(Double.parseDouble(_total)))),mostCurrent._icon.getBitmap(),(Object)(mostCurrent._packname));
 //BA.debugLineNum = 488;BA.debugLine="extra.Add(packName)";
mostCurrent._extra.Add((Object)(mostCurrent._packname));
 };
 }
};
 //BA.debugLineNum = 491;BA.debugLine="End Sub";
return "";
}
public static String  _close_click() throws Exception{
 //BA.debugLineNum = 186;BA.debugLine="Sub close_click";
 //BA.debugLineNum = 187;BA.debugLine="ExitApplication";
anywheresoftware.b4a.keywords.Common.ExitApplication();
 //BA.debugLineNum = 188;BA.debugLine="End Sub";
return "";
}
public static String  _converttotimeformat(int _ms) throws Exception{
int _seconds = 0;
int _minutes = 0;
 //BA.debugLineNum = 516;BA.debugLine="Sub ConvertToTimeFormat(ms As Int) As String";
 //BA.debugLineNum = 517;BA.debugLine="Dim seconds, minutes As Int";
_seconds = 0;
_minutes = 0;
 //BA.debugLineNum = 518;BA.debugLine="seconds = Round(ms / 1000)";
_seconds = (int) (anywheresoftware.b4a.keywords.Common.Round(_ms/(double)1000));
 //BA.debugLineNum = 519;BA.debugLine="minutes = Floor(seconds / 60)";
_minutes = (int) (anywheresoftware.b4a.keywords.Common.Floor(_seconds/(double)60));
 //BA.debugLineNum = 520;BA.debugLine="seconds = seconds Mod 60";
_seconds = (int) (_seconds%60);
 //BA.debugLineNum = 521;BA.debugLine="Return NumberFormat(minutes, 1, 0) & \":\" & Number";
if (true) return anywheresoftware.b4a.keywords.Common.NumberFormat(_minutes,(int) (1),(int) (0))+":"+anywheresoftware.b4a.keywords.Common.NumberFormat(_seconds,(int) (2),(int) (0));
 //BA.debugLineNum = 522;BA.debugLine="End Sub";
return "";
}
public static String  _end_extract() throws Exception{
 //BA.debugLineNum = 283;BA.debugLine="Sub end_extract";
 //BA.debugLineNum = 284;BA.debugLine="tim.Enabled=False";
_tim.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 285;BA.debugLine="lvt.Visible=False";
mostCurrent._lvt.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 286;BA.debugLine="ex_pan2";
_ex_pan2();
 //BA.debugLineNum = 287;BA.debugLine="end_msg";
_end_msg();
 //BA.debugLineNum = 288;BA.debugLine="End Sub";
return "";
}
public static String  _end_msg() throws Exception{
anywheresoftware.b4a.objects.ListViewWrapper _lv1 = null;
anywheresoftware.b4a.objects.LabelWrapper _l1 = null;
anywheresoftware.b4a.objects.LabelWrapper _l2 = null;
String _dirs = "";
String _s = "";
String _e = "";
int _u = 0;
int _zu = 0;
 //BA.debugLineNum = 290;BA.debugLine="Sub end_msg";
 //BA.debugLineNum = 292;BA.debugLine="Dim lv1 As ListView";
_lv1 = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 293;BA.debugLine="lv1.Initialize(\"lv1\")";
_lv1.Initialize(mostCurrent.activityBA,"lv1");
 //BA.debugLineNum = 294;BA.debugLine="lv1.Enabled=True";
_lv1.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 295;BA.debugLine="lv1.Color=Colors.Transparent";
_lv1.setColor(anywheresoftware.b4a.keywords.Common.Colors.Transparent);
 //BA.debugLineNum = 296;BA.debugLine="Dim l1,l2 As Label";
_l1 = new anywheresoftware.b4a.objects.LabelWrapper();
_l2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 297;BA.debugLine="l1.Initialize(\"l1\")";
_l1.Initialize(mostCurrent.activityBA,"l1");
 //BA.debugLineNum = 298;BA.debugLine="l2.Initialize(\"l2\")";
_l2.Initialize(mostCurrent.activityBA,"l2");
 //BA.debugLineNum = 299;BA.debugLine="l1=lv1.TwoLinesAndBitmap.Label";
_l1 = _lv1.getTwoLinesAndBitmap().Label;
 //BA.debugLineNum = 300;BA.debugLine="l2=lv1.TwoLinesAndBitmap.SecondLabel";
_l2 = _lv1.getTwoLinesAndBitmap().SecondLabel;
 //BA.debugLineNum = 301;BA.debugLine="l1.TextSize=14";
_l1.setTextSize((float) (14));
 //BA.debugLineNum = 302;BA.debugLine="l1.TextColor=Colors.White";
_l1.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 303;BA.debugLine="l2.TextSize=12";
_l2.setTextSize((float) (12));
 //BA.debugLineNum = 304;BA.debugLine="l2.TextColor=mcl.md_light_blue_500";
_l2.setTextColor(mostCurrent._mcl.getmd_light_blue_500());
 //BA.debugLineNum = 305;BA.debugLine="Dim dirs As String";
_dirs = "";
 //BA.debugLineNum = 306;BA.debugLine="If datas.ContainsKey(\"0\") Then";
if (mostCurrent._datas._containskey("0")) { 
 //BA.debugLineNum = 307;BA.debugLine="dirs=datas.GetSimple(\"0\")";
_dirs = mostCurrent._datas._getsimple("0");
 };
 //BA.debugLineNum = 309;BA.debugLine="Dim s As String = extra2.Get(1)";
_s = BA.ObjectToString(mostCurrent._extra2.Get((int) (1)));
 //BA.debugLineNum = 310;BA.debugLine="packName=s";
mostCurrent._packname = _s;
 //BA.debugLineNum = 311;BA.debugLine="Dim e As String=extra2.get(0)";
_e = BA.ObjectToString(mostCurrent._extra2.Get((int) (0)));
 //BA.debugLineNum = 312;BA.debugLine="name=e";
mostCurrent._name = _e;
 //BA.debugLineNum = 313;BA.debugLine="icon=pak.GetApplicationIcon(s)";
mostCurrent._icon.setObject((android.graphics.drawable.BitmapDrawable)(_pak.GetApplicationIcon(_s)));
 //BA.debugLineNum = 314;BA.debugLine="finish=File.ListFiles(dir)";
mostCurrent._finish = anywheresoftware.b4a.keywords.Common.File.ListFiles(mostCurrent._dir);
 //BA.debugLineNum = 315;BA.debugLine="For u = 0 To finish.Size-1";
{
final int step24 = 1;
final int limit24 = (int) (mostCurrent._finish.getSize()-1);
for (_u = (int) (0) ; (step24 > 0 && _u <= limit24) || (step24 < 0 && _u >= limit24); _u = ((int)(0 + _u + step24)) ) {
 //BA.debugLineNum = 316;BA.debugLine="Log(finish.Get(u))";
anywheresoftware.b4a.keywords.Common.Log(BA.ObjectToString(mostCurrent._finish.Get(_u)));
 }
};
 //BA.debugLineNum = 320;BA.debugLine="SetDivider(ListView1, Colors.White, 1dip)";
_setdivider(mostCurrent._listview1,anywheresoftware.b4a.keywords.Common.Colors.White,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1)));
 //BA.debugLineNum = 321;BA.debugLine="Label4.Text=name";
mostCurrent._label4.setText(BA.ObjectToCharSequence(mostCurrent._name));
 //BA.debugLineNum = 323;BA.debugLine="Dim zu As Int";
_zu = 0;
 //BA.debugLineNum = 324;BA.debugLine="lv1.AddTwoLinesAndBitmap(name&\" exported to:\",dir";
_lv1.AddTwoLinesAndBitmap(BA.ObjectToCharSequence(mostCurrent._name+" exported to:"),BA.ObjectToCharSequence(_dirs),mostCurrent._icon.getBitmap());
 //BA.debugLineNum = 326;BA.debugLine="zu=bdia.CustomDialog(\"Export log:\",250dip,100dip,";
_zu = mostCurrent._bdia.CustomDialog((Object)("Export log:"),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (250)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100)),(anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(_lv1.getObject())),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (220)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (120)),(int) (20),(Object)(anywheresoftware.b4a.keywords.Common.Colors.Transparent),(Object)("Backups"),(Object)("Ok"),(Object)(""),anywheresoftware.b4a.keywords.Common.False,"bdia",mostCurrent.activityBA);
 //BA.debugLineNum = 327;BA.debugLine="If zu=DialogResponse.POSITIVE Then";
if (_zu==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 329;BA.debugLine="dlgFileExpl.Initialize(Activity, dirs, \".apk\", T";
mostCurrent._dlgfileexpl._initialize(mostCurrent.activityBA,mostCurrent._activity,_dirs,".apk",anywheresoftware.b4a.keywords.Common.True,anywheresoftware.b4a.keywords.Common.False,"OK");
 //BA.debugLineNum = 330;BA.debugLine="dlgFileExpl.FastScrollEnabled = True";
mostCurrent._dlgfileexpl._fastscrollenabled = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 331;BA.debugLine="dlgFileExpl.Explorer2(True)";
mostCurrent._dlgfileexpl._explorer2(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 332;BA.debugLine="If Not(dlgFileExpl.Selection.Canceled Or dlgFile";
if (anywheresoftware.b4a.keywords.Common.Not(mostCurrent._dlgfileexpl._selection.Canceled || (mostCurrent._dlgfileexpl._selection.ChosenFile).equals(""))) { 
 //BA.debugLineNum = 333;BA.debugLine="ToastMessageShow(name,False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(mostCurrent._name),anywheresoftware.b4a.keywords.Common.False);
 };
 };
 //BA.debugLineNum = 336;BA.debugLine="End Sub";
return "";
}
public static String  _ex_pan() throws Exception{
 //BA.debugLineNum = 441;BA.debugLine="Sub ex_pan";
 //BA.debugLineNum = 442;BA.debugLine="If Panel1.Visible=True Then";
if (mostCurrent._panel1.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 443;BA.debugLine="Panel1.Visible=False";
mostCurrent._panel1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 444;BA.debugLine="ListView1.Enabled=True";
mostCurrent._listview1.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 446;BA.debugLine="Panel1.Visible=True";
mostCurrent._panel1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 447;BA.debugLine="ListView1.Enabled=False";
mostCurrent._listview1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 449;BA.debugLine="End Sub";
return "";
}
public static String  _ex_pan2() throws Exception{
 //BA.debugLineNum = 451;BA.debugLine="Sub ex_pan2";
 //BA.debugLineNum = 452;BA.debugLine="If Panel2.Visible=False Then";
if (mostCurrent._panel2.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 453;BA.debugLine="Panel1.Visible=False";
mostCurrent._panel1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 454;BA.debugLine="Panel2.Visible=True";
mostCurrent._panel2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 455;BA.debugLine="ListView1.Enabled=False";
mostCurrent._listview1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 457;BA.debugLine="Panel2.Visible=False";
mostCurrent._panel2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 458;BA.debugLine="ListView1.Enabled=True";
mostCurrent._listview1.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 460;BA.debugLine="End Sub";
return "";
}
public static String  _file_check() throws Exception{
String _s = "";
String _d1 = "";
String _d2 = "";
String _master = "";
 //BA.debugLineNum = 386;BA.debugLine="Sub file_check";
 //BA.debugLineNum = 387;BA.debugLine="Dim s As String = extra2.Get(1)";
_s = BA.ObjectToString(mostCurrent._extra2.Get((int) (1)));
 //BA.debugLineNum = 388;BA.debugLine="Log(\"pak: \"&s)";
anywheresoftware.b4a.keywords.Common.Log("pak: "+_s);
 //BA.debugLineNum = 389;BA.debugLine="name=pak.GetApplicationLabel(s)";
mostCurrent._name = _pak.GetApplicationLabel(_s);
 //BA.debugLineNum = 390;BA.debugLine="Dim d1,d2,master As String";
_d1 = "";
_d2 = "";
_master = "";
 //BA.debugLineNum = 391;BA.debugLine="d1=File.DirRootExternal&\"/APEX\"";
_d1 = anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/APEX";
 //BA.debugLineNum = 392;BA.debugLine="d2=datas.GetSimple(\"0\")";
_d2 = mostCurrent._datas._getsimple("0");
 //BA.debugLineNum = 393;BA.debugLine="If datas.ContainsKey(\"0\") Then";
if (mostCurrent._datas._containskey("0")) { 
 //BA.debugLineNum = 394;BA.debugLine="master=d2";
_master = _d2;
 };
 //BA.debugLineNum = 396;BA.debugLine="If File.Exists(master,s&\".apk\") Then";
if (anywheresoftware.b4a.keywords.Common.File.Exists(_master,_s+".apk")) { 
 //BA.debugLineNum = 397;BA.debugLine="int_folder";
_int_folder();
 }else {
 //BA.debugLineNum = 399;BA.debugLine="start";
_start();
 //BA.debugLineNum = 400;BA.debugLine="ex_pan2";
_ex_pan2();
 };
 //BA.debugLineNum = 402;BA.debugLine="End Sub";
return "";
}
public static String  _formatfilesize(float _bytes) throws Exception{
String[] _unit = null;
double _po = 0;
double _si = 0;
int _i = 0;
 //BA.debugLineNum = 531;BA.debugLine="Sub FormatFileSize(Bytes As Float) As String";
 //BA.debugLineNum = 533;BA.debugLine="Private Unit() As String = Array As String(\" Byte";
_unit = new String[]{" Byte"," KB"," MB"," GB"," TB"," PB"," EB"," ZB"," YB"};
 //BA.debugLineNum = 535;BA.debugLine="If Bytes = 0 Then";
if (_bytes==0) { 
 //BA.debugLineNum = 536;BA.debugLine="Return \"0 Bytes\"";
if (true) return "0 Bytes";
 }else {
 //BA.debugLineNum = 538;BA.debugLine="Private Po, Si As Double";
_po = 0;
_si = 0;
 //BA.debugLineNum = 539;BA.debugLine="Private I As Int";
_i = 0;
 //BA.debugLineNum = 540;BA.debugLine="Bytes = Abs(Bytes)";
_bytes = (float) (anywheresoftware.b4a.keywords.Common.Abs(_bytes));
 //BA.debugLineNum = 541;BA.debugLine="I = Floor(Logarithm(Bytes, 1024))";
_i = (int) (anywheresoftware.b4a.keywords.Common.Floor(anywheresoftware.b4a.keywords.Common.Logarithm(_bytes,1024)));
 //BA.debugLineNum = 542;BA.debugLine="Po = Power(1024, I)";
_po = anywheresoftware.b4a.keywords.Common.Power(1024,_i);
 //BA.debugLineNum = 543;BA.debugLine="Si = Bytes / Po";
_si = _bytes/(double)_po;
 //BA.debugLineNum = 544;BA.debugLine="Return NumberFormat(Si, 1, 2) & Unit(I)";
if (true) return anywheresoftware.b4a.keywords.Common.NumberFormat(_si,(int) (1),(int) (2))+_unit[_i];
 };
 //BA.debugLineNum = 546;BA.debugLine="End Sub";
return "";
}
public static Object  _get_respath(String _package) throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
 //BA.debugLineNum = 590;BA.debugLine="Sub get_respath(package As String) As Object";
 //BA.debugLineNum = 591;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 592;BA.debugLine="r.Target = r.GetContext";
_r.Target = (Object)(_r.GetContext(processBA));
 //BA.debugLineNum = 593;BA.debugLine="r.Target = r.RunMethod(\"getPackageManager\")";
_r.Target = _r.RunMethod("getPackageManager");
 //BA.debugLineNum = 594;BA.debugLine="r.Target = r.RunMethod3(\"getApplicationInfo\", pac";
_r.Target = _r.RunMethod3("getApplicationInfo",_package,"java.lang.String",BA.NumberToString(0x00000001),"java.lang.int");
 //BA.debugLineNum = 595;BA.debugLine="Return r.GetField(\"dataDir\")";
if (true) return _r.GetField("dataDir");
 //BA.debugLineNum = 596;BA.debugLine="End Sub";
return null;
}
public static Object  _getactivitiesinfo(String _package) throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
 //BA.debugLineNum = 572;BA.debugLine="Sub GetActivitiesInfo(package As String) As Object";
 //BA.debugLineNum = 573;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 574;BA.debugLine="r.Target = r.GetContext";
_r.Target = (Object)(_r.GetContext(processBA));
 //BA.debugLineNum = 575;BA.debugLine="r.Target = r.RunMethod(\"getPackageManager\")";
_r.Target = _r.RunMethod("getPackageManager");
 //BA.debugLineNum = 576;BA.debugLine="r.Target = r.RunMethod3(\"getPackageInfo\", package";
_r.Target = _r.RunMethod3("getPackageInfo",_package,"java.lang.String",BA.NumberToString(0x00000001),"java.lang.int");
 //BA.debugLineNum = 577;BA.debugLine="Return r.GetField(\"applicationInfo\")";
if (true) return _r.GetField("applicationInfo");
 //BA.debugLineNum = 578;BA.debugLine="End Sub";
return null;
}
public static String  _getfilename(String _fullpath) throws Exception{
 //BA.debugLineNum = 548;BA.debugLine="Sub GetFileName(FullPath As String) As String";
 //BA.debugLineNum = 549;BA.debugLine="Return FullPath.SubString(FullPath.LastIndexOf(\"/";
if (true) return _fullpath.substring((int) (_fullpath.lastIndexOf("/")+1));
 //BA.debugLineNum = 550;BA.debugLine="End Sub";
return "";
}
public static String  _getparentpath(String _path) throws Exception{
String _path1 = "";
 //BA.debugLineNum = 553;BA.debugLine="Sub GetParentPath(path As String) As String";
 //BA.debugLineNum = 554;BA.debugLine="Dim Path1 As String";
_path1 = "";
 //BA.debugLineNum = 555;BA.debugLine="If path = \"/\" Then";
if ((_path).equals("/")) { 
 //BA.debugLineNum = 556;BA.debugLine="Return \"/\"";
if (true) return "/";
 };
 //BA.debugLineNum = 558;BA.debugLine="L = path.LastIndexOf(\"/\")";
mostCurrent._l = BA.NumberToString(_path.lastIndexOf("/"));
 //BA.debugLineNum = 559;BA.debugLine="If L = path.Length - 1 Then";
if ((mostCurrent._l).equals(BA.NumberToString(_path.length()-1))) { 
 //BA.debugLineNum = 561;BA.debugLine="Path1 = path.SubString2(0,L)";
_path1 = _path.substring((int) (0),(int)(Double.parseDouble(mostCurrent._l)));
 }else {
 //BA.debugLineNum = 563;BA.debugLine="Path1 = path";
_path1 = _path;
 };
 //BA.debugLineNum = 565;BA.debugLine="L = path.LastIndexOf(\"/\")";
mostCurrent._l = BA.NumberToString(_path.lastIndexOf("/"));
 //BA.debugLineNum = 566;BA.debugLine="If L = 0 Then";
if ((mostCurrent._l).equals(BA.NumberToString(0))) { 
 //BA.debugLineNum = 567;BA.debugLine="L = 1";
mostCurrent._l = BA.NumberToString(1);
 };
 //BA.debugLineNum = 569;BA.debugLine="Return Path1.SubString2(0,L)";
if (true) return _path1.substring((int) (0),(int)(Double.parseDouble(mostCurrent._l)));
 //BA.debugLineNum = 570;BA.debugLine="End Sub";
return "";
}
public static String[]  _getpermissions(String _package) throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
String[] _permissions = null;
 //BA.debugLineNum = 502;BA.debugLine="Sub GetPermissions(Package As String) As String()";
 //BA.debugLineNum = 503;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 504;BA.debugLine="r.Target = r.GetContext";
_r.Target = (Object)(_r.GetContext(processBA));
 //BA.debugLineNum = 505;BA.debugLine="r.Target = r.RunMethod(\"getPackageManager\")";
_r.Target = _r.RunMethod("getPackageManager");
 //BA.debugLineNum = 507;BA.debugLine="r.Target = r.RunMethod4(\"getPackageInfo\", Array A";
_r.Target = _r.RunMethod4("getPackageInfo",new Object[]{(Object)(_package),(Object)(0x00001000)},new String[]{"java.lang.String","java.lang.int"});
 //BA.debugLineNum = 509;BA.debugLine="Dim permissions() As String";
_permissions = new String[(int) (0)];
java.util.Arrays.fill(_permissions,"");
 //BA.debugLineNum = 510;BA.debugLine="permissions = r.GetField(\"requestedPermissions\")";
_permissions = (String[])(_r.GetField("requestedPermissions"));
 //BA.debugLineNum = 511;BA.debugLine="If permissions = Null Then";
if (_permissions== null) { 
 //BA.debugLineNum = 512;BA.debugLine="Dim permissions(0) As String";
_permissions = new String[(int) (0)];
java.util.Arrays.fill(_permissions,"");
 };
 //BA.debugLineNum = 514;BA.debugLine="Return permissions";
if (true) return _permissions;
 //BA.debugLineNum = 515;BA.debugLine="End Sub";
return null;
}
public static String  _getsourcedir(Object _appinfo) throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
 //BA.debugLineNum = 580;BA.debugLine="Sub GetSourceDir(AppInfo As Object) As String";
 //BA.debugLineNum = 581;BA.debugLine="Try";
try { //BA.debugLineNum = 582;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 583;BA.debugLine="r.Target = AppInfo";
_r.Target = _appinfo;
 //BA.debugLineNum = 584;BA.debugLine="Return r.GetField(\"sourceDir\")";
if (true) return BA.ObjectToString(_r.GetField("sourceDir"));
 } 
       catch (Exception e6) {
			processBA.setLastException(e6); //BA.debugLineNum = 586;BA.debugLine="Return \"\"";
if (true) return "";
 };
 //BA.debugLineNum = 588;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 28;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 31;BA.debugLine="Private applist,extra,extra2,finish As List";
mostCurrent._applist = new anywheresoftware.b4a.objects.collections.List();
mostCurrent._extra = new anywheresoftware.b4a.objects.collections.List();
mostCurrent._extra2 = new anywheresoftware.b4a.objects.collections.List();
mostCurrent._finish = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 32;BA.debugLine="Private toolbar As ACToolBarDark";
mostCurrent._toolbar = new de.amberhome.objects.appcompat.ACToolbarDarkWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Dim ABHelper As ACActionBar";
mostCurrent._abhelper = new de.amberhome.objects.appcompat.ACActionBar();
 //BA.debugLineNum = 34;BA.debugLine="Dim AC As AppCompat";
mostCurrent._ac = new de.amberhome.objects.appcompat.AppCompatBase();
 //BA.debugLineNum = 35;BA.debugLine="Private ListView1 As ListView";
mostCurrent._listview1 = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Dim args(1) As Object";
mostCurrent._args = new Object[(int) (1)];
{
int d0 = mostCurrent._args.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._args[i0] = new Object();
}
}
;
 //BA.debugLineNum = 37;BA.debugLine="Dim Obj1, Obj2, Obj3 As Reflector";
mostCurrent._obj1 = new anywheresoftware.b4a.agraham.reflection.Reflection();
mostCurrent._obj2 = new anywheresoftware.b4a.agraham.reflection.Reflection();
mostCurrent._obj3 = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 38;BA.debugLine="Dim size,flags As Int";
_size = 0;
_flags = 0;
 //BA.debugLineNum = 39;BA.debugLine="Private name,apath,l,Types(1),packName As String";
mostCurrent._name = "";
mostCurrent._apath = "";
mostCurrent._l = "";
mostCurrent._types = new String[(int) (1)];
java.util.Arrays.fill(mostCurrent._types,"");
mostCurrent._packname = "";
 //BA.debugLineNum = 40;BA.debugLine="Private icon As BitmapDrawable";
mostCurrent._icon = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 41;BA.debugLine="Private mcl As MaterialColors";
mostCurrent._mcl = new com.tchart.materialcolors.MaterialColors();
 //BA.debugLineNum = 42;BA.debugLine="Private ab1 As ACButton";
mostCurrent._ab1 = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Private ab2 As ACButton";
mostCurrent._ab2 = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 44;BA.debugLine="Private Panel1 As Panel";
mostCurrent._panel1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Private Label1 As Label";
mostCurrent._label1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 46;BA.debugLine="Private lvt As LVLineWithText";
mostCurrent._lvt = new de.donmanfred.LVLineWithTextWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Private lvt2 As LVLineWithText";
mostCurrent._lvt2 = new de.donmanfred.LVLineWithTextWrapper();
 //BA.debugLineNum = 48;BA.debugLine="Private iv1 As ImageView";
mostCurrent._iv1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 49;BA.debugLine="Private Label2 As Label";
mostCurrent._label2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 50;BA.debugLine="Private Label3 As Label";
mostCurrent._label3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 51;BA.debugLine="Private Panel2 As Panel";
mostCurrent._panel2 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 52;BA.debugLine="Private ab3 As ACButton";
mostCurrent._ab3 = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 54;BA.debugLine="Private Label5 As Label";
mostCurrent._label5 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 55;BA.debugLine="Dim count As Int";
_count = 0;
 //BA.debugLineNum = 56;BA.debugLine="Private md As MaterialDialogBuilder";
mostCurrent._md = new de.amberhome.materialdialogs.MaterialDialogBuilderWrapper();
 //BA.debugLineNum = 57;BA.debugLine="Private Label6 As Label";
mostCurrent._label6 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 58;BA.debugLine="Private Bname As Label";
mostCurrent._bname = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 59;BA.debugLine="Dim bdia As BetterDialogs";
mostCurrent._bdia = new flm.b4a.betterdialogs.BetterDialogs();
 //BA.debugLineNum = 60;BA.debugLine="Dim dias As CustomDialog2";
mostCurrent._dias = new anywheresoftware.b4a.agraham.dialogs.InputDialog.CustomDialog2();
 //BA.debugLineNum = 61;BA.debugLine="Private Label4 As Label";
mostCurrent._label4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 62;BA.debugLine="Private Bpath As Label";
mostCurrent._bpath = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 63;BA.debugLine="Private Bcontent As Label";
mostCurrent._bcontent = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 64;BA.debugLine="Dim ie As INTENTID";
mostCurrent._ie = new b4a.example.intentid();
 //BA.debugLineNum = 65;BA.debugLine="Dim dlgFileExpl As ClsExplorer";
mostCurrent._dlgfileexpl = new com.apex.clsexplorer();
 //BA.debugLineNum = 66;BA.debugLine="Dim dir As String = File.DirRootExternal&\"/APEX\"";
mostCurrent._dir = anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/APEX";
 //BA.debugLineNum = 67;BA.debugLine="Private datas As KeyValueStore";
mostCurrent._datas = new com.apex.keyvaluestore();
 //BA.debugLineNum = 68;BA.debugLine="Private apannel,bpannel As Panel";
mostCurrent._apannel = new anywheresoftware.b4a.objects.PanelWrapper();
mostCurrent._bpannel = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 69;BA.debugLine="Dim b1 As Button";
mostCurrent._b1 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 70;BA.debugLine="End Sub";
return "";
}
public static String  _inf_click() throws Exception{
 //BA.debugLineNum = 182;BA.debugLine="Sub inf_click";
 //BA.debugLineNum = 183;BA.debugLine="md_build";
_md_build();
 //BA.debugLineNum = 185;BA.debugLine="End Sub";
return "";
}
public static String  _ini_extract() throws Exception{
String _s = "";
String _b = "";
String _d1 = "";
String _d2 = "";
String _master = "";
 //BA.debugLineNum = 259;BA.debugLine="Sub ini_extract";
 //BA.debugLineNum = 260;BA.debugLine="lvt.Visible=True";
mostCurrent._lvt.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 261;BA.debugLine="Dim s As String = extra2.Get(1)";
_s = BA.ObjectToString(mostCurrent._extra2.Get((int) (1)));
 //BA.debugLineNum = 262;BA.debugLine="Log(\"pak: \"&s)";
anywheresoftware.b4a.keywords.Common.Log("pak: "+_s);
 //BA.debugLineNum = 263;BA.debugLine="Dim b As String = extra2.Get(0)";
_b = BA.ObjectToString(mostCurrent._extra2.Get((int) (0)));
 //BA.debugLineNum = 264;BA.debugLine="Log(\"Name: \"&b)";
anywheresoftware.b4a.keywords.Common.Log("Name: "+_b);
 //BA.debugLineNum = 265;BA.debugLine="name=b";
mostCurrent._name = _b;
 //BA.debugLineNum = 266;BA.debugLine="Label4.text=name";
mostCurrent._label4.setText(BA.ObjectToCharSequence(mostCurrent._name));
 //BA.debugLineNum = 267;BA.debugLine="Dim d1,d2,master As String";
_d1 = "";
_d2 = "";
_master = "";
 //BA.debugLineNum = 268;BA.debugLine="d1=File.DirRootExternal&\"/APEX\"";
_d1 = anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/APEX";
 //BA.debugLineNum = 269;BA.debugLine="d2=datas.GetSimple(\"0\")";
_d2 = mostCurrent._datas._getsimple("0");
 //BA.debugLineNum = 270;BA.debugLine="If datas.ContainsKey(\"0\") Then";
if (mostCurrent._datas._containskey("0")) { 
 //BA.debugLineNum = 271;BA.debugLine="master=d2";
_master = _d2;
 };
 //BA.debugLineNum = 273;BA.debugLine="Log(\"copy value: \"&s)";
anywheresoftware.b4a.keywords.Common.Log("copy value: "+_s);
 //BA.debugLineNum = 274;BA.debugLine="If File.Exists(master,s&\".apk\") Then";
if (anywheresoftware.b4a.keywords.Common.File.Exists(_master,_s+".apk")) { 
 //BA.debugLineNum = 275;BA.debugLine="int_folder";
_int_folder();
 }else {
 //BA.debugLineNum = 278;BA.debugLine="File.Copy(GetParentPath(GetSourceDir(GetActiviti";
anywheresoftware.b4a.keywords.Common.File.Copy(_getparentpath(_getsourcedir(_getactivitiesinfo(_s))),_getfilename(_getsourcedir(_getactivitiesinfo(_s))),_master,_s+".apk");
 //BA.debugLineNum = 279;BA.debugLine="end_extract";
_end_extract();
 };
 //BA.debugLineNum = 282;BA.debugLine="End Sub";
return "";
}
public static String  _int_folder() throws Exception{
String _error = "";
anywheresoftware.b4a.objects.LabelWrapper _errorb = null;
int _tu = 0;
 //BA.debugLineNum = 338;BA.debugLine="Sub int_folder";
 //BA.debugLineNum = 339;BA.debugLine="Dim error As String =\"File allready exists!\"";
_error = "File allready exists!";
 //BA.debugLineNum = 340;BA.debugLine="Dim errorb As Label";
_errorb = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 341;BA.debugLine="errorb.Initialize(\"errorb\")";
_errorb.Initialize(mostCurrent.activityBA,"errorb");
 //BA.debugLineNum = 342;BA.debugLine="errorb.Text=error";
_errorb.setText(BA.ObjectToCharSequence(_error));
 //BA.debugLineNum = 343;BA.debugLine="errorb.Gravity=Gravity.CENTER";
_errorb.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 344;BA.debugLine="errorb.TextColor=mcl.md_white_1000";
_errorb.setTextColor(mostCurrent._mcl.getmd_white_1000());
 //BA.debugLineNum = 345;BA.debugLine="errorb.TextSize=18";
_errorb.setTextSize((float) (18));
 //BA.debugLineNum = 346;BA.debugLine="Dim tu As Int";
_tu = 0;
 //BA.debugLineNum = 347;BA.debugLine="bdia.CustomDialog(\"INFO:\",200dip,150dip,errorb,22";
mostCurrent._bdia.CustomDialog((Object)("INFO:"),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (200)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (150)),(anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(_errorb.getObject())),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (220)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1)),(Object)(anywheresoftware.b4a.keywords.Common.Colors.Transparent),(Object)("OK"),(Object)(""),(Object)(""),anywheresoftware.b4a.keywords.Common.False,"bdia",mostCurrent.activityBA);
 //BA.debugLineNum = 349;BA.debugLine="End Sub";
return "";
}
public static String  _listview1_itemclick(int _position,Object _value) throws Exception{
String _filename = "";
String _d = "";
 //BA.debugLineNum = 226;BA.debugLine="Sub ListView1_ItemClick (Position As Int, Value As";
 //BA.debugLineNum = 227;BA.debugLine="Dim filename As String";
_filename = "";
 //BA.debugLineNum = 228;BA.debugLine="For Each d As String In extra";
final anywheresoftware.b4a.BA.IterableList group2 = mostCurrent._extra;
final int groupLen2 = group2.getSize();
for (int index2 = 0;index2 < groupLen2 ;index2++){
_d = BA.ObjectToString(group2.Get(index2));
 //BA.debugLineNum = 229;BA.debugLine="packName=d";
mostCurrent._packname = _d;
 //BA.debugLineNum = 230;BA.debugLine="Log(\"packname. \"&d)";
anywheresoftware.b4a.keywords.Common.Log("packname. "+_d);
 //BA.debugLineNum = 231;BA.debugLine="If Value=packName Then";
if ((_value).equals((Object)(mostCurrent._packname))) { 
 //BA.debugLineNum = 232;BA.debugLine="icon=pak.GetApplicationIcon(packName)";
mostCurrent._icon.setObject((android.graphics.drawable.BitmapDrawable)(_pak.GetApplicationIcon(mostCurrent._packname)));
 //BA.debugLineNum = 233;BA.debugLine="size=File.Size(GetParentPath(GetSourceDir(GetAct";
_size = (int) (anywheresoftware.b4a.keywords.Common.File.Size(_getparentpath(_getsourcedir(_getactivitiesinfo(mostCurrent._packname))),_getfilename(_getsourcedir(_getactivitiesinfo(mostCurrent._packname)))));
 //BA.debugLineNum = 234;BA.debugLine="apath=get_respath(d)";
mostCurrent._apath = BA.ObjectToString(_get_respath(_d));
 //BA.debugLineNum = 235;BA.debugLine="name=pak.GetApplicationLabel(d)";
mostCurrent._name = _pak.GetApplicationLabel(_d);
 //BA.debugLineNum = 237;BA.debugLine="filename=d";
_filename = _d;
 //BA.debugLineNum = 238;BA.debugLine="Log(filename)";
anywheresoftware.b4a.keywords.Common.Log(_filename);
 //BA.debugLineNum = 240;BA.debugLine="Label1.Text=name";
mostCurrent._label1.setText(BA.ObjectToCharSequence(mostCurrent._name));
 //BA.debugLineNum = 241;BA.debugLine="iv1.Bitmap=icon.Bitmap";
mostCurrent._iv1.setBitmap(mostCurrent._icon.getBitmap());
 //BA.debugLineNum = 242;BA.debugLine="Label2.Text=filename";
mostCurrent._label2.setText(BA.ObjectToCharSequence(_filename));
 //BA.debugLineNum = 243;BA.debugLine="Label6.Text=apath";
mostCurrent._label6.setText(BA.ObjectToCharSequence(mostCurrent._apath));
 //BA.debugLineNum = 244;BA.debugLine="Label5.Text=\"size: \"&FormatFileSize(size)";
mostCurrent._label5.setText(BA.ObjectToCharSequence("size: "+_formatfilesize((float) (_size))));
 //BA.debugLineNum = 246;BA.debugLine="Label3.Text=\"Path: \"&apath";
mostCurrent._label3.setText(BA.ObjectToCharSequence("Path: "+mostCurrent._apath));
 //BA.debugLineNum = 247;BA.debugLine="extra2.Clear";
mostCurrent._extra2.Clear();
 //BA.debugLineNum = 248;BA.debugLine="Log(\"-------clear-----\")";
anywheresoftware.b4a.keywords.Common.Log("-------clear-----");
 //BA.debugLineNum = 249;BA.debugLine="extra2.Add(name)";
mostCurrent._extra2.Add((Object)(mostCurrent._name));
 //BA.debugLineNum = 250;BA.debugLine="extra2.Add(d)";
mostCurrent._extra2.Add((Object)(_d));
 //BA.debugLineNum = 251;BA.debugLine="Log(\"->add: \"&d)";
anywheresoftware.b4a.keywords.Common.Log("->add: "+_d);
 //BA.debugLineNum = 252;BA.debugLine="ex_pan";
_ex_pan();
 };
 }
;
 //BA.debugLineNum = 257;BA.debugLine="End Sub";
return "";
}
public static String  _listview1_itemlongclick(int _position,Object _value) throws Exception{
anywheresoftware.b4a.objects.LabelWrapper _lab = null;
String _text = "";
int _res = 0;
anywheresoftware.b4a.objects.IntentWrapper _i = null;
 //BA.debugLineNum = 201;BA.debugLine="Sub Listview1_ItemLongClick (Position As Int, Valu";
 //BA.debugLineNum = 202;BA.debugLine="Dim lab As Label";
_lab = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 203;BA.debugLine="lab.Initialize(\"lab\")";
_lab.Initialize(mostCurrent.activityBA,"lab");
 //BA.debugLineNum = 204;BA.debugLine="Dim text As String = \"Start the Application?\"";
_text = "Start the Application?";
 //BA.debugLineNum = 205;BA.debugLine="lab.TextSize=18";
_lab.setTextSize((float) (18));
 //BA.debugLineNum = 206;BA.debugLine="lab.Gravity=Gravity.CENTER";
_lab.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 207;BA.debugLine="lab.TextColor=mcl.md_white_1000";
_lab.setTextColor(mostCurrent._mcl.getmd_white_1000());
 //BA.debugLineNum = 208;BA.debugLine="lab.Text=text";
_lab.setText(BA.ObjectToCharSequence(_text));
 //BA.debugLineNum = 209;BA.debugLine="Dim res As Int";
_res = 0;
 //BA.debugLineNum = 210;BA.debugLine="res =bdia.CustomDialog(\"Start?\",200dip,150dip,lab";
_res = mostCurrent._bdia.CustomDialog((Object)("Start?"),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (200)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (150)),(anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(_lab.getObject())),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (250)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)),(int) (2),(Object)(anywheresoftware.b4a.keywords.Common.Colors.Transparent),(Object)("OK"),(Object)(""),(Object)("Cancel"),anywheresoftware.b4a.keywords.Common.False,"bdia",mostCurrent.activityBA);
 //BA.debugLineNum = 211;BA.debugLine="If res=DialogResponse.POSITIVE Then";
if (_res==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 212;BA.debugLine="Dim i As Intent";
_i = new anywheresoftware.b4a.objects.IntentWrapper();
 //BA.debugLineNum = 213;BA.debugLine="i.Initialize(extra.Get(Position),\"\")";
_i.Initialize(BA.ObjectToString(mostCurrent._extra.Get(_position)),"");
 //BA.debugLineNum = 214;BA.debugLine="i = pak.GetApplicationIntent(extra.Get(Position))";
_i = _pak.GetApplicationIntent(BA.ObjectToString(mostCurrent._extra.Get(_position)));
 //BA.debugLineNum = 215;BA.debugLine="Try";
try { //BA.debugLineNum = 216;BA.debugLine="StartActivity(i)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(_i.getObject()));
 } 
       catch (Exception e17) {
			processBA.setLastException(e17); };
 }else {
 //BA.debugLineNum = 221;BA.debugLine="ToastMessageShow(\"canceled..\",False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("canceled.."),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 224;BA.debugLine="End Sub";
return "";
}
public static String  _md_build() throws Exception{
 //BA.debugLineNum = 351;BA.debugLine="Sub md_build";
 //BA.debugLineNum = 352;BA.debugLine="md.Theme(md.THEME_DARK)";
mostCurrent._md.Theme(mostCurrent._md.THEME_DARK);
 //BA.debugLineNum = 353;BA.debugLine="md.CanceledOnTouchOutside(True)";
mostCurrent._md.CanceledOnTouchOutside(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 354;BA.debugLine="md.Cancelable(True)";
mostCurrent._md.Cancelable(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 355;BA.debugLine="md.ContentColor(mcl.md_white_1000)";
mostCurrent._md.ContentColor(mostCurrent._mcl.getmd_white_1000());
 //BA.debugLineNum = 356;BA.debugLine="md.content(pak.GetApplicationLabel(\"com.apex\")&\",";
mostCurrent._md.Content(BA.ObjectToCharSequence(_pak.GetApplicationLabel("com.apex")+", Version: "+_pak.GetVersionName("com.apex")+" | Build: "+BA.NumberToString(_pak.GetVersionCode("com.apex"))+"| Welcome to APK Extractor short APEX. It's easy and fast to use, simply tap an app and tap EXPORT. In the settings you can specify an individual path to save. By pressing and holding down an app in the list, this starts after a query Unless otherwise set APEX save your backups under 'sd / APEX /' the app does not need a root. Please note that you can not export system apps with APEX! All Rights Reserved APEX ©2017"));
 //BA.debugLineNum = 358;BA.debugLine="md.PositiveText(\"Ok\")";
mostCurrent._md.PositiveText(BA.ObjectToCharSequence("Ok"));
 //BA.debugLineNum = 359;BA.debugLine="md.ButtonStackedGravity(md.GRAVITY_Start)";
mostCurrent._md.ButtonStackedGravity(mostCurrent._md.GRAVITY_START);
 //BA.debugLineNum = 360;BA.debugLine="md.ForceStacking(True)";
mostCurrent._md.ForceStacking(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 361;BA.debugLine="md.ContentGravity(md.GRAVITY_START)";
mostCurrent._md.ContentGravity(mostCurrent._md.GRAVITY_START);
 //BA.debugLineNum = 362;BA.debugLine="md.Title(\"About APEX:\")";
mostCurrent._md.Title(BA.ObjectToCharSequence("About APEX:"));
 //BA.debugLineNum = 363;BA.debugLine="md.PositiveColor(mcl.md_white_1000)";
mostCurrent._md.PositiveColor(mostCurrent._mcl.getmd_white_1000());
 //BA.debugLineNum = 364;BA.debugLine="md.show";
mostCurrent._md.Show();
 //BA.debugLineNum = 365;BA.debugLine="End Sub";
return "";
}
public static String  _md_buttonpressed(de.amberhome.materialdialogs.MaterialDialogWrapper _dialog,String _action) throws Exception{
 //BA.debugLineNum = 367;BA.debugLine="Sub md_ButtonPressed (Dialog As MaterialDialog, Ac";
 //BA.debugLineNum = 369;BA.debugLine="Select Action";
switch (BA.switchObjectToInt(_action,_dialog.ACTION_POSITIVE,_dialog.ACTION_NEGATIVE,_dialog.ACTION_NEUTRAL)) {
case 0: {
 break; }
case 1: {
 break; }
case 2: {
 break; }
}
;
 //BA.debugLineNum = 380;BA.debugLine="End Sub";
return "";
}
public static String  _minutes_hours(int _ms) throws Exception{
int _val = 0;
int _hours = 0;
int _minutes = 0;
 //BA.debugLineNum = 523;BA.debugLine="Sub minutes_hours ( ms As Int ) As String";
 //BA.debugLineNum = 524;BA.debugLine="Dim val,hours,minutes As Int";
_val = 0;
_hours = 0;
_minutes = 0;
 //BA.debugLineNum = 525;BA.debugLine="val = ms";
_val = _ms;
 //BA.debugLineNum = 526;BA.debugLine="hours = Floor(val / 60)";
_hours = (int) (anywheresoftware.b4a.keywords.Common.Floor(_val/(double)60));
 //BA.debugLineNum = 527;BA.debugLine="minutes = val Mod 60";
_minutes = (int) (_val%60);
 //BA.debugLineNum = 528;BA.debugLine="Return NumberFormat(hours, 1, 0) & \":\" & NumberFo";
if (true) return anywheresoftware.b4a.keywords.Common.NumberFormat(_hours,(int) (1),(int) (0))+":"+anywheresoftware.b4a.keywords.Common.NumberFormat(_minutes,(int) (2),(int) (0));
 //BA.debugLineNum = 529;BA.debugLine="End Sub";
return "";
}

public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        main._process_globals();
sett._process_globals();
starter._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 20;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 23;BA.debugLine="Private pak As PackageManager";
_pak = new anywheresoftware.b4a.phone.PackageManagerWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Dim tim As Timer";
_tim = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 26;BA.debugLine="End Sub";
return "";
}
public static String  _setdivider(anywheresoftware.b4a.objects.ListViewWrapper _lv,int _color,int _height) throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _cd = null;
 //BA.debugLineNum = 493;BA.debugLine="Sub SetDivider(lv As ListView, Color As Int, Heigh";
 //BA.debugLineNum = 494;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 495;BA.debugLine="r.Target = lv";
_r.Target = (Object)(_lv.getObject());
 //BA.debugLineNum = 496;BA.debugLine="Dim CD As ColorDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 497;BA.debugLine="CD.Initialize(Color, 0)";
_cd.Initialize(_color,(int) (0));
 //BA.debugLineNum = 498;BA.debugLine="r.RunMethod4(\"setDivider\", Array As Object(CD), A";
_r.RunMethod4("setDivider",new Object[]{(Object)(_cd.getObject())},new String[]{"android.graphics.drawable.Drawable"});
 //BA.debugLineNum = 499;BA.debugLine="r.RunMethod2(\"setDividerHeight\", Height, \"java.la";
_r.RunMethod2("setDividerHeight",BA.NumberToString(_height),"java.lang.int");
 //BA.debugLineNum = 500;BA.debugLine="End Sub";
return "";
}
public static String  _sett_click() throws Exception{
 //BA.debugLineNum = 178;BA.debugLine="Sub sett_click";
 //BA.debugLineNum = 179;BA.debugLine="StartActivity(sett)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._sett.getObject()));
 //BA.debugLineNum = 180;BA.debugLine="End Sub";
return "";
}
public static String  _start() throws Exception{
 //BA.debugLineNum = 422;BA.debugLine="Sub start";
 //BA.debugLineNum = 423;BA.debugLine="If tim.Enabled=False Then";
if (_tim.getEnabled()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 424;BA.debugLine="tim.Enabled=True";
_tim.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 425;BA.debugLine="lvt.Visible=True";
mostCurrent._lvt.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 427;BA.debugLine="tim.Enabled=False";
_tim.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 428;BA.debugLine="ti_start";
_ti_start();
 };
 //BA.debugLineNum = 430;BA.debugLine="End Sub";
return "";
}
public static String  _ti_start() throws Exception{
 //BA.debugLineNum = 432;BA.debugLine="Sub ti_start";
 //BA.debugLineNum = 433;BA.debugLine="tim.Enabled=True";
_tim.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 434;BA.debugLine="End Sub";
return "";
}
public static String  _tim_tick() throws Exception{
 //BA.debugLineNum = 190;BA.debugLine="Sub tim_Tick";
 //BA.debugLineNum = 191;BA.debugLine="count=count+1";
_count = (int) (_count+1);
 //BA.debugLineNum = 193;BA.debugLine="lvt.Visible=True";
mostCurrent._lvt.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 194;BA.debugLine="lvt.Value=count*10*2.5";
mostCurrent._lvt.setValue((int) (_count*10*2.5));
 //BA.debugLineNum = 195;BA.debugLine="If count = 4 Then";
if (_count==4) { 
 //BA.debugLineNum = 196;BA.debugLine="tim.Enabled=False";
_tim.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 197;BA.debugLine="count=0";
_count = (int) (0);
 //BA.debugLineNum = 198;BA.debugLine="ini_extract";
_ini_extract();
 };
 //BA.debugLineNum = 200;BA.debugLine="End Sub";
return "";
}
public static String  _toolbar_navigationitemclick() throws Exception{
 //BA.debugLineNum = 599;BA.debugLine="Sub toolbar_NavigationItemClick";
 //BA.debugLineNum = 601;BA.debugLine="End Sub";
return "";
}
}

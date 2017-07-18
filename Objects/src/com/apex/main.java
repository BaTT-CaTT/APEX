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
public anywheresoftware.b4a.objects.ImageViewWrapper _iv1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label3 = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel2 = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _ab3 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label5 = null;
public static int _count = 0;
public de.amberhome.materialdialogs.MaterialDialogBuilderWrapper _md = null;
public anywheresoftware.b4a.objects.LabelWrapper _label6 = null;
public flm.b4a.betterdialogs.BetterDialogs _bdia = null;
public com.apex.starter _starter = null;

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
return vis;}
public static String  _ab1_click() throws Exception{
 //BA.debugLineNum = 272;BA.debugLine="Sub ab1_Click";
 //BA.debugLineNum = 274;BA.debugLine="If tim.Enabled=False Then";
if (_tim.getEnabled()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 275;BA.debugLine="tim.Enabled=True";
_tim.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 276;BA.debugLine="ex_pan2";
_ex_pan2();
 }else {
 //BA.debugLineNum = 279;BA.debugLine="tim.Enabled=False";
_tim.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 280;BA.debugLine="ex_pan2";
_ex_pan2();
 //BA.debugLineNum = 281;BA.debugLine="ti_start";
_ti_start();
 };
 //BA.debugLineNum = 284;BA.debugLine="End Sub";
return "";
}
public static String  _ab2_click() throws Exception{
 //BA.debugLineNum = 268;BA.debugLine="Sub ab2_Click";
 //BA.debugLineNum = 269;BA.debugLine="ex_pan";
_ex_pan();
 //BA.debugLineNum = 270;BA.debugLine="End Sub";
return "";
}
public static String  _ab3_click() throws Exception{
 //BA.debugLineNum = 290;BA.debugLine="Sub ab3_Click";
 //BA.debugLineNum = 291;BA.debugLine="tim.Enabled=False";
_tim.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 292;BA.debugLine="Panel2.Visible=False";
mostCurrent._panel2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 293;BA.debugLine="End Sub";
return "";
}
public static String  _activity_create(boolean _firsttime) throws Exception{
anywheresoftware.b4a.objects.drawable.BitmapDrawable _bd = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _bd1 = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _bd2 = null;
anywheresoftware.b4a.objects.LabelWrapper _l1 = null;
anywheresoftware.b4a.objects.LabelWrapper _l2 = null;
 //BA.debugLineNum = 60;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 62;BA.debugLine="Activity.LoadLayout(\"2\")";
mostCurrent._activity.LoadLayout("2",mostCurrent.activityBA);
 //BA.debugLineNum = 63;BA.debugLine="toolbar.SetAsActionBar";
mostCurrent._toolbar.SetAsActionBar(mostCurrent.activityBA);
 //BA.debugLineNum = 66;BA.debugLine="toolbar.PopupTheme=toolbar.THEME_DARK";
mostCurrent._toolbar.setPopupTheme(mostCurrent._toolbar.THEME_DARK);
 //BA.debugLineNum = 67;BA.debugLine="toolbar.Title=pak.GetApplicationLabel(\"com.apex\")";
mostCurrent._toolbar.setTitle(BA.ObjectToCharSequence(_pak.GetApplicationLabel("com.apex")));
 //BA.debugLineNum = 68;BA.debugLine="toolbar.SubTitle=\"Native app export\"";
mostCurrent._toolbar.setSubTitle(BA.ObjectToCharSequence("Native app export"));
 //BA.debugLineNum = 69;BA.debugLine="ABHelper.Initialize";
mostCurrent._abhelper.Initialize(mostCurrent.activityBA);
 //BA.debugLineNum = 70;BA.debugLine="extra.Initialize";
mostCurrent._extra.Initialize();
 //BA.debugLineNum = 71;BA.debugLine="extra2.Initialize";
mostCurrent._extra2.Initialize();
 //BA.debugLineNum = 72;BA.debugLine="finish.Initialize";
mostCurrent._finish.Initialize();
 //BA.debugLineNum = 73;BA.debugLine="md.Initialize(\"md\")";
mostCurrent._md.Initialize(mostCurrent.activityBA,"md");
 //BA.debugLineNum = 75;BA.debugLine="Dim bd,bd1,bd2 As BitmapDrawable";
_bd = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
_bd1 = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
_bd2 = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 76;BA.debugLine="bd1.Initialize(LoadBitmap(File.DirAssets,\"menu-1.";
_bd1.Initialize((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"menu-1.png").getObject()));
 //BA.debugLineNum = 77;BA.debugLine="Activity.AddMenuItem3(\"Menu\", \"Menu\", bd1.Bitmap,";
mostCurrent._activity.AddMenuItem3(BA.ObjectToCharSequence("Menu"),"Menu",_bd1.getBitmap(),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 78;BA.debugLine="bd.Initialize(LoadBitmap(File.DirAssets,\"info.png";
_bd.Initialize((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"info.png").getObject()));
 //BA.debugLineNum = 79;BA.debugLine="Activity.AddMenuItem2(\"Info\", \"inf\", bd.Bitmap)";
mostCurrent._activity.AddMenuItem2(BA.ObjectToCharSequence("Info"),"inf",_bd.getBitmap());
 //BA.debugLineNum = 80;BA.debugLine="bd2.Initialize(LoadBitmap(File.DirAssets,\"Minus R";
_bd2.Initialize((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"Minus Red Button.png").getObject()));
 //BA.debugLineNum = 81;BA.debugLine="Activity.AddMenuItem2(\"Exit\", \"close\",bd2.Bitmap)";
mostCurrent._activity.AddMenuItem2(BA.ObjectToCharSequence("Exit"),"close",_bd2.getBitmap());
 //BA.debugLineNum = 83;BA.debugLine="Dim l1,l2 As Label";
_l1 = new anywheresoftware.b4a.objects.LabelWrapper();
_l2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 84;BA.debugLine="l1=ListView1.TwoLinesAndBitmap.Label";
_l1 = mostCurrent._listview1.getTwoLinesAndBitmap().Label;
 //BA.debugLineNum = 85;BA.debugLine="l2=ListView1.TwoLinesAndBitmap.SecondLabel";
_l2 = mostCurrent._listview1.getTwoLinesAndBitmap().SecondLabel;
 //BA.debugLineNum = 86;BA.debugLine="l1.TextSize=19";
_l1.setTextSize((float) (19));
 //BA.debugLineNum = 87;BA.debugLine="l1.TextColor=mcl.md_black_1000";
_l1.setTextColor(mostCurrent._mcl.getmd_black_1000());
 //BA.debugLineNum = 88;BA.debugLine="l2.TextSize=14";
_l2.setTextSize((float) (14));
 //BA.debugLineNum = 89;BA.debugLine="l2.TextColor=mcl.md_blue_400";
_l2.setTextColor(mostCurrent._mcl.getmd_blue_400());
 //BA.debugLineNum = 90;BA.debugLine="ListView1.TwoLinesAndBitmap.ItemHeight=70dip";
mostCurrent._listview1.getTwoLinesAndBitmap().setItemHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (70)));
 //BA.debugLineNum = 91;BA.debugLine="ListView1.TwoLinesAndBitmap.ImageView.Height=50di";
mostCurrent._listview1.getTwoLinesAndBitmap().ImageView.setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
 //BA.debugLineNum = 92;BA.debugLine="ListView1.Color=Colors.Transparent";
mostCurrent._listview1.setColor(anywheresoftware.b4a.keywords.Common.Colors.Transparent);
 //BA.debugLineNum = 94;BA.debugLine="Panel1.Visible=False";
mostCurrent._panel1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 95;BA.debugLine="Panel2.Visible=False";
mostCurrent._panel2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 96;BA.debugLine="lvt.Visible=False";
mostCurrent._lvt.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 97;BA.debugLine="ab1.Text=\"Export\"";
mostCurrent._ab1.setText(BA.ObjectToCharSequence("Export"));
 //BA.debugLineNum = 98;BA.debugLine="ab1.ButtonColor=mcl.md_amber_300";
mostCurrent._ab1.setButtonColor(mostCurrent._mcl.getmd_amber_300());
 //BA.debugLineNum = 99;BA.debugLine="ab2.Text=\"Cancel\"";
mostCurrent._ab2.setText(BA.ObjectToCharSequence("Cancel"));
 //BA.debugLineNum = 100;BA.debugLine="ab2.ButtonColor=mcl.md_brown_400";
mostCurrent._ab2.setButtonColor(mostCurrent._mcl.getmd_brown_400());
 //BA.debugLineNum = 101;BA.debugLine="ab3.Text=\"cancel\"";
mostCurrent._ab3.setText(BA.ObjectToCharSequence("cancel"));
 //BA.debugLineNum = 102;BA.debugLine="ab3.ButtonColor=Colors.ARGB(200,255,255,255)";
mostCurrent._ab3.setButtonColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (200),(int) (255),(int) (255),(int) (255)));
 //BA.debugLineNum = 103;BA.debugLine="Activity.Color=mcl.md_white_1000";
mostCurrent._activity.setColor(mostCurrent._mcl.getmd_white_1000());
 //BA.debugLineNum = 105;BA.debugLine="tim.Initialize(\"tim\",1000)";
_tim.Initialize(processBA,"tim",(long) (1000));
 //BA.debugLineNum = 106;BA.debugLine="count=0";
_count = (int) (0);
 //BA.debugLineNum = 107;BA.debugLine="apps_start";
_apps_start();
 //BA.debugLineNum = 108;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 114;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 116;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 110;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 112;BA.debugLine="End Sub";
return "";
}
public static String  _apps_start() throws Exception{
int _i = 0;
String _total = "";
 //BA.debugLineNum = 313;BA.debugLine="Sub apps_start";
 //BA.debugLineNum = 315;BA.debugLine="ListView1.Clear";
mostCurrent._listview1.Clear();
 //BA.debugLineNum = 316;BA.debugLine="extra.Clear";
mostCurrent._extra.Clear();
 //BA.debugLineNum = 317;BA.debugLine="applist=pak.GetInstalledPackages";
mostCurrent._applist = _pak.GetInstalledPackages();
 //BA.debugLineNum = 318;BA.debugLine="Obj1.Target = Obj1.GetContext";
mostCurrent._obj1.Target = (Object)(mostCurrent._obj1.GetContext(processBA));
 //BA.debugLineNum = 319;BA.debugLine="Obj1.Target = Obj1.RunMethod(\"getPackageManager\")";
mostCurrent._obj1.Target = mostCurrent._obj1.RunMethod("getPackageManager");
 //BA.debugLineNum = 320;BA.debugLine="Obj2.Target = Obj1.RunMethod2(\"getInstalledPackag";
mostCurrent._obj2.Target = mostCurrent._obj1.RunMethod2("getInstalledPackages",BA.NumberToString(0),"java.lang.int");
 //BA.debugLineNum = 321;BA.debugLine="size = Obj2.RunMethod(\"size\")";
_size = (int)(BA.ObjectToNumber(mostCurrent._obj2.RunMethod("size")));
 //BA.debugLineNum = 322;BA.debugLine="For i = 0 To size -1";
{
final int step8 = 1;
final int limit8 = (int) (_size-1);
for (_i = (int) (0) ; (step8 > 0 && _i <= limit8) || (step8 < 0 && _i >= limit8); _i = ((int)(0 + _i + step8)) ) {
 //BA.debugLineNum = 323;BA.debugLine="Obj3.Target = Obj2.RunMethod2(\"get\", i, \"java.la";
mostCurrent._obj3.Target = mostCurrent._obj2.RunMethod2("get",BA.NumberToString(_i),"java.lang.int");
 //BA.debugLineNum = 324;BA.debugLine="size = Obj2.RunMethod(\"size\")";
_size = (int)(BA.ObjectToNumber(mostCurrent._obj2.RunMethod("size")));
 //BA.debugLineNum = 325;BA.debugLine="Obj3.Target = Obj3.GetField(\"applicationInfo\") '";
mostCurrent._obj3.Target = mostCurrent._obj3.GetField("applicationInfo");
 //BA.debugLineNum = 326;BA.debugLine="flags = Obj3.GetField(\"flags\")";
_flags = (int)(BA.ObjectToNumber(mostCurrent._obj3.GetField("flags")));
 //BA.debugLineNum = 327;BA.debugLine="packName = Obj3.GetField(\"packageName\")";
mostCurrent._packname = BA.ObjectToString(mostCurrent._obj3.GetField("packageName"));
 //BA.debugLineNum = 328;BA.debugLine="If Bit.And(flags, 1)  = 0 Then";
if (anywheresoftware.b4a.keywords.Common.Bit.And(_flags,(int) (1))==0) { 
 //BA.debugLineNum = 330;BA.debugLine="args(0) = Obj3.Target";
mostCurrent._args[(int) (0)] = mostCurrent._obj3.Target;
 //BA.debugLineNum = 331;BA.debugLine="Types(0) = \"android.content.pm.ApplicationInfo\"";
mostCurrent._types[(int) (0)] = "android.content.pm.ApplicationInfo";
 //BA.debugLineNum = 332;BA.debugLine="name = Obj1.RunMethod4(\"getApplicationLabel\", a";
mostCurrent._name = BA.ObjectToString(mostCurrent._obj1.RunMethod4("getApplicationLabel",mostCurrent._args,mostCurrent._types));
 //BA.debugLineNum = 333;BA.debugLine="icon = Obj1.RunMethod4(\"getApplicationIcon\", ar";
mostCurrent._icon.setObject((android.graphics.drawable.BitmapDrawable)(mostCurrent._obj1.RunMethod4("getApplicationIcon",mostCurrent._args,mostCurrent._types)));
 //BA.debugLineNum = 334;BA.debugLine="apath= GetParentPath(GetSourceDir(GetActivities";
mostCurrent._apath = _getparentpath(_getsourcedir(_getactivitiesinfo(mostCurrent._packname)));
 //BA.debugLineNum = 335;BA.debugLine="Dim total As String";
_total = "";
 //BA.debugLineNum = 336;BA.debugLine="total = File.Size(GetParentPath(GetSourceDir(Ge";
_total = BA.NumberToString(anywheresoftware.b4a.keywords.Common.File.Size(_getparentpath(_getsourcedir(_getactivitiesinfo(mostCurrent._packname))),_getfilename(_getsourcedir(_getactivitiesinfo(mostCurrent._packname)))));
 //BA.debugLineNum = 337;BA.debugLine="ListView1.AddTwoLinesAndBitmap2(name,packName&\"";
mostCurrent._listview1.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence(mostCurrent._name),BA.ObjectToCharSequence(mostCurrent._packname+", "+_formatfilesize((float)(Double.parseDouble(_total)))),mostCurrent._icon.getBitmap(),(Object)(mostCurrent._packname));
 //BA.debugLineNum = 338;BA.debugLine="extra.Add(packName)";
mostCurrent._extra.Add((Object)(mostCurrent._packname));
 };
 }
};
 //BA.debugLineNum = 341;BA.debugLine="End Sub";
return "";
}
public static String  _close_click() throws Exception{
 //BA.debugLineNum = 121;BA.debugLine="Sub close_click";
 //BA.debugLineNum = 122;BA.debugLine="ExitApplication";
anywheresoftware.b4a.keywords.Common.ExitApplication();
 //BA.debugLineNum = 123;BA.debugLine="End Sub";
return "";
}
public static String  _converttotimeformat(int _ms) throws Exception{
int _seconds = 0;
int _minutes = 0;
 //BA.debugLineNum = 357;BA.debugLine="Sub ConvertToTimeFormat(ms As Int) As String";
 //BA.debugLineNum = 358;BA.debugLine="Dim seconds, minutes As Int";
_seconds = 0;
_minutes = 0;
 //BA.debugLineNum = 359;BA.debugLine="seconds = Round(ms / 1000)";
_seconds = (int) (anywheresoftware.b4a.keywords.Common.Round(_ms/(double)1000));
 //BA.debugLineNum = 360;BA.debugLine="minutes = Floor(seconds / 60)";
_minutes = (int) (anywheresoftware.b4a.keywords.Common.Floor(_seconds/(double)60));
 //BA.debugLineNum = 361;BA.debugLine="seconds = seconds Mod 60";
_seconds = (int) (_seconds%60);
 //BA.debugLineNum = 362;BA.debugLine="Return NumberFormat(minutes, 1, 0) & \":\" & Number";
if (true) return anywheresoftware.b4a.keywords.Common.NumberFormat(_minutes,(int) (1),(int) (0))+":"+anywheresoftware.b4a.keywords.Common.NumberFormat(_seconds,(int) (2),(int) (0));
 //BA.debugLineNum = 363;BA.debugLine="End Sub";
return "";
}
public static String  _end_extract() throws Exception{
String _s = "";
 //BA.debugLineNum = 198;BA.debugLine="Sub end_extract";
 //BA.debugLineNum = 199;BA.debugLine="tim.Enabled=False";
_tim.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 200;BA.debugLine="lvt.Visible=False";
mostCurrent._lvt.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 201;BA.debugLine="Dim s As String = extra2.Get(1)";
_s = BA.ObjectToString(mostCurrent._extra2.Get((int) (1)));
 //BA.debugLineNum = 202;BA.debugLine="Log(\"pak: \"&s)";
anywheresoftware.b4a.keywords.Common.Log("pak: "+_s);
 //BA.debugLineNum = 203;BA.debugLine="name=pak.GetApplicationLabel(s)";
mostCurrent._name = _pak.GetApplicationLabel(_s);
 //BA.debugLineNum = 204;BA.debugLine="If File.Exists(File.DirRootExternal&\"/APEX/backup";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/APEX/backups",_s+".apk")) { 
 //BA.debugLineNum = 205;BA.debugLine="ex_pan2";
_ex_pan2();
 //BA.debugLineNum = 206;BA.debugLine="end_msg";
_end_msg();
 };
 //BA.debugLineNum = 208;BA.debugLine="End Sub";
return "";
}
public static String  _end_msg() throws Exception{
anywheresoftware.b4a.objects.ListViewWrapper _lv1 = null;
anywheresoftware.b4a.objects.LabelWrapper _l1 = null;
anywheresoftware.b4a.objects.LabelWrapper _l2 = null;
String _s = "";
int _u = 0;
 //BA.debugLineNum = 210;BA.debugLine="Sub end_msg";
 //BA.debugLineNum = 211;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 212;BA.debugLine="Dim lv1 As ListView";
_lv1 = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 213;BA.debugLine="lv1.Initialize(\"lv1\")";
_lv1.Initialize(mostCurrent.activityBA,"lv1");
 //BA.debugLineNum = 214;BA.debugLine="lv1.Enabled=True";
_lv1.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 215;BA.debugLine="lv1.Color=Colors.Transparent";
_lv1.setColor(anywheresoftware.b4a.keywords.Common.Colors.Transparent);
 //BA.debugLineNum = 216;BA.debugLine="Dim l1,l2 As Label";
_l1 = new anywheresoftware.b4a.objects.LabelWrapper();
_l2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 217;BA.debugLine="l1.Initialize(\"l1\")";
_l1.Initialize(mostCurrent.activityBA,"l1");
 //BA.debugLineNum = 218;BA.debugLine="l2.Initialize(\"l2\")";
_l2.Initialize(mostCurrent.activityBA,"l2");
 //BA.debugLineNum = 219;BA.debugLine="l1=lv1.TwoLinesAndBitmap.Label";
_l1 = _lv1.getTwoLinesAndBitmap().Label;
 //BA.debugLineNum = 220;BA.debugLine="l2=lv1.TwoLinesAndBitmap.SecondLabel";
_l2 = _lv1.getTwoLinesAndBitmap().SecondLabel;
 //BA.debugLineNum = 221;BA.debugLine="l1.TextSize=14";
_l1.setTextSize((float) (14));
 //BA.debugLineNum = 222;BA.debugLine="l1.TextColor=Colors.White";
_l1.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 223;BA.debugLine="l2.TextSize=12";
_l2.setTextSize((float) (12));
 //BA.debugLineNum = 224;BA.debugLine="l2.TextColor=mcl.md_light_blue_500";
_l2.setTextColor(mostCurrent._mcl.getmd_light_blue_500());
 //BA.debugLineNum = 225;BA.debugLine="Dim s As String = extra2.Get(1)";
_s = BA.ObjectToString(mostCurrent._extra2.Get((int) (1)));
 //BA.debugLineNum = 226;BA.debugLine="name=s";
mostCurrent._name = _s;
 //BA.debugLineNum = 227;BA.debugLine="icon=pak.GetApplicationIcon(s)";
mostCurrent._icon.setObject((android.graphics.drawable.BitmapDrawable)(_pak.GetApplicationIcon(_s)));
 //BA.debugLineNum = 228;BA.debugLine="finish=File.ListFiles(File.DirRootExternal&\"/APEX";
mostCurrent._finish = anywheresoftware.b4a.keywords.Common.File.ListFiles(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/APEX/backups");
 //BA.debugLineNum = 229;BA.debugLine="For u = 0 To finish.Size-1";
{
final int step19 = 1;
final int limit19 = (int) (mostCurrent._finish.getSize()-1);
for (_u = (int) (0) ; (step19 > 0 && _u <= limit19) || (step19 < 0 && _u >= limit19); _u = ((int)(0 + _u + step19)) ) {
 //BA.debugLineNum = 230;BA.debugLine="Log(finish.Get(u))";
anywheresoftware.b4a.keywords.Common.Log(BA.ObjectToString(mostCurrent._finish.Get(_u)));
 }
};
 //BA.debugLineNum = 234;BA.debugLine="lv1.AddTwoLinesAndBitmap(\"Export to:\",\"/APEX/back";
_lv1.AddTwoLinesAndBitmap(BA.ObjectToCharSequence("Export to:"),BA.ObjectToCharSequence("/APEX/backups/"+mostCurrent._name+".apk"),mostCurrent._icon.getBitmap());
 //BA.debugLineNum = 235;BA.debugLine="bdia.CustomDialog(\"Export log:\",100dip,50dip,lv1,";
mostCurrent._bdia.CustomDialog((Object)("Export log:"),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)),(anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(_lv1.getObject())),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (300)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (300)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),(Object)(anywheresoftware.b4a.keywords.Common.Colors.Transparent),(Object)("Ok"),(Object)(""),(Object)(""),anywheresoftware.b4a.keywords.Common.False,"bdia",mostCurrent.activityBA);
 //BA.debugLineNum = 236;BA.debugLine="End Sub";
return "";
}
public static String  _ex_pan() throws Exception{
 //BA.debugLineNum = 295;BA.debugLine="Sub ex_pan";
 //BA.debugLineNum = 296;BA.debugLine="If Panel1.Visible=True Then";
if (mostCurrent._panel1.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 297;BA.debugLine="Panel1.Visible=False";
mostCurrent._panel1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 299;BA.debugLine="Panel1.Visible=True";
mostCurrent._panel1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 301;BA.debugLine="End Sub";
return "";
}
public static String  _ex_pan2() throws Exception{
 //BA.debugLineNum = 303;BA.debugLine="Sub ex_pan2";
 //BA.debugLineNum = 304;BA.debugLine="If Panel2.Visible=False Then";
if (mostCurrent._panel2.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 305;BA.debugLine="Panel1.Visible=False";
mostCurrent._panel1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 306;BA.debugLine="Panel2.Visible=True";
mostCurrent._panel2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 308;BA.debugLine="Panel2.Visible=False";
mostCurrent._panel2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 310;BA.debugLine="End Sub";
return "";
}
public static String  _formatfilesize(float _bytes) throws Exception{
String[] _unit = null;
double _po = 0;
double _si = 0;
int _i = 0;
 //BA.debugLineNum = 372;BA.debugLine="Sub FormatFileSize(Bytes As Float) As String";
 //BA.debugLineNum = 374;BA.debugLine="Private Unit() As String = Array As String(\" Byte";
_unit = new String[]{" Byte"," KB"," MB"," GB"," TB"," PB"," EB"," ZB"," YB"};
 //BA.debugLineNum = 376;BA.debugLine="If Bytes = 0 Then";
if (_bytes==0) { 
 //BA.debugLineNum = 377;BA.debugLine="Return \"0 Bytes\"";
if (true) return "0 Bytes";
 }else {
 //BA.debugLineNum = 379;BA.debugLine="Private Po, Si As Double";
_po = 0;
_si = 0;
 //BA.debugLineNum = 380;BA.debugLine="Private I As Int";
_i = 0;
 //BA.debugLineNum = 381;BA.debugLine="Bytes = Abs(Bytes)";
_bytes = (float) (anywheresoftware.b4a.keywords.Common.Abs(_bytes));
 //BA.debugLineNum = 382;BA.debugLine="I = Floor(Logarithm(Bytes, 1024))";
_i = (int) (anywheresoftware.b4a.keywords.Common.Floor(anywheresoftware.b4a.keywords.Common.Logarithm(_bytes,1024)));
 //BA.debugLineNum = 383;BA.debugLine="Po = Power(1024, I)";
_po = anywheresoftware.b4a.keywords.Common.Power(1024,_i);
 //BA.debugLineNum = 384;BA.debugLine="Si = Bytes / Po";
_si = _bytes/(double)_po;
 //BA.debugLineNum = 385;BA.debugLine="Return NumberFormat(Si, 1, 2) & Unit(I)";
if (true) return anywheresoftware.b4a.keywords.Common.NumberFormat(_si,(int) (1),(int) (2))+_unit[_i];
 };
 //BA.debugLineNum = 387;BA.debugLine="End Sub";
return "";
}
public static Object  _get_respath(String _package) throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
 //BA.debugLineNum = 431;BA.debugLine="Sub get_respath(package As String) As Object";
 //BA.debugLineNum = 432;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 433;BA.debugLine="r.Target = r.GetContext";
_r.Target = (Object)(_r.GetContext(processBA));
 //BA.debugLineNum = 434;BA.debugLine="r.Target = r.RunMethod(\"getPackageManager\")";
_r.Target = _r.RunMethod("getPackageManager");
 //BA.debugLineNum = 435;BA.debugLine="r.Target = r.RunMethod3(\"getApplicationInfo\", pac";
_r.Target = _r.RunMethod3("getApplicationInfo",_package,"java.lang.String",BA.NumberToString(0x00000001),"java.lang.int");
 //BA.debugLineNum = 436;BA.debugLine="Return r.GetField(\"dataDir\")";
if (true) return _r.GetField("dataDir");
 //BA.debugLineNum = 437;BA.debugLine="End Sub";
return null;
}
public static Object  _getactivitiesinfo(String _package) throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
 //BA.debugLineNum = 413;BA.debugLine="Sub GetActivitiesInfo(package As String) As Object";
 //BA.debugLineNum = 414;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 415;BA.debugLine="r.Target = r.GetContext";
_r.Target = (Object)(_r.GetContext(processBA));
 //BA.debugLineNum = 416;BA.debugLine="r.Target = r.RunMethod(\"getPackageManager\")";
_r.Target = _r.RunMethod("getPackageManager");
 //BA.debugLineNum = 417;BA.debugLine="r.Target = r.RunMethod3(\"getPackageInfo\", package";
_r.Target = _r.RunMethod3("getPackageInfo",_package,"java.lang.String",BA.NumberToString(0x00000001),"java.lang.int");
 //BA.debugLineNum = 418;BA.debugLine="Return r.GetField(\"applicationInfo\")";
if (true) return _r.GetField("applicationInfo");
 //BA.debugLineNum = 419;BA.debugLine="End Sub";
return null;
}
public static String  _getfilename(String _fullpath) throws Exception{
 //BA.debugLineNum = 389;BA.debugLine="Sub GetFileName(FullPath As String) As String";
 //BA.debugLineNum = 390;BA.debugLine="Return FullPath.SubString(FullPath.LastIndexOf(\"/";
if (true) return _fullpath.substring((int) (_fullpath.lastIndexOf("/")+1));
 //BA.debugLineNum = 391;BA.debugLine="End Sub";
return "";
}
public static String  _getparentpath(String _path) throws Exception{
String _path1 = "";
 //BA.debugLineNum = 394;BA.debugLine="Sub GetParentPath(path As String) As String";
 //BA.debugLineNum = 395;BA.debugLine="Dim Path1 As String";
_path1 = "";
 //BA.debugLineNum = 396;BA.debugLine="If path = \"/\" Then";
if ((_path).equals("/")) { 
 //BA.debugLineNum = 397;BA.debugLine="Return \"/\"";
if (true) return "/";
 };
 //BA.debugLineNum = 399;BA.debugLine="L = path.LastIndexOf(\"/\")";
mostCurrent._l = BA.NumberToString(_path.lastIndexOf("/"));
 //BA.debugLineNum = 400;BA.debugLine="If L = path.Length - 1 Then";
if ((mostCurrent._l).equals(BA.NumberToString(_path.length()-1))) { 
 //BA.debugLineNum = 402;BA.debugLine="Path1 = path.SubString2(0,L)";
_path1 = _path.substring((int) (0),(int)(Double.parseDouble(mostCurrent._l)));
 }else {
 //BA.debugLineNum = 404;BA.debugLine="Path1 = path";
_path1 = _path;
 };
 //BA.debugLineNum = 406;BA.debugLine="L = path.LastIndexOf(\"/\")";
mostCurrent._l = BA.NumberToString(_path.lastIndexOf("/"));
 //BA.debugLineNum = 407;BA.debugLine="If L = 0 Then";
if ((mostCurrent._l).equals(BA.NumberToString(0))) { 
 //BA.debugLineNum = 408;BA.debugLine="L = 1";
mostCurrent._l = BA.NumberToString(1);
 };
 //BA.debugLineNum = 410;BA.debugLine="Return Path1.SubString2(0,L)";
if (true) return _path1.substring((int) (0),(int)(Double.parseDouble(mostCurrent._l)));
 //BA.debugLineNum = 411;BA.debugLine="End Sub";
return "";
}
public static String[]  _getpermissions(String _package) throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
String[] _permissions = null;
 //BA.debugLineNum = 343;BA.debugLine="Sub GetPermissions(Package As String) As String()";
 //BA.debugLineNum = 344;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 345;BA.debugLine="r.Target = r.GetContext";
_r.Target = (Object)(_r.GetContext(processBA));
 //BA.debugLineNum = 346;BA.debugLine="r.Target = r.RunMethod(\"getPackageManager\")";
_r.Target = _r.RunMethod("getPackageManager");
 //BA.debugLineNum = 348;BA.debugLine="r.Target = r.RunMethod4(\"getPackageInfo\", Array A";
_r.Target = _r.RunMethod4("getPackageInfo",new Object[]{(Object)(_package),(Object)(0x00001000)},new String[]{"java.lang.String","java.lang.int"});
 //BA.debugLineNum = 350;BA.debugLine="Dim permissions() As String";
_permissions = new String[(int) (0)];
java.util.Arrays.fill(_permissions,"");
 //BA.debugLineNum = 351;BA.debugLine="permissions = r.GetField(\"requestedPermissions\")";
_permissions = (String[])(_r.GetField("requestedPermissions"));
 //BA.debugLineNum = 352;BA.debugLine="If permissions = Null Then";
if (_permissions== null) { 
 //BA.debugLineNum = 353;BA.debugLine="Dim permissions(0) As String";
_permissions = new String[(int) (0)];
java.util.Arrays.fill(_permissions,"");
 };
 //BA.debugLineNum = 355;BA.debugLine="Return permissions";
if (true) return _permissions;
 //BA.debugLineNum = 356;BA.debugLine="End Sub";
return null;
}
public static String  _getsourcedir(Object _appinfo) throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
 //BA.debugLineNum = 421;BA.debugLine="Sub GetSourceDir(AppInfo As Object) As String";
 //BA.debugLineNum = 422;BA.debugLine="Try";
try { //BA.debugLineNum = 423;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 424;BA.debugLine="r.Target = AppInfo";
_r.Target = _appinfo;
 //BA.debugLineNum = 425;BA.debugLine="Return r.GetField(\"sourceDir\")";
if (true) return BA.ObjectToString(_r.GetField("sourceDir"));
 } 
       catch (Exception e6) {
			processBA.setLastException(e6); //BA.debugLineNum = 427;BA.debugLine="Return \"\"";
if (true) return "";
 };
 //BA.debugLineNum = 429;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 47;BA.debugLine="Private iv1 As ImageView";
mostCurrent._iv1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 48;BA.debugLine="Private Label2 As Label";
mostCurrent._label2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 49;BA.debugLine="Private Label3 As Label";
mostCurrent._label3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 50;BA.debugLine="Private Panel2 As Panel";
mostCurrent._panel2 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 51;BA.debugLine="Private ab3 As ACButton";
mostCurrent._ab3 = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 53;BA.debugLine="Private Label5 As Label";
mostCurrent._label5 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 54;BA.debugLine="Dim count As Int";
_count = 0;
 //BA.debugLineNum = 55;BA.debugLine="Private md As MaterialDialogBuilder";
mostCurrent._md = new de.amberhome.materialdialogs.MaterialDialogBuilderWrapper();
 //BA.debugLineNum = 56;BA.debugLine="Private Label6 As Label";
mostCurrent._label6 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 57;BA.debugLine="Dim bdia As BetterDialogs";
mostCurrent._bdia = new flm.b4a.betterdialogs.BetterDialogs();
 //BA.debugLineNum = 58;BA.debugLine="End Sub";
return "";
}
public static String  _inf_click() throws Exception{
 //BA.debugLineNum = 117;BA.debugLine="Sub inf_click";
 //BA.debugLineNum = 118;BA.debugLine="md_build";
_md_build();
 //BA.debugLineNum = 120;BA.debugLine="End Sub";
return "";
}
public static String  _ini_extract() throws Exception{
byte[] _buffer = null;
anywheresoftware.b4a.objects.streams.File.InputStreamWrapper _it = null;
anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper _out = null;
String _date = "";
String _s = "";
String _b = "";
 //BA.debugLineNum = 169;BA.debugLine="Sub ini_extract";
 //BA.debugLineNum = 171;BA.debugLine="lvt.Visible=True";
mostCurrent._lvt.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 172;BA.debugLine="Dim buffer(1024) As Byte";
_buffer = new byte[(int) (1024)];
;
 //BA.debugLineNum = 173;BA.debugLine="Dim it As InputStream";
_it = new anywheresoftware.b4a.objects.streams.File.InputStreamWrapper();
 //BA.debugLineNum = 174;BA.debugLine="Dim out As OutputStream";
_out = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
 //BA.debugLineNum = 175;BA.debugLine="Dim date As String = DateTime.Date(DateTime.Now)";
_date = anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 176;BA.debugLine="Dim s As String = extra2.Get(1)";
_s = BA.ObjectToString(mostCurrent._extra2.Get((int) (1)));
 //BA.debugLineNum = 177;BA.debugLine="Log(\"pak: \"&s)";
anywheresoftware.b4a.keywords.Common.Log("pak: "+_s);
 //BA.debugLineNum = 178;BA.debugLine="Dim b As String = extra2.Get(0)";
_b = BA.ObjectToString(mostCurrent._extra2.Get((int) (0)));
 //BA.debugLineNum = 179;BA.debugLine="Log(\"Name: \"&b)";
anywheresoftware.b4a.keywords.Common.Log("Name: "+_b);
 //BA.debugLineNum = 180;BA.debugLine="name=pak.GetApplicationLabel(s)";
mostCurrent._name = _pak.GetApplicationLabel(_s);
 //BA.debugLineNum = 182;BA.debugLine="Log(\"copy value: \"&s)";
anywheresoftware.b4a.keywords.Common.Log("copy value: "+_s);
 //BA.debugLineNum = 185;BA.debugLine="If File.IsDirectory(File.DirRootExternal&\"/APEX/b";
if (anywheresoftware.b4a.keywords.Common.File.IsDirectory(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/APEX/backups",mostCurrent._name+".apk")) { 
 }else {
 //BA.debugLineNum = 188;BA.debugLine="File.MakeDir(File.DirRootExternal,\"APEX/backups\"";
anywheresoftware.b4a.keywords.Common.File.MakeDir(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"APEX/backups");
 };
 //BA.debugLineNum = 193;BA.debugLine="File.Copy(GetParentPath(GetSourceDir(GetActiviti";
anywheresoftware.b4a.keywords.Common.File.Copy(_getparentpath(_getsourcedir(_getactivitiesinfo(_s))),_getfilename(_getsourcedir(_getactivitiesinfo(_s))),anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/APEX/backups",_s+".apk");
 //BA.debugLineNum = 194;BA.debugLine="end_extract";
_end_extract();
 //BA.debugLineNum = 197;BA.debugLine="End Sub";
return "";
}
public static String  _listview1_itemclick(int _position,Object _value) throws Exception{
String _filename = "";
String _d = "";
 //BA.debugLineNum = 137;BA.debugLine="Sub ListView1_ItemClick (Position As Int, Value As";
 //BA.debugLineNum = 138;BA.debugLine="Dim filename As String";
_filename = "";
 //BA.debugLineNum = 139;BA.debugLine="For Each d As String In extra";
final anywheresoftware.b4a.BA.IterableList group2 = mostCurrent._extra;
final int groupLen2 = group2.getSize();
for (int index2 = 0;index2 < groupLen2 ;index2++){
_d = BA.ObjectToString(group2.Get(index2));
 //BA.debugLineNum = 140;BA.debugLine="packName=d";
mostCurrent._packname = _d;
 //BA.debugLineNum = 141;BA.debugLine="Log(\"packname. \"&d)";
anywheresoftware.b4a.keywords.Common.Log("packname. "+_d);
 //BA.debugLineNum = 142;BA.debugLine="If Value=packName Then";
if ((_value).equals((Object)(mostCurrent._packname))) { 
 //BA.debugLineNum = 143;BA.debugLine="icon=pak.GetApplicationIcon(packName)";
mostCurrent._icon.setObject((android.graphics.drawable.BitmapDrawable)(_pak.GetApplicationIcon(mostCurrent._packname)));
 //BA.debugLineNum = 144;BA.debugLine="size=File.Size(GetParentPath(GetSourceDir(GetAct";
_size = (int) (anywheresoftware.b4a.keywords.Common.File.Size(_getparentpath(_getsourcedir(_getactivitiesinfo(mostCurrent._packname))),_getfilename(_getsourcedir(_getactivitiesinfo(mostCurrent._packname)))));
 //BA.debugLineNum = 145;BA.debugLine="apath=get_respath(d)";
mostCurrent._apath = BA.ObjectToString(_get_respath(_d));
 //BA.debugLineNum = 146;BA.debugLine="name=pak.GetApplicationLabel(d)";
mostCurrent._name = _pak.GetApplicationLabel(_d);
 //BA.debugLineNum = 147;BA.debugLine="filename=d";
_filename = _d;
 //BA.debugLineNum = 148;BA.debugLine="Log(filename)";
anywheresoftware.b4a.keywords.Common.Log(_filename);
 //BA.debugLineNum = 150;BA.debugLine="Label1.Text=name";
mostCurrent._label1.setText(BA.ObjectToCharSequence(mostCurrent._name));
 //BA.debugLineNum = 151;BA.debugLine="iv1.Bitmap=icon.Bitmap";
mostCurrent._iv1.setBitmap(mostCurrent._icon.getBitmap());
 //BA.debugLineNum = 152;BA.debugLine="Label2.Text=filename";
mostCurrent._label2.setText(BA.ObjectToCharSequence(_filename));
 //BA.debugLineNum = 153;BA.debugLine="Label6.Text=apath";
mostCurrent._label6.setText(BA.ObjectToCharSequence(mostCurrent._apath));
 //BA.debugLineNum = 154;BA.debugLine="Label5.Text=\"size: \"&FormatFileSize(size)";
mostCurrent._label5.setText(BA.ObjectToCharSequence("size: "+_formatfilesize((float) (_size))));
 //BA.debugLineNum = 155;BA.debugLine="Label3.Text=\"Path: \"&apath";
mostCurrent._label3.setText(BA.ObjectToCharSequence("Path: "+mostCurrent._apath));
 //BA.debugLineNum = 156;BA.debugLine="extra2.Clear";
mostCurrent._extra2.Clear();
 //BA.debugLineNum = 157;BA.debugLine="Log(\"-------clear-----\")";
anywheresoftware.b4a.keywords.Common.Log("-------clear-----");
 //BA.debugLineNum = 158;BA.debugLine="extra2.Add(name)";
mostCurrent._extra2.Add((Object)(mostCurrent._name));
 //BA.debugLineNum = 159;BA.debugLine="extra2.Add(d)";
mostCurrent._extra2.Add((Object)(_d));
 //BA.debugLineNum = 160;BA.debugLine="Log(\"->add: \"&d)";
anywheresoftware.b4a.keywords.Common.Log("->add: "+_d);
 //BA.debugLineNum = 162;BA.debugLine="ex_pan";
_ex_pan();
 };
 }
;
 //BA.debugLineNum = 167;BA.debugLine="End Sub";
return "";
}
public static String  _md_build() throws Exception{
 //BA.debugLineNum = 238;BA.debugLine="Sub md_build";
 //BA.debugLineNum = 239;BA.debugLine="md.Theme(md.THEME_DARK)";
mostCurrent._md.Theme(mostCurrent._md.THEME_DARK);
 //BA.debugLineNum = 240;BA.debugLine="md.CanceledOnTouchOutside(True)";
mostCurrent._md.CanceledOnTouchOutside(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 241;BA.debugLine="md.Cancelable(True)";
mostCurrent._md.Cancelable(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 242;BA.debugLine="md.ContentColor(mcl.md_white_1000)";
mostCurrent._md.ContentColor(mostCurrent._mcl.getmd_white_1000());
 //BA.debugLineNum = 243;BA.debugLine="md.content(pak.GetApplicationLabel(\"com.apex\")&\",";
mostCurrent._md.Content(BA.ObjectToCharSequence(_pak.GetApplicationLabel("com.apex")+", Version: "+_pak.GetVersionName("com.apex")+", build: "+BA.NumberToString(_pak.GetVersionCode("com.apex"))+" Programmed in: Basic4A, Android and Java. Java is a free OpenSurce software and is subject to the free Creative public license. Android is under the google license, all associated names and content are protected by the google Inc. software agreement. For more information, visit www.google.com/license. All rights to the code and the design are reserved to BaTTCaTT and its owners..Code by D. Trojan, published by SuloMedia™ www.battcatt.bplaced.net for Recent Infos. All Rights Reserved APEX ©2017"));
 //BA.debugLineNum = 245;BA.debugLine="md.PositiveText(\"Ok\")";
mostCurrent._md.PositiveText(BA.ObjectToCharSequence("Ok"));
 //BA.debugLineNum = 246;BA.debugLine="md.ButtonStackedGravity(md.GRAVITY_END)";
mostCurrent._md.ButtonStackedGravity(mostCurrent._md.GRAVITY_END);
 //BA.debugLineNum = 247;BA.debugLine="md.ForceStacking(True)";
mostCurrent._md.ForceStacking(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 248;BA.debugLine="md.ContentGravity(md.GRAVITY_START)";
mostCurrent._md.ContentGravity(mostCurrent._md.GRAVITY_START);
 //BA.debugLineNum = 249;BA.debugLine="md.Title(\"About APEX:\")";
mostCurrent._md.Title(BA.ObjectToCharSequence("About APEX:"));
 //BA.debugLineNum = 250;BA.debugLine="md.PositiveColor(mcl.md_lime_A200)";
mostCurrent._md.PositiveColor(mostCurrent._mcl.getmd_lime_A200());
 //BA.debugLineNum = 251;BA.debugLine="md.show";
mostCurrent._md.Show();
 //BA.debugLineNum = 252;BA.debugLine="End Sub";
return "";
}
public static String  _md_buttonpressed(de.amberhome.materialdialogs.MaterialDialogWrapper _dialog,String _action) throws Exception{
 //BA.debugLineNum = 254;BA.debugLine="Sub md_ButtonPressed (Dialog As MaterialDialog, Ac";
 //BA.debugLineNum = 256;BA.debugLine="Select Action";
switch (BA.switchObjectToInt(_action,_dialog.ACTION_POSITIVE,_dialog.ACTION_NEGATIVE,_dialog.ACTION_NEUTRAL)) {
case 0: {
 break; }
case 1: {
 break; }
case 2: {
 break; }
}
;
 //BA.debugLineNum = 267;BA.debugLine="End Sub";
return "";
}
public static String  _minutes_hours(int _ms) throws Exception{
int _val = 0;
int _hours = 0;
int _minutes = 0;
 //BA.debugLineNum = 364;BA.debugLine="Sub minutes_hours ( ms As Int ) As String";
 //BA.debugLineNum = 365;BA.debugLine="Dim val,hours,minutes As Int";
_val = 0;
_hours = 0;
_minutes = 0;
 //BA.debugLineNum = 366;BA.debugLine="val = ms";
_val = _ms;
 //BA.debugLineNum = 367;BA.debugLine="hours = Floor(val / 60)";
_hours = (int) (anywheresoftware.b4a.keywords.Common.Floor(_val/(double)60));
 //BA.debugLineNum = 368;BA.debugLine="minutes = val Mod 60";
_minutes = (int) (_val%60);
 //BA.debugLineNum = 369;BA.debugLine="Return NumberFormat(hours, 1, 0) & \":\" & NumberFo";
if (true) return anywheresoftware.b4a.keywords.Common.NumberFormat(_hours,(int) (1),(int) (0))+":"+anywheresoftware.b4a.keywords.Common.NumberFormat(_minutes,(int) (2),(int) (0));
 //BA.debugLineNum = 370;BA.debugLine="End Sub";
return "";
}

public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        main._process_globals();
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
public static String  _ti_start() throws Exception{
 //BA.debugLineNum = 286;BA.debugLine="Sub ti_start";
 //BA.debugLineNum = 287;BA.debugLine="tim.Enabled=True";
_tim.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 288;BA.debugLine="End Sub";
return "";
}
public static String  _tim_tick() throws Exception{
 //BA.debugLineNum = 125;BA.debugLine="Sub tim_Tick";
 //BA.debugLineNum = 126;BA.debugLine="count=count+1";
_count = (int) (_count+1);
 //BA.debugLineNum = 128;BA.debugLine="lvt.Visible=True";
mostCurrent._lvt.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 129;BA.debugLine="lvt.Value=count*10*1.5";
mostCurrent._lvt.setValue((int) (_count*10*1.5));
 //BA.debugLineNum = 130;BA.debugLine="If count = 3 Then";
if (_count==3) { 
 //BA.debugLineNum = 131;BA.debugLine="tim.Enabled=False";
_tim.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 132;BA.debugLine="count=0";
_count = (int) (0);
 //BA.debugLineNum = 133;BA.debugLine="ini_extract";
_ini_extract();
 };
 //BA.debugLineNum = 135;BA.debugLine="End Sub";
return "";
}
public static String  _toolbar_navigationitemclick() throws Exception{
 //BA.debugLineNum = 440;BA.debugLine="Sub toolbar_NavigationItemClick";
 //BA.debugLineNum = 442;BA.debugLine="End Sub";
return "";
}
}

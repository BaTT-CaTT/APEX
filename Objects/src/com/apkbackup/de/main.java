package com.apkbackup.de;


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
			processBA = new BA(this.getApplicationContext(), null, null, "com.apkbackup.de", "com.apkbackup.de.main");
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
		activityBA = new BA(this, layout, processBA, "com.apkbackup.de", "com.apkbackup.de.main");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "com.apkbackup.de.main", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
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
public static String _time = "";
public static String _date = "";
public static anywheresoftware.b4a.objects.Timer _dex = null;
public static anywheresoftware.b4a.objects.Timer _ex = null;
public static anywheresoftware.b4a.objects.Timer _exa = null;
public static String _dir = "";
public anywheresoftware.b4a.objects.collections.List _applist = null;
public anywheresoftware.b4a.objects.collections.List _extra = null;
public anywheresoftware.b4a.objects.collections.List _extra2 = null;
public anywheresoftware.b4a.objects.collections.List _finish = null;
public anywheresoftware.b4a.objects.collections.List _datal = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _cbut = null;
public de.amberhome.objects.appcompat.ACActionBar _abhelper = null;
public de.amberhome.objects.appcompat.AppCompatBase _ac = null;
public Object[] _args = null;
public static int _tcount = 0;
public anywheresoftware.b4a.agraham.reflection.Reflection _obj1 = null;
public anywheresoftware.b4a.agraham.reflection.Reflection _obj2 = null;
public anywheresoftware.b4a.agraham.reflection.Reflection _obj3 = null;
public anywheresoftware.b4a.agraham.reflection.Reflection _obj4 = null;
public anywheresoftware.b4a.agraham.reflection.Reflection _obj5 = null;
public anywheresoftware.b4a.agraham.reflection.Reflection _obj6 = null;
public static int _size = 0;
public static int _flags = 0;
public static int _csize = 0;
public static int _sdk = 0;
public static int _itime = 0;
public static String _name1 = "";
public static String _l = "";
public static String[] _types = null;
public static String _packname = "";
public static String _idt = "";
public static String _apath = "";
public anywheresoftware.b4a.objects.drawable.BitmapDrawable _icon = null;
public anywheresoftware.b4a.objects.drawable.BitmapDrawable _icon2 = null;
public com.tchart.materialcolors.MaterialColors _mcl = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _iv1 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _iv2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label5 = null;
public anywheresoftware.b4a.objects.LabelWrapper _bname = null;
public flm.b4a.betterdialogs.BetterDialogs _bdia = null;
public anywheresoftware.b4a.agraham.dialogs.InputDialog.CustomDialog2 _dias = null;
public anywheresoftware.b4a.objects.LabelWrapper _label4 = null;
public anywheresoftware.b4a.objects.LabelWrapper _bpath = null;
public anywheresoftware.b4a.objects.LabelWrapper _bcontent = null;
public b4a.example.intentid _ie = null;
public com.apkbackup.de.keyvaluestore _datas = null;
public com.apkbackup.de.keyvaluestore _dtime = null;
public com.apkbackup.de.keyvaluestore _bcount = null;
public com.apkbackup.de.keyvaluestore _cop = null;
public anywheresoftware.b4a.objects.PanelWrapper _bbpanel = null;
public anywheresoftware.b4a.objects.ListViewWrapper _dataliste = null;
public anywheresoftware.b4a.agraham.dialogs.InputDialog.CustomDialog2 _bdia2 = null;
public de.amberhome.materialdialogs.MaterialDialogsActivity.MDManager _dia3 = null;
public anywheresoftware.b4a.objects.ScrollViewWrapper _svlist = null;
public com.apkbackup.de.clspaklist _lst = null;
public de.amberhome.materialdialogs.MaterialDialogWrapper _pdia = null;
public anywheresoftware.b4a.objects.collections.List _pn = null;
public MLfiles.Fileslib.MLfiles _sd = null;
public com.apkbackup.de.clsexplorer _ff = null;
public de.amberhome.objects.appcompat.ACFlatButtonWrapper _mb = null;
public de.amberhome.strefresh.AHSwipeToRefresh _asr = null;
public anywheresoftware.b4a.objects.ButtonWrapper _ref = null;
public anywheresoftware.b4a.objects.collections.List _pid = null;
public de.amberhome.objects.appcompat.ACToolbarLightWrapper _toolbar = null;
public anywheresoftware.b4a.keywords.constants.TypefaceWrapper _superfont = null;
public de.amberhome.materialdialogs.MaterialDialogWrapper _deldia = null;
public de.amberhome.objects.appcompat.ACFlatButtonWrapper _chk = null;
public anywheresoftware.b4a.objects.ListViewWrapper _lvmenu = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblinfo = null;
public anywheresoftware.b4a.objects.collections.List _menulist = null;
public anywheresoftware.b4a.objects.SlidingMenuWrapper _sm = null;
public anywheresoftware.b4a.objects.ListViewWrapper _rvmenu = null;
public anywheresoftware.b4a.objects.LabelWrapper _rftext = null;
public anywheresoftware.b4a.objects.ListViewWrapper _rightlv = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _fbox = null;
public de.amberhome.objects.appcompat.ACToolbarLightWrapper _otb = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel1 = null;
public de.amberhome.objects.appcompat.ACToolbarLightWrapper _btb = null;
public de.amberhome.objects.appcompat.ACSpinnerWrapper _acp = null;
public de.amberhome.materialdialogs.MaterialDialogWrapper _df = null;
public b4a.community.donmanfred.widget.bcCheckBox _acs = null;
public anywheresoftware.b4a.objects.collections.List _exalist = null;
public de.donmanfred.storage _storage = null;
public anywheresoftware.b4a.objects.collections.Map _paths = null;
public anywheresoftware.b4a.randomaccessfile.AsyncStreams _astream = null;
public com.apkbackup.de.swit _swit = null;
public com.apkbackup.de.animator _animator = null;
public com.apkbackup.de.savemodule _savemodule = null;
public com.apkbackup.de.starter _starter = null;
public com.apkbackup.de.fileobserve _fileobserve = null;

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
vis = vis | (swit.mostCurrent != null);
return vis;}
public static String  _ab1_click() throws Exception{
String _fname = "";
String _version = "";
anywheresoftware.b4a.objects.LabelWrapper _cont = null;
de.amberhome.materialdialogs.MaterialDialogBuilderWrapper _builder = null;
 //BA.debugLineNum = 818;BA.debugLine="Sub ab1_Click";
 //BA.debugLineNum = 819;BA.debugLine="Dim fname,version As String";
_fname = "";
_version = "";
 //BA.debugLineNum = 820;BA.debugLine="Dim cont As Label";
_cont = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 821;BA.debugLine="cont.Initialize(\"\")";
_cont.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 822;BA.debugLine="Bname.Enabled=True";
mostCurrent._bname.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 823;BA.debugLine="Bpath.Enabled=True";
mostCurrent._bpath.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 825;BA.debugLine="Bname.TextColor=mcl.md_light_blue_400";
mostCurrent._bname.setTextColor(mostCurrent._mcl.getmd_light_blue_400());
 //BA.debugLineNum = 826;BA.debugLine="Bpath.Gravity=Gravity.CENTER_HORIZONTAL";
mostCurrent._bpath.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL);
 //BA.debugLineNum = 827;BA.debugLine="Bname.Text=pak.GetApplicationLabel(finish.Get(0))";
mostCurrent._bname.setText(BA.ObjectToCharSequence(_pak.GetApplicationLabel(BA.ObjectToString(mostCurrent._finish.Get((int) (0))))));
 //BA.debugLineNum = 828;BA.debugLine="fname=pak.GetApplicationLabel(finish.Get(0))";
_fname = _pak.GetApplicationLabel(BA.ObjectToString(mostCurrent._finish.Get((int) (0))));
 //BA.debugLineNum = 829;BA.debugLine="version=pak.GetVersionName(finish.Get(0))";
_version = _pak.GetVersionName(BA.ObjectToString(mostCurrent._finish.Get((int) (0))));
 //BA.debugLineNum = 830;BA.debugLine="Bpath.Text=datas.GetSimple(\"0\")";
mostCurrent._bpath.setText(BA.ObjectToCharSequence(mostCurrent._datas._getsimple("0")));
 //BA.debugLineNum = 831;BA.debugLine="cont.Text=\"Backup \"&Bname.Text&\" ?\"";
_cont.setText(BA.ObjectToCharSequence("Backup "+mostCurrent._bname.getText()+" ?"));
 //BA.debugLineNum = 832;BA.debugLine="icon=pak.GetApplicationIcon(finish.Get(0))";
mostCurrent._icon.setObject((android.graphics.drawable.BitmapDrawable)(_pak.GetApplicationIcon(BA.ObjectToString(mostCurrent._finish.Get((int) (0))))));
 //BA.debugLineNum = 833;BA.debugLine="Dim Builder As MaterialDialogBuilder";
_builder = new de.amberhome.materialdialogs.MaterialDialogBuilderWrapper();
 //BA.debugLineNum = 834;BA.debugLine="Builder.Initialize(\"stadia\")";
_builder.Initialize(mostCurrent.activityBA,"stadia");
 //BA.debugLineNum = 835;BA.debugLine="Builder.Title(\"Backup App:\").TitleColor(mcl.md_bl";
_builder.Title(BA.ObjectToCharSequence("Backup App:")).TitleColor(mostCurrent._mcl.getmd_black_1000()).Icon((android.graphics.drawable.Drawable)(mostCurrent._icon.getObject())).LimitIconToDefaultSize().Theme(_builder.THEME_LIGHT).Content(BA.ObjectToCharSequence(_cont.getText())).ContentLineSpacing((float) (1)).ContentColor(mostCurrent._mcl.getmd_black_1000()).Cancelable(anywheresoftware.b4a.keywords.Common.False).AutoDismiss(anywheresoftware.b4a.keywords.Common.True).PositiveText(BA.ObjectToCharSequence("Start")).PositiveColor(mostCurrent._mcl.getmd_black_1000()).NeutralText(BA.ObjectToCharSequence("Cancel")).NeutralColor(anywheresoftware.b4a.keywords.Common.Colors.LightGray).ItemsColor(mostCurrent._mcl.getmd_blue_400()).ItemsGravity(_builder.GRAVITY_START).MaxIconSize(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (64))).ItemsCallbackSingleChoice((int) (3)).AlwaysCallSingleChoiceCallback();
 //BA.debugLineNum = 836;BA.debugLine="Builder.Items(Array As String (fname,\"Ver. \"&vers";
_builder.Items((java.lang.CharSequence[])(new String[]{_fname,"Ver. "+_version,"Folder:"+mostCurrent._bpath.getText()}));
 //BA.debugLineNum = 837;BA.debugLine="Builder.ItemsCallback";
_builder.ItemsCallback();
 //BA.debugLineNum = 838;BA.debugLine="deldia=Builder.Show";
mostCurrent._deldia = _builder.Show();
 //BA.debugLineNum = 839;BA.debugLine="deldia.Show";
mostCurrent._deldia.Show();
 //BA.debugLineNum = 840;BA.debugLine="End Sub";
return "";
}
public static String  _acp_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 411;BA.debugLine="Sub acp_ItemClick (Position As Int, Value As Objec";
 //BA.debugLineNum = 412;BA.debugLine="Select Position";
switch (_position) {
case 0: {
 //BA.debugLineNum = 414;BA.debugLine="sm.HideMenus";
mostCurrent._sm.HideMenus();
 //BA.debugLineNum = 415;BA.debugLine="path_explorer";
_path_explorer();
 break; }
case 1: {
 //BA.debugLineNum = 417;BA.debugLine="sm.HideMenus";
mostCurrent._sm.HideMenus();
 //BA.debugLineNum = 418;BA.debugLine="ToastMessageShow(\"Select case 1\",False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Select case 1"),anywheresoftware.b4a.keywords.Common.False);
 break; }
}
;
 //BA.debugLineNum = 420;BA.debugLine="End Sub";
return "";
}
public static String  _acs_oncheckedchanged(Object _tag,boolean _checked) throws Exception{
 //BA.debugLineNum = 421;BA.debugLine="Sub acs_onCheckedChanged(tag As Object, checked As";
 //BA.debugLineNum = 422;BA.debugLine="Select checked";
switch (BA.switchObjectToInt(_checked,_checked==anywheresoftware.b4a.keywords.Common.True,_checked==anywheresoftware.b4a.keywords.Common.False)) {
case 0: {
 //BA.debugLineNum = 424;BA.debugLine="ToastMessageShow(\"Service Options coming on nex";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Service Options coming on next Update"),anywheresoftware.b4a.keywords.Common.False);
 break; }
case 1: {
 //BA.debugLineNum = 426;BA.debugLine="ToastMessageShow(\"coming soon...\",False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("coming soon..."),anywheresoftware.b4a.keywords.Common.False);
 break; }
}
;
 //BA.debugLineNum = 429;BA.debugLine="End Sub";
return "";
}
public static String  _activity_create(boolean _firsttime) throws Exception{
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _fim = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _him = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _eim = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _climg = null;
int _offset = 0;
anywheresoftware.b4a.objects.PanelWrapper _lftmenu = null;
anywheresoftware.b4a.objects.PanelWrapper _rhtmenu = null;
anywheresoftware.b4a.objects.LabelWrapper _lvm1 = null;
anywheresoftware.b4a.objects.LabelWrapper _lvm2 = null;
anywheresoftware.b4a.objects.LabelWrapper _lvm3 = null;
anywheresoftware.b4a.objects.LabelWrapper _lvm4 = null;
anywheresoftware.b4a.objects.LabelWrapper _l3 = null;
anywheresoftware.b4a.objects.LabelWrapper _l4 = null;
int _i = 0;
String _de = "";
anywheresoftware.b4a.keywords.Regex.MatcherWrapper _mtc = null;
String _extsdcard = "";
String _mnt = "";
anywheresoftware.b4a.objects.collections.List _dirs = null;
String _f = "";
 //BA.debugLineNum = 95;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 96;BA.debugLine="Activity.LoadLayout(\"2\")";
mostCurrent._activity.LoadLayout("2",mostCurrent.activityBA);
 //BA.debugLineNum = 97;BA.debugLine="dir= File.DirRootExternal&\"/APEX/backups\"";
mostCurrent._dir = anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/APEX/backups";
 //BA.debugLineNum = 98;BA.debugLine="superfont=Typeface.LoadFromAssets(\"OpenSans.ttf\")";
mostCurrent._superfont.setObject((android.graphics.Typeface)(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("OpenSans.ttf")));
 //BA.debugLineNum = 99;BA.debugLine="Dim fim,him,eim As Bitmap";
_fim = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
_him = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
_eim = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 100;BA.debugLine="Dim climg As BitmapDrawable";
_climg = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 101;BA.debugLine="fim.Initialize(File.DirAssets,\"ic_clear_white_36d";
_fim.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"ic_clear_white_36dp.png");
 //BA.debugLineNum = 102;BA.debugLine="climg.Initialize(LoadBitmap(File.DirAssets,\"ic_ar";
_climg.Initialize((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"ic_archive_white_36dp.png").getObject()));
 //BA.debugLineNum = 103;BA.debugLine="eim.Initialize(File.DirAssets,\"ic_label_white_36d";
_eim.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"ic_label_white_36dp.png");
 //BA.debugLineNum = 104;BA.debugLine="him.Initialize(File.DirAssets,\"ic_info_outline_wh";
_him.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"ic_info_outline_white_36dp.png");
 //BA.debugLineNum = 105;BA.debugLine="Activity.Color=mcl.md_white_1000";
mostCurrent._activity.setColor(mostCurrent._mcl.getmd_white_1000());
 //BA.debugLineNum = 106;BA.debugLine="time=DateTime.Time(DateTime.Now)";
_time = anywheresoftware.b4a.keywords.Common.DateTime.Time(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 107;BA.debugLine="DateTime.TimeFormat=\"HH:mm\"";
anywheresoftware.b4a.keywords.Common.DateTime.setTimeFormat("HH:mm");
 //BA.debugLineNum = 108;BA.debugLine="DateTime.DateFormat=\"dd/MM/yyyy\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("dd/MM/yyyy");
 //BA.debugLineNum = 109;BA.debugLine="date=DateTime.Date(DateTime.Now)";
_date = anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 110;BA.debugLine="sm.Initialize(\"sm\")";
mostCurrent._sm.Initialize(mostCurrent.activityBA,"sm");
 //BA.debugLineNum = 111;BA.debugLine="datas.Initialize(File.DirInternal,\"datastore_1\")";
mostCurrent._datas._initialize(processBA,anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"datastore_1");
 //BA.debugLineNum = 112;BA.debugLine="dtime.Initialize(File.DirInternal,\"datastore_2\")";
mostCurrent._dtime._initialize(processBA,anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"datastore_2");
 //BA.debugLineNum = 113;BA.debugLine="bcount.Initialize(File.DirInternal,\"datastore_3\")";
mostCurrent._bcount._initialize(processBA,anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"datastore_3");
 //BA.debugLineNum = 114;BA.debugLine="cop.Initialize(File.DirInternal,\"datastore_cop\")";
mostCurrent._cop._initialize(processBA,anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"datastore_cop");
 //BA.debugLineNum = 115;BA.debugLine="dia3.Initialize(\"dia3\")";
mostCurrent._dia3.Initialize(mostCurrent.activityBA,"dia3");
 //BA.debugLineNum = 116;BA.debugLine="iv1.Initialize(\"iv1\")";
mostCurrent._iv1.Initialize(mostCurrent.activityBA,"iv1");
 //BA.debugLineNum = 117;BA.debugLine="iv2.Initialize(\"iv2\")";
mostCurrent._iv2.Initialize(mostCurrent.activityBA,"iv2");
 //BA.debugLineNum = 118;BA.debugLine="exalist.Initialize";
mostCurrent._exalist.Initialize();
 //BA.debugLineNum = 119;BA.debugLine="iv1.Gravity=Gravity.FILL";
mostCurrent._iv1.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 120;BA.debugLine="iv2.Gravity=Gravity.FILL";
mostCurrent._iv2.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 121;BA.debugLine="datal.Initialize";
mostCurrent._datal.Initialize();
 //BA.debugLineNum = 122;BA.debugLine="Dim offset As Int = 22%x";
_offset = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (22),mostCurrent.activityBA);
 //BA.debugLineNum = 123;BA.debugLine="sm.BehindOffset = offset";
mostCurrent._sm.setBehindOffset(_offset);
 //BA.debugLineNum = 125;BA.debugLine="sm.Mode = sm.BOTH";
mostCurrent._sm.setMode(mostCurrent._sm.BOTH);
 //BA.debugLineNum = 127;BA.debugLine="Dim lftMenu As Panel";
_lftmenu = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 128;BA.debugLine="Dim rhtMenu As Panel";
_rhtmenu = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 129;BA.debugLine="lftMenu.Initialize(\"\")";
_lftmenu.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 130;BA.debugLine="rhtMenu.Initialize(\"\")";
_rhtmenu.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 131;BA.debugLine="sm.Menu.AddView(lftMenu, 0, 0, 100%x-offset, 100%";
mostCurrent._sm.getMenu().AddView((android.view.View)(_lftmenu.getObject()),(int) (0),(int) (0),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-_offset),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 132;BA.debugLine="sm.SecondaryMenu.AddView(rhtMenu, 0, 0,100%x-offs";
mostCurrent._sm.getSecondaryMenu().AddView((android.view.View)(_rhtmenu.getObject()),(int) (0),(int) (0),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-_offset),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 133;BA.debugLine="lftMenu.LoadLayout(\"Left\")";
_lftmenu.LoadLayout("Left",mostCurrent.activityBA);
 //BA.debugLineNum = 134;BA.debugLine="rhtMenu.LoadLayout(\"right\")";
_rhtmenu.LoadLayout("right",mostCurrent.activityBA);
 //BA.debugLineNum = 135;BA.debugLine="Pid.Initialize";
mostCurrent._pid.Initialize();
 //BA.debugLineNum = 136;BA.debugLine="ref.Initialize(\"ref\")";
mostCurrent._ref.Initialize(mostCurrent.activityBA,"ref");
 //BA.debugLineNum = 137;BA.debugLine="ABHelper.Initialize";
mostCurrent._abhelper.Initialize(mostCurrent.activityBA);
 //BA.debugLineNum = 138;BA.debugLine="toolbar.SetAsActionBar";
mostCurrent._toolbar.SetAsActionBar(mostCurrent.activityBA);
 //BA.debugLineNum = 139;BA.debugLine="toolbar.InitMenuListener";
mostCurrent._toolbar.InitMenuListener();
 //BA.debugLineNum = 140;BA.debugLine="toolbar.PopupTheme=toolbar.THEME_LIGHT";
mostCurrent._toolbar.setPopupTheme(mostCurrent._toolbar.THEME_LIGHT);
 //BA.debugLineNum = 142;BA.debugLine="toolbar.SubTitle=\"V\"&pak.GetVersionName(\"com.apkb";
mostCurrent._toolbar.setSubTitle(BA.ObjectToCharSequence("V"+_pak.GetVersionName("com.apkbackup.de")));
 //BA.debugLineNum = 143;BA.debugLine="ABHelper.ShowUpIndicator = True";
mostCurrent._abhelper.setShowUpIndicator(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 144;BA.debugLine="ABHelper.HomeVisible=True";
mostCurrent._abhelper.setHomeVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 145;BA.debugLine="Dim lvm1,lvm2,lvm3,lvm4 As Label";
_lvm1 = new anywheresoftware.b4a.objects.LabelWrapper();
_lvm2 = new anywheresoftware.b4a.objects.LabelWrapper();
_lvm3 = new anywheresoftware.b4a.objects.LabelWrapper();
_lvm4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 146;BA.debugLine="lvm1.Initialize(\"\")";
_lvm1.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 147;BA.debugLine="lvm2.Initialize(\"\")";
_lvm2.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 148;BA.debugLine="lvm3.Initialize(\"\")";
_lvm3.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 149;BA.debugLine="lvm4.Initialize(\"\")";
_lvm4.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 150;BA.debugLine="lvm1=lvMenu.TwoLinesAndBitmap.Label";
_lvm1 = mostCurrent._lvmenu.getTwoLinesAndBitmap().Label;
 //BA.debugLineNum = 151;BA.debugLine="lvm2=lvMenu.TwoLinesAndBitmap.SecondLabel";
_lvm2 = mostCurrent._lvmenu.getTwoLinesAndBitmap().SecondLabel;
 //BA.debugLineNum = 152;BA.debugLine="lvm3=rvmenu.TwoLinesAndBitmap.Label";
_lvm3 = mostCurrent._rvmenu.getTwoLinesAndBitmap().Label;
 //BA.debugLineNum = 153;BA.debugLine="lvm4=rvmenu.TwoLinesAndBitmap.SecondLabel";
_lvm4 = mostCurrent._rvmenu.getTwoLinesAndBitmap().SecondLabel;
 //BA.debugLineNum = 154;BA.debugLine="lvm1.TextColor = Colors.White";
_lvm1.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 155;BA.debugLine="lvm3.TextColor = Colors.White";
_lvm3.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 156;BA.debugLine="lvm1.textsize=15";
_lvm1.setTextSize((float) (15));
 //BA.debugLineNum = 157;BA.debugLine="lvm3.textsize=15";
_lvm3.setTextSize((float) (15));
 //BA.debugLineNum = 158;BA.debugLine="lvm2.textsize=12";
_lvm2.setTextSize((float) (12));
 //BA.debugLineNum = 159;BA.debugLine="lvm4.textsize=12";
_lvm4.setTextSize((float) (12));
 //BA.debugLineNum = 160;BA.debugLine="lvm2.textcolor=Colors.ARGB(200,255,255,255)";
_lvm2.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (200),(int) (255),(int) (255),(int) (255)));
 //BA.debugLineNum = 161;BA.debugLine="lvm4.textcolor=Colors.ARGB(200,255,255,255)";
_lvm4.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (200),(int) (255),(int) (255),(int) (255)));
 //BA.debugLineNum = 162;BA.debugLine="lvMenu.color=Colors.ARGB(255,25,118,210)";
mostCurrent._lvmenu.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (25),(int) (118),(int) (210)));
 //BA.debugLineNum = 163;BA.debugLine="lvMenu.TwoLinesAndBitmap.ImageView.Height=38dip";
mostCurrent._lvmenu.getTwoLinesAndBitmap().ImageView.setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (38)));
 //BA.debugLineNum = 164;BA.debugLine="lvMenu.TwoLinesAndBitmap.ImageView.Width=38dip";
mostCurrent._lvmenu.getTwoLinesAndBitmap().ImageView.setWidth(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (38)));
 //BA.debugLineNum = 165;BA.debugLine="lvMenu.TwoLinesAndBitmap.ImageView.Gravity=Gravit";
mostCurrent._lvmenu.getTwoLinesAndBitmap().ImageView.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 166;BA.debugLine="lvMenu.TwoLinesAndBitmap.ItemHeight=12%y";
mostCurrent._lvmenu.getTwoLinesAndBitmap().setItemHeight(anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (12),mostCurrent.activityBA));
 //BA.debugLineNum = 167;BA.debugLine="lvMenu.TwoLinesAndBitmap.SecondLabel.Height=12%y";
mostCurrent._lvmenu.getTwoLinesAndBitmap().SecondLabel.setHeight(anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (12),mostCurrent.activityBA));
 //BA.debugLineNum = 168;BA.debugLine="lvMenu.TwoLinesAndBitmap.SecondLabel.Width=90%x";
mostCurrent._lvmenu.getTwoLinesAndBitmap().SecondLabel.setWidth(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA));
 //BA.debugLineNum = 169;BA.debugLine="lvm1.Typeface=superfont";
_lvm1.setTypeface((android.graphics.Typeface)(mostCurrent._superfont.getObject()));
 //BA.debugLineNum = 170;BA.debugLine="lvm2.Typeface=superfont";
_lvm2.setTypeface((android.graphics.Typeface)(mostCurrent._superfont.getObject()));
 //BA.debugLineNum = 171;BA.debugLine="lvm3.Typeface=superfont";
_lvm3.setTypeface((android.graphics.Typeface)(mostCurrent._superfont.getObject()));
 //BA.debugLineNum = 172;BA.debugLine="lvm4.Typeface=superfont";
_lvm4.setTypeface((android.graphics.Typeface)(mostCurrent._superfont.getObject()));
 //BA.debugLineNum = 173;BA.debugLine="rvmenu.color=Colors.ARGB(255,25,118,210)";
mostCurrent._rvmenu.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (25),(int) (118),(int) (210)));
 //BA.debugLineNum = 174;BA.debugLine="rvmenu.TwoLinesAndBitmap.ImageView.Height=38dip";
mostCurrent._rvmenu.getTwoLinesAndBitmap().ImageView.setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (38)));
 //BA.debugLineNum = 175;BA.debugLine="rvmenu.TwoLinesAndBitmap.ImageView.Width=38dip";
mostCurrent._rvmenu.getTwoLinesAndBitmap().ImageView.setWidth(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (38)));
 //BA.debugLineNum = 176;BA.debugLine="rvmenu.TwoLinesAndBitmap.ImageView.Gravity=Gravit";
mostCurrent._rvmenu.getTwoLinesAndBitmap().ImageView.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL);
 //BA.debugLineNum = 177;BA.debugLine="rvmenu.TwoLinesAndBitmap.ItemHeight=12%y";
mostCurrent._rvmenu.getTwoLinesAndBitmap().setItemHeight(anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (12),mostCurrent.activityBA));
 //BA.debugLineNum = 178;BA.debugLine="rvmenu.TwoLinesAndBitmap.SecondLabel.Height=12%y";
mostCurrent._rvmenu.getTwoLinesAndBitmap().SecondLabel.setHeight(anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (12),mostCurrent.activityBA));
 //BA.debugLineNum = 179;BA.debugLine="rvmenu.TwoLinesAndBitmap.SecondLabel.Width=90%x";
mostCurrent._rvmenu.getTwoLinesAndBitmap().SecondLabel.setWidth(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA));
 //BA.debugLineNum = 180;BA.debugLine="rightlv.Initialize(\"rightlv\")";
mostCurrent._rightlv.Initialize(mostCurrent.activityBA,"rightlv");
 //BA.debugLineNum = 184;BA.debugLine="menulist.Initialize";
mostCurrent._menulist.Initialize();
 //BA.debugLineNum = 185;BA.debugLine="mb.Initialize(\"mb\")";
mostCurrent._mb.Initialize(mostCurrent.activityBA,"mb");
 //BA.debugLineNum = 186;BA.debugLine="mb.Text=\"Close\"";
mostCurrent._mb.setText(BA.ObjectToCharSequence("Close"));
 //BA.debugLineNum = 188;BA.debugLine="menulist.Add(\"About\")";
mostCurrent._menulist.Add((Object)("About"));
 //BA.debugLineNum = 189;BA.debugLine="lst.Initialize(Me, svList, \"\", \"lst_Click\", \"\", 1";
mostCurrent._lst._initialize(mostCurrent.activityBA,main.getObject(),mostCurrent._svlist,"","lst_Click","",anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1)));
 //BA.debugLineNum = 190;BA.debugLine="lst.ExtensionColor = Colors.ARGB(25,162,185,219)";
mostCurrent._lst._extensioncolor = anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (25),(int) (162),(int) (185),(int) (219));
 //BA.debugLineNum = 191;BA.debugLine="lst.SetOnPaintListener(\"spl\")";
mostCurrent._lst._setonpaintlistener("spl");
 //BA.debugLineNum = 192;BA.debugLine="lst.DividerColor=Colors.ARGB(30,0,0,0)";
mostCurrent._lst._dividercolor = anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (30),(int) (0),(int) (0),(int) (0));
 //BA.debugLineNum = 194;BA.debugLine="chk.Initialize(\"Chk\")";
mostCurrent._chk.Initialize(mostCurrent.activityBA,"Chk");
 //BA.debugLineNum = 195;BA.debugLine="chk.TextSize=13";
mostCurrent._chk.setTextSize((float) (13));
 //BA.debugLineNum = 196;BA.debugLine="chk.Color=mcl.md_light_blue_400";
mostCurrent._chk.setColor(mostCurrent._mcl.getmd_light_blue_400());
 //BA.debugLineNum = 197;BA.debugLine="chk.TextColor=mcl.md_white_1000";
mostCurrent._chk.setTextColor(mostCurrent._mcl.getmd_white_1000());
 //BA.debugLineNum = 198;BA.debugLine="chk.Typeface=superfont";
mostCurrent._chk.setTypeface((android.graphics.Typeface)(mostCurrent._superfont.getObject()));
 //BA.debugLineNum = 199;BA.debugLine="chk.Text=\"close\"";
mostCurrent._chk.setText(BA.ObjectToCharSequence("close"));
 //BA.debugLineNum = 200;BA.debugLine="chk.Gravity=Gravity.FILL";
mostCurrent._chk.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 202;BA.debugLine="extra.Initialize";
mostCurrent._extra.Initialize();
 //BA.debugLineNum = 203;BA.debugLine="extra2.Initialize";
mostCurrent._extra2.Initialize();
 //BA.debugLineNum = 204;BA.debugLine="finish.Initialize";
mostCurrent._finish.Initialize();
 //BA.debugLineNum = 205;BA.debugLine="ie.Initialize";
mostCurrent._ie._initialize(processBA);
 //BA.debugLineNum = 206;BA.debugLine="Bbpanel.Initialize(\"Bbpanel\")";
mostCurrent._bbpanel.Initialize(mostCurrent.activityBA,"Bbpanel");
 //BA.debugLineNum = 207;BA.debugLine="Bname.Initialize(\"bname\")";
mostCurrent._bname.Initialize(mostCurrent.activityBA,"bname");
 //BA.debugLineNum = 208;BA.debugLine="Bpath.Initialize(\"Bpath\")";
mostCurrent._bpath.Initialize(mostCurrent.activityBA,"Bpath");
 //BA.debugLineNum = 209;BA.debugLine="Bcontent.Initialize(\"bcontent\")";
mostCurrent._bcontent.Initialize(mostCurrent.activityBA,"bcontent");
 //BA.debugLineNum = 210;BA.debugLine="Label4.Initialize(\"label4\")";
mostCurrent._label4.Initialize(mostCurrent.activityBA,"label4");
 //BA.debugLineNum = 211;BA.debugLine="Label5.Initialize(\"label5\")";
mostCurrent._label5.Initialize(mostCurrent.activityBA,"label5");
 //BA.debugLineNum = 212;BA.debugLine="dataliste.Initialize(\"dataliste\")";
mostCurrent._dataliste.Initialize(mostCurrent.activityBA,"dataliste");
 //BA.debugLineNum = 213;BA.debugLine="dataliste.Enabled=True";
mostCurrent._dataliste.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 215;BA.debugLine="Dim l3,l4 As Label";
_l3 = new anywheresoftware.b4a.objects.LabelWrapper();
_l4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 216;BA.debugLine="l3.Initialize(\"\")";
_l3.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 217;BA.debugLine="l4.Initialize(\"\")";
_l4.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 218;BA.debugLine="l3=dataliste.TwoLinesAndBitmap.Label";
_l3 = mostCurrent._dataliste.getTwoLinesAndBitmap().Label;
 //BA.debugLineNum = 219;BA.debugLine="l4=dataliste.TwoLinesAndBitmap.SecondLabel";
_l4 = mostCurrent._dataliste.getTwoLinesAndBitmap().SecondLabel;
 //BA.debugLineNum = 220;BA.debugLine="l3.TextSize=15";
_l3.setTextSize((float) (15));
 //BA.debugLineNum = 221;BA.debugLine="l3.TextColor=mcl.md_white_1000";
_l3.setTextColor(mostCurrent._mcl.getmd_white_1000());
 //BA.debugLineNum = 222;BA.debugLine="l4.TextSize=14";
_l4.setTextSize((float) (14));
 //BA.debugLineNum = 223;BA.debugLine="l4.TextColor=mcl.md_light_blue_400";
_l4.setTextColor(mostCurrent._mcl.getmd_light_blue_400());
 //BA.debugLineNum = 224;BA.debugLine="dataliste.TwoLinesAndBitmap.ImageView.Height=38di";
mostCurrent._dataliste.getTwoLinesAndBitmap().ImageView.setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (38)));
 //BA.debugLineNum = 225;BA.debugLine="dataliste.TwoLinesAndBitmap.ImageView.Width=38dip";
mostCurrent._dataliste.getTwoLinesAndBitmap().ImageView.setWidth(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (38)));
 //BA.debugLineNum = 226;BA.debugLine="dataliste.TwoLinesAndBitmap.ItemHeight=25%y";
mostCurrent._dataliste.getTwoLinesAndBitmap().setItemHeight(anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (25),mostCurrent.activityBA));
 //BA.debugLineNum = 228;BA.debugLine="ex.Initialize(\"ex\",1000)";
_ex.Initialize(processBA,"ex",(long) (1000));
 //BA.debugLineNum = 229;BA.debugLine="exa.Initialize(\"exa\",1000)";
_exa.Initialize(processBA,"exa",(long) (1000));
 //BA.debugLineNum = 230;BA.debugLine="dex.Initialize(\"dex\",1000)";
_dex.Initialize(processBA,"dex",(long) (1000));
 //BA.debugLineNum = 231;BA.debugLine="dex.Enabled=False";
_dex.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 232;BA.debugLine="exa.Enabled=False";
_exa.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 233;BA.debugLine="ex.Interval=40";
_ex.setInterval((long) (40));
 //BA.debugLineNum = 234;BA.debugLine="dex.Interval=40";
_dex.setInterval((long) (40));
 //BA.debugLineNum = 235;BA.debugLine="exa.Interval=1";
_exa.setInterval((long) (1));
 //BA.debugLineNum = 236;BA.debugLine="tcount=0";
_tcount = (int) (0);
 //BA.debugLineNum = 237;BA.debugLine="ex.Enabled=False";
_ex.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 238;BA.debugLine="pn.Initialize";
mostCurrent._pn.Initialize();
 //BA.debugLineNum = 240;BA.debugLine="If FirstTime Then";
if (_firsttime) { 
 //BA.debugLineNum = 241;BA.debugLine="File.MakeDir(File.DirRootExternal,\"/APEX/backups";
anywheresoftware.b4a.keywords.Common.File.MakeDir(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"/APEX/backups");
 //BA.debugLineNum = 242;BA.debugLine="If Not (datas.ContainsKey(\"0\")) Then";
if (anywheresoftware.b4a.keywords.Common.Not(mostCurrent._datas._containskey("0"))) { 
 //BA.debugLineNum = 243;BA.debugLine="datas.PutSimple(\"0\",dir)";
mostCurrent._datas._putsimple("0",(Object)(mostCurrent._dir));
 };
 };
 //BA.debugLineNum = 246;BA.debugLine="paths = storage.Initialize";
mostCurrent._paths = mostCurrent._storage.Initialize();
 //BA.debugLineNum = 247;BA.debugLine="For i = 0 To paths.Size-1";
{
final int step140 = 1;
final int limit140 = (int) (mostCurrent._paths.getSize()-1);
_i = (int) (0) ;
for (;(step140 > 0 && _i <= limit140) || (step140 < 0 && _i >= limit140) ;_i = ((int)(0 + _i + step140))  ) {
 //BA.debugLineNum = 248;BA.debugLine="Log(paths.GetKeyAt(i)&\"=\"&paths.GetValueAt(i))";
anywheresoftware.b4a.keywords.Common.Log(BA.ObjectToString(mostCurrent._paths.GetKeyAt(_i))+"="+BA.ObjectToString(mostCurrent._paths.GetValueAt(_i)));
 }
};
 //BA.debugLineNum = 253;BA.debugLine="Dim de As String = File.DirRootExternal";
_de = anywheresoftware.b4a.keywords.Common.File.getDirRootExternal();
 //BA.debugLineNum = 254;BA.debugLine="Log (\"DirRootExternal = \"&de)";
anywheresoftware.b4a.keywords.Common.Log("DirRootExternal = "+_de);
 //BA.debugLineNum = 255;BA.debugLine="Dim mtc As Matcher = Regex.Matcher(\"(/|\\\\)[^(/|\\\\";
_mtc = new anywheresoftware.b4a.keywords.Regex.MatcherWrapper();
_mtc = anywheresoftware.b4a.keywords.Common.Regex.Matcher("(/|\\\\)[^(/|\\\\)]*(/|\\\\)",_de);
 //BA.debugLineNum = 256;BA.debugLine="Dim extsdcard As String = de";
_extsdcard = _de;
 //BA.debugLineNum = 257;BA.debugLine="If mtc.Find = True Then";
if (_mtc.Find()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 258;BA.debugLine="Dim mnt As String = mtc.Group(0)";
_mnt = _mtc.Group((int) (0));
 //BA.debugLineNum = 260;BA.debugLine="Log (\"mount point = \"& mnt)";
anywheresoftware.b4a.keywords.Common.Log("mount point = "+_mnt);
 //BA.debugLineNum = 261;BA.debugLine="Dim dirs As List = File.ListFiles(mnt)";
_dirs = new anywheresoftware.b4a.objects.collections.List();
_dirs = anywheresoftware.b4a.keywords.Common.File.ListFiles(_mnt);
 //BA.debugLineNum = 262;BA.debugLine="For Each f As String In dirs";
{
final anywheresoftware.b4a.BA.IterableList group151 = _dirs;
final int groupLen151 = group151.getSize()
;int index151 = 0;
;
for (; index151 < groupLen151;index151++){
_f = BA.ObjectToString(group151.Get(index151));
 //BA.debugLineNum = 263;BA.debugLine="If storage.isExternalStorageRemovable(mnt&f) Th";
if (mostCurrent._storage.isExternalStorageRemovable(_mnt+_f)) { 
 //BA.debugLineNum = 264;BA.debugLine="Log (\"Device = \"& f&\":\"&mnt&f&\" is removable\")";
anywheresoftware.b4a.keywords.Common.Log("Device = "+_f+":"+_mnt+_f+" is removable");
 //BA.debugLineNum = 265;BA.debugLine="If File.ListFiles(mnt&f).IsInitialized Then";
if (anywheresoftware.b4a.keywords.Common.File.ListFiles(_mnt+_f).IsInitialized()) { 
 //BA.debugLineNum = 266;BA.debugLine="Log(\"probably ExtSDCard: \"&mnt&f)";
anywheresoftware.b4a.keywords.Common.Log("probably ExtSDCard: "+_mnt+_f);
 //BA.debugLineNum = 267;BA.debugLine="extsdcard = mnt&f";
_extsdcard = _mnt+_f;
 //BA.debugLineNum = 268;BA.debugLine="datas.DeleteAll";
mostCurrent._datas._deleteall();
 //BA.debugLineNum = 269;BA.debugLine="datas.PutSimple(\"0\",mnt&f&\"/APEX/backups\")";
mostCurrent._datas._putsimple("0",(Object)(_mnt+_f+"/APEX/backups"));
 }else {
 };
 }else {
 //BA.debugLineNum = 274;BA.debugLine="Log (\"Device = \"& f&\":\"&mnt&f&\" is NOT removab";
anywheresoftware.b4a.keywords.Common.Log("Device = "+_f+":"+_mnt+_f+" is NOT removable");
 };
 }
};
 };
 //BA.debugLineNum = 278;BA.debugLine="Log(\"extsdcard=\"&extsdcard)";
anywheresoftware.b4a.keywords.Common.Log("extsdcard="+_extsdcard);
 //BA.debugLineNum = 281;BA.debugLine="file_check";
_file_check();
 //BA.debugLineNum = 282;BA.debugLine="apps_start";
_apps_start();
 //BA.debugLineNum = 284;BA.debugLine="End Sub";
return "";
}
public static String  _activity_createmenu(de.amberhome.objects.appcompat.ACMenuWrapper _menu) throws Exception{
anywheresoftware.b4a.objects.drawable.BitmapDrawable _eim = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _fim1 = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _climg1 = null;
de.amberhome.objects.appcompat.ACMenuItemWrapper _item = null;
de.amberhome.objects.appcompat.ACMenuItemWrapper _item2 = null;
de.amberhome.objects.appcompat.ACMenuItemWrapper _item3 = null;
 //BA.debugLineNum = 304;BA.debugLine="Sub Activity_CreateMenu(menu As ACMenu)";
 //BA.debugLineNum = 305;BA.debugLine="Dim eim As BitmapDrawable";
_eim = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 306;BA.debugLine="eim.Initialize(LoadBitmap(File.DirAssets,\"ic_exit";
_eim.Initialize((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"ic_exit_to_app_white_48dp.png").getObject()));
 //BA.debugLineNum = 307;BA.debugLine="Dim fim1,climg1 As BitmapDrawable";
_fim1 = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
_climg1 = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 308;BA.debugLine="fim1.Initialize(LoadBitmap(File.DirAssets,\"ic_set";
_fim1.Initialize((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"ic_settings_applications_white_36dp.png").getObject()));
 //BA.debugLineNum = 309;BA.debugLine="climg1.Initialize(LoadBitmap(File.DirAssets,\"ic_a";
_climg1.Initialize((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"ic_apps_white_36dp.png").getObject()));
 //BA.debugLineNum = 310;BA.debugLine="menu.Clear";
_menu.Clear();
 //BA.debugLineNum = 311;BA.debugLine="Dim item,item2,item3 As ACMenuItem";
_item = new de.amberhome.objects.appcompat.ACMenuItemWrapper();
_item2 = new de.amberhome.objects.appcompat.ACMenuItemWrapper();
_item3 = new de.amberhome.objects.appcompat.ACMenuItemWrapper();
 //BA.debugLineNum = 312;BA.debugLine="item3=toolbar.Menu.Add2(0, 0, \"Menu\", climg1)";
_item3 = mostCurrent._toolbar.getMenu().Add2((int) (0),(int) (0),BA.ObjectToCharSequence("Menu"),(android.graphics.drawable.Drawable)(_climg1.getObject()));
 //BA.debugLineNum = 313;BA.debugLine="item=toolbar.Menu.Add2(1, 1, \"SMenu\", fim1)";
_item = mostCurrent._toolbar.getMenu().Add2((int) (1),(int) (1),BA.ObjectToCharSequence("SMenu"),(android.graphics.drawable.Drawable)(_fim1.getObject()));
 //BA.debugLineNum = 314;BA.debugLine="item2=toolbar.Menu.Add2(2, 2, \"Exit\", eim)";
_item2 = mostCurrent._toolbar.getMenu().Add2((int) (2),(int) (2),BA.ObjectToCharSequence("Exit"),(android.graphics.drawable.Drawable)(_eim.getObject()));
 //BA.debugLineNum = 315;BA.debugLine="item.ShowAsAction = item.SHOW_AS_ACTION_ALWAYS";
_item.setShowAsAction(_item.SHOW_AS_ACTION_ALWAYS);
 //BA.debugLineNum = 316;BA.debugLine="item2.ShowAsAction = item2.SHOW_AS_ACTION_ALWAYS";
_item2.setShowAsAction(_item2.SHOW_AS_ACTION_ALWAYS);
 //BA.debugLineNum = 317;BA.debugLine="item3.ShowAsAction = item3.SHOW_AS_ACTION_ALWAYS";
_item3.setShowAsAction(_item3.SHOW_AS_ACTION_ALWAYS);
 //BA.debugLineNum = 318;BA.debugLine="menu_two";
_menu_two();
 //BA.debugLineNum = 319;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 296;BA.debugLine="Sub Activity_KeyPress(KeyCode As Int)As Boolean";
 //BA.debugLineNum = 297;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 298;BA.debugLine="close_click";
_close_click();
 };
 //BA.debugLineNum = 301;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 302;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 290;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 292;BA.debugLine="exa.Enabled = False";
_exa.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 293;BA.debugLine="ex.Enabled=False";
_ex.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 295;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 286;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 287;BA.debugLine="file_check";
_file_check();
 //BA.debugLineNum = 288;BA.debugLine="End Sub";
return "";
}
public static String  _app_share() throws Exception{
anywheresoftware.b4a.objects.IntentWrapper _i = null;
 //BA.debugLineNum = 714;BA.debugLine="Sub app_share";
 //BA.debugLineNum = 715;BA.debugLine="Dim i As Intent";
_i = new anywheresoftware.b4a.objects.IntentWrapper();
 //BA.debugLineNum = 716;BA.debugLine="i.Initialize(i.ACTION_SEND, \"\")";
_i.Initialize(_i.ACTION_SEND,"");
 //BA.debugLineNum = 717;BA.debugLine="i.SetType(\"text/plain\")";
_i.SetType("text/plain");
 //BA.debugLineNum = 718;BA.debugLine="i.PutExtra(\"android.intent.extra.TEXT\", \"https://";
_i.PutExtra("android.intent.extra.TEXT",(Object)("https://www.amazon.com/dp/B0754TM922/ref=sr_1_1?ie=UTF8&qid=1504040405&sr=8-1&keywords=APK+Backup%28Pro%29"));
 //BA.debugLineNum = 719;BA.debugLine="i.PutExtra(\"android.intent.extra.SUBJECT\", \"APK B";
_i.PutExtra("android.intent.extra.SUBJECT",(Object)("APK Backup (Pro) is a cost-free solution for app management on your Android smartphone. Get it on Amazone"));
 //BA.debugLineNum = 720;BA.debugLine="StartActivity(i)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(_i.getObject()));
 //BA.debugLineNum = 721;BA.debugLine="End Sub";
return "";
}
public static String  _apps_start() throws Exception{
int _i = 0;
 //BA.debugLineNum = 1240;BA.debugLine="Sub apps_start";
 //BA.debugLineNum = 1241;BA.debugLine="extra.Clear";
mostCurrent._extra.Clear();
 //BA.debugLineNum = 1242;BA.debugLine="applist=pak.GetInstalledPackages";
mostCurrent._applist = _pak.GetInstalledPackages();
 //BA.debugLineNum = 1243;BA.debugLine="Obj1.Target = Obj1.GetContext";
mostCurrent._obj1.Target = (Object)(mostCurrent._obj1.GetContext(processBA));
 //BA.debugLineNum = 1244;BA.debugLine="Obj1.Target = Obj1.RunMethod(\"getPackageManager\")";
mostCurrent._obj1.Target = mostCurrent._obj1.RunMethod("getPackageManager");
 //BA.debugLineNum = 1245;BA.debugLine="Obj2.Target = Obj1.RunMethod2(\"getInstalledPackag";
mostCurrent._obj2.Target = mostCurrent._obj1.RunMethod2("getInstalledPackages",BA.NumberToString(0),"java.lang.int");
 //BA.debugLineNum = 1246;BA.debugLine="size = Obj2.RunMethod(\"size\")";
_size = (int)(BA.ObjectToNumber(mostCurrent._obj2.RunMethod("size")));
 //BA.debugLineNum = 1248;BA.debugLine="For i = 0 To size -1";
{
final int step7 = 1;
final int limit7 = (int) (_size-1);
_i = (int) (0) ;
for (;(step7 > 0 && _i <= limit7) || (step7 < 0 && _i >= limit7) ;_i = ((int)(0 + _i + step7))  ) {
 //BA.debugLineNum = 1249;BA.debugLine="Obj3.Target = Obj2.RunMethod2(\"get\", i, \"java.la";
mostCurrent._obj3.Target = mostCurrent._obj2.RunMethod2("get",BA.NumberToString(_i),"java.lang.int");
 //BA.debugLineNum = 1250;BA.debugLine="size = Obj2.RunMethod(\"size\")";
_size = (int)(BA.ObjectToNumber(mostCurrent._obj2.RunMethod("size")));
 //BA.debugLineNum = 1251;BA.debugLine="Obj3.Target = Obj3.GetField(\"applicationInfo\") '";
mostCurrent._obj3.Target = mostCurrent._obj3.GetField("applicationInfo");
 //BA.debugLineNum = 1252;BA.debugLine="idt= Obj3.GetField(\"nativeLibraryDir\")";
mostCurrent._idt = BA.ObjectToString(mostCurrent._obj3.GetField("nativeLibraryDir"));
 //BA.debugLineNum = 1253;BA.debugLine="flags = Obj3.GetField(\"flags\")";
_flags = (int)(BA.ObjectToNumber(mostCurrent._obj3.GetField("flags")));
 //BA.debugLineNum = 1254;BA.debugLine="packName = Obj3.GetField(\"packageName\")";
mostCurrent._packname = BA.ObjectToString(mostCurrent._obj3.GetField("packageName"));
 //BA.debugLineNum = 1255;BA.debugLine="sdk= Obj3.GetField(\"targetSdkVersion\")";
_sdk = (int)(BA.ObjectToNumber(mostCurrent._obj3.GetField("targetSdkVersion")));
 //BA.debugLineNum = 1256;BA.debugLine="apath= Obj3.GetField(\"sourceDir\")";
mostCurrent._apath = BA.ObjectToString(mostCurrent._obj3.GetField("sourceDir"));
 //BA.debugLineNum = 1258;BA.debugLine="If Bit.And(flags, 1)  = 0 Then";
if (anywheresoftware.b4a.keywords.Common.Bit.And(_flags,(int) (1))==0) { 
 //BA.debugLineNum = 1260;BA.debugLine="args(0) = Obj3.Target";
mostCurrent._args[(int) (0)] = mostCurrent._obj3.Target;
 //BA.debugLineNum = 1261;BA.debugLine="Types(0) = \"android.content.pm.ApplicationInfo\"";
mostCurrent._types[(int) (0)] = "android.content.pm.ApplicationInfo";
 //BA.debugLineNum = 1262;BA.debugLine="name1 = Obj1.RunMethod4(\"getApplicationLabel\",";
mostCurrent._name1 = BA.ObjectToString(mostCurrent._obj1.RunMethod4("getApplicationLabel",mostCurrent._args,mostCurrent._types));
 //BA.debugLineNum = 1263;BA.debugLine="icon = Obj1.RunMethod4(\"getApplicationIcon\", ar";
mostCurrent._icon.setObject((android.graphics.drawable.BitmapDrawable)(mostCurrent._obj1.RunMethod4("getApplicationIcon",mostCurrent._args,mostCurrent._types)));
 //BA.debugLineNum = 1264;BA.debugLine="extra.Add(packName)";
mostCurrent._extra.Add((Object)(mostCurrent._packname));
 };
 }
};
 //BA.debugLineNum = 1267;BA.debugLine="create_lvs";
_create_lvs();
 //BA.debugLineNum = 1268;BA.debugLine="End Sub";
return "";
}
public static String  _b2dia_buttonpressed(de.amberhome.materialdialogs.MaterialDialogWrapper _dialog,String _action) throws Exception{
anywheresoftware.b4a.objects.IntentWrapper _i = null;
 //BA.debugLineNum = 766;BA.debugLine="Sub b2dia_ButtonPressed (Dialog As MaterialDialog,";
 //BA.debugLineNum = 767;BA.debugLine="Select Action";
switch (BA.switchObjectToInt(_action,_dialog.ACTION_POSITIVE,_dialog.ACTION_NEGATIVE,_dialog.ACTION_NEUTRAL)) {
case 0: {
 //BA.debugLineNum = 770;BA.debugLine="Dim i As Intent";
_i = new anywheresoftware.b4a.objects.IntentWrapper();
 //BA.debugLineNum = 771;BA.debugLine="i.Initialize(finish.Get(0),\"\")";
_i.Initialize(BA.ObjectToString(mostCurrent._finish.Get((int) (0))),"");
 //BA.debugLineNum = 772;BA.debugLine="i = pak.GetApplicationIntent(finish.Get(0))";
_i = _pak.GetApplicationIntent(BA.ObjectToString(mostCurrent._finish.Get((int) (0))));
 //BA.debugLineNum = 773;BA.debugLine="StartActivity(i)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(_i.getObject()));
 //BA.debugLineNum = 774;BA.debugLine="Animator.setanimati(\"slide_from_left\",\"slide_to";
mostCurrent._animator._setanimati(mostCurrent.activityBA,"slide_from_left","slide_to_left");
 break; }
case 1: {
 //BA.debugLineNum = 777;BA.debugLine="ToastMessageShow(Action, False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(_action),anywheresoftware.b4a.keywords.Common.False);
 break; }
case 2: {
 //BA.debugLineNum = 780;BA.debugLine="ToastMessageShow(\"canceled...\",False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("canceled..."),anywheresoftware.b4a.keywords.Common.False);
 break; }
}
;
 //BA.debugLineNum = 783;BA.debugLine="End Sub";
return "";
}
public static String  _b3dia_buttonpressed(de.amberhome.materialdialogs.MaterialDialogWrapper _dialog,String _action) throws Exception{
 //BA.debugLineNum = 789;BA.debugLine="Sub b3dia_ButtonPressed (Dialog As MaterialDialog,";
 //BA.debugLineNum = 790;BA.debugLine="Select Action";
switch (BA.switchObjectToInt(_action,_dialog.ACTION_POSITIVE,_dialog.ACTION_NEGATIVE,_dialog.ACTION_NEUTRAL)) {
case 0: {
 //BA.debugLineNum = 793;BA.debugLine="social_init";
_social_init();
 break; }
case 1: {
 //BA.debugLineNum = 796;BA.debugLine="ToastMessageShow(Action, False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(_action),anywheresoftware.b4a.keywords.Common.False);
 break; }
case 2: {
 //BA.debugLineNum = 799;BA.debugLine="ToastMessageShow(\"canceled...\",False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("canceled..."),anywheresoftware.b4a.keywords.Common.False);
 break; }
}
;
 //BA.debugLineNum = 802;BA.debugLine="End Sub";
return "";
}
public static String  _bckdia_buttonpressed(de.amberhome.materialdialogs.MaterialDialogWrapper _dialog,String _action) throws Exception{
 //BA.debugLineNum = 993;BA.debugLine="Sub bckdia_ButtonPressed (Dialog As MaterialDialog";
 //BA.debugLineNum = 994;BA.debugLine="Select Action";
switch (BA.switchObjectToInt(_action,_dialog.ACTION_POSITIVE,_dialog.ACTION_NEGATIVE,_dialog.ACTION_NEUTRAL)) {
case 0: {
 //BA.debugLineNum = 997;BA.debugLine="svList.SetVisibleAnimated(350,True)";
mostCurrent._svlist.SetVisibleAnimated((int) (350),anywheresoftware.b4a.keywords.Common.True);
 break; }
case 1: {
 //BA.debugLineNum = 1000;BA.debugLine="ToastMessageShow(Action, False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(_action),anywheresoftware.b4a.keywords.Common.False);
 break; }
case 2: {
 //BA.debugLineNum = 1003;BA.debugLine="svList.SetVisibleAnimated(350,True)";
mostCurrent._svlist.SetVisibleAnimated((int) (350),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1004;BA.debugLine="ToastMessageShow(Action,False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(_action),anywheresoftware.b4a.keywords.Common.False);
 break; }
}
;
 //BA.debugLineNum = 1007;BA.debugLine="End Sub";
return "";
}
public static String  _bckdia_itemlongselected(de.amberhome.materialdialogs.MaterialDialogWrapper _dialog,int _position,String _text) throws Exception{
String _s = "";
anywheresoftware.b4a.objects.LabelWrapper _dlab = null;
anywheresoftware.b4a.objects.LabelWrapper _dcont = null;
de.amberhome.materialdialogs.MaterialDialogBuilderWrapper _builder = null;
 //BA.debugLineNum = 976;BA.debugLine="Sub bckdia_ItemLongSelected (Dialog As MaterialDia";
 //BA.debugLineNum = 977;BA.debugLine="Dim s As String = finish.Get(0)";
_s = BA.ObjectToString(mostCurrent._finish.Get((int) (0)));
 //BA.debugLineNum = 978;BA.debugLine="Log(\"2_EXPAN: \"&s)";
anywheresoftware.b4a.keywords.Common.Log("2_EXPAN: "+_s);
 //BA.debugLineNum = 979;BA.debugLine="name1=s&\".apk\"";
mostCurrent._name1 = _s+".apk";
 //BA.debugLineNum = 980;BA.debugLine="packName=s";
mostCurrent._packname = _s;
 //BA.debugLineNum = 981;BA.debugLine="Dim dlab,dcont As Label";
_dlab = new anywheresoftware.b4a.objects.LabelWrapper();
_dcont = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 982;BA.debugLine="dlab.Initialize(\"\")";
_dlab.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 983;BA.debugLine="dcont.Initialize(\"\")";
_dcont.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 984;BA.debugLine="dlab.TextSize=15";
_dlab.setTextSize((float) (15));
 //BA.debugLineNum = 985;BA.debugLine="dcont.TextSize=14";
_dcont.setTextSize((float) (14));
 //BA.debugLineNum = 986;BA.debugLine="Dim Builder As MaterialDialogBuilder";
_builder = new de.amberhome.materialdialogs.MaterialDialogBuilderWrapper();
 //BA.debugLineNum = 987;BA.debugLine="Builder.Initialize(\"cidia\")";
_builder.Initialize(mostCurrent.activityBA,"cidia");
 //BA.debugLineNum = 988;BA.debugLine="Builder.Title(dlab.text).TitleColor(mcl.md_black";
_builder.Title(BA.ObjectToCharSequence(_dlab.getText())).TitleColor(mostCurrent._mcl.getmd_black_1000()).Icon((android.graphics.drawable.Drawable)(mostCurrent._icon.getObject())).LimitIconToDefaultSize().Theme(_builder.THEME_LIGHT).Content(BA.ObjectToCharSequence(_dcont.getText())).ContentLineSpacing((float) (1)).ContentColor(mostCurrent._mcl.getmd_black_1000()).Cancelable(anywheresoftware.b4a.keywords.Common.True).AutoDismiss(anywheresoftware.b4a.keywords.Common.False).NeutralText(BA.ObjectToCharSequence("Ok")).NeutralColor(mostCurrent._mcl.getmd_black_1000()).ItemsCallback().Build();
 //BA.debugLineNum = 989;BA.debugLine="Builder.ForceStacking(True)";
_builder.ForceStacking(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 990;BA.debugLine="deldia=Builder.Show";
mostCurrent._deldia = _builder.Show();
 //BA.debugLineNum = 991;BA.debugLine="deldia.Show";
mostCurrent._deldia.Show();
 //BA.debugLineNum = 992;BA.debugLine="End Sub";
return "";
}
public static String  _btb_navigationitemclick() throws Exception{
 //BA.debugLineNum = 395;BA.debugLine="Sub btb_NavigationItemClick";
 //BA.debugLineNum = 396;BA.debugLine="sm.HideMenus";
mostCurrent._sm.HideMenus();
 //BA.debugLineNum = 397;BA.debugLine="End Sub";
return "";
}
public static String  _butt_click() throws Exception{
 //BA.debugLineNum = 1452;BA.debugLine="Sub butt_Click";
 //BA.debugLineNum = 1453;BA.debugLine="ab1_Click";
_ab1_click();
 //BA.debugLineNum = 1454;BA.debugLine="End Sub";
return "";
}
public static String  _butt2_click() throws Exception{
anywheresoftware.b4a.objects.LabelWrapper _lab = null;
String _text = "";
de.amberhome.materialdialogs.MaterialDialogBuilderWrapper _builder = null;
 //BA.debugLineNum = 751;BA.debugLine="Sub butt2_click";
 //BA.debugLineNum = 752;BA.debugLine="Dim lab As Label";
_lab = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 753;BA.debugLine="lab.Initialize(\"lab\")";
_lab.Initialize(mostCurrent.activityBA,"lab");
 //BA.debugLineNum = 754;BA.debugLine="Dim text As String = \"Start \"&pak.GetApplicationL";
_text = "Start "+_pak.GetApplicationLabel(BA.ObjectToString(mostCurrent._finish.Get((int) (0))))+"?";
 //BA.debugLineNum = 755;BA.debugLine="lab.TextSize=18";
_lab.setTextSize((float) (18));
 //BA.debugLineNum = 756;BA.debugLine="lab.Gravity=Gravity.CENTER_HORIZONTAL";
_lab.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL);
 //BA.debugLineNum = 757;BA.debugLine="lab.TextColor=mcl.md_black_1000";
_lab.setTextColor(mostCurrent._mcl.getmd_black_1000());
 //BA.debugLineNum = 758;BA.debugLine="lab.Text=text";
_lab.setText(BA.ObjectToCharSequence(_text));
 //BA.debugLineNum = 759;BA.debugLine="icon=pak.GetApplicationIcon(finish.Get(0))";
mostCurrent._icon.setObject((android.graphics.drawable.BitmapDrawable)(_pak.GetApplicationIcon(BA.ObjectToString(mostCurrent._finish.Get((int) (0))))));
 //BA.debugLineNum = 760;BA.debugLine="Dim Builder As MaterialDialogBuilder";
_builder = new de.amberhome.materialdialogs.MaterialDialogBuilderWrapper();
 //BA.debugLineNum = 761;BA.debugLine="Builder.Initialize(\"b2dia\")";
_builder.Initialize(mostCurrent.activityBA,"b2dia");
 //BA.debugLineNum = 762;BA.debugLine="Builder.Title(pak.GetApplicationLabel(finish.Get(";
_builder.Title(BA.ObjectToCharSequence(_pak.GetApplicationLabel(BA.ObjectToString(mostCurrent._finish.Get((int) (0)))))).TitleColor(mostCurrent._mcl.getmd_black_1000()).Icon((android.graphics.drawable.Drawable)(mostCurrent._icon.getObject())).LimitIconToDefaultSize().Theme(_builder.THEME_LIGHT).Content(BA.ObjectToCharSequence(_lab.getText())).ContentLineSpacing((float) (1)).ContentColor(mostCurrent._mcl.getmd_black_1000()).Cancelable(anywheresoftware.b4a.keywords.Common.False).AutoDismiss(anywheresoftware.b4a.keywords.Common.True).PositiveText(BA.ObjectToCharSequence("Ok")).PositiveColor(mostCurrent._mcl.getmd_amber_900()).NeutralText(BA.ObjectToCharSequence("Cancel")).NeutralColor(mostCurrent._mcl.getmd_black_1000());
 //BA.debugLineNum = 763;BA.debugLine="deldia=Builder.Show";
mostCurrent._deldia = _builder.Show();
 //BA.debugLineNum = 764;BA.debugLine="deldia.Show";
mostCurrent._deldia.Show();
 //BA.debugLineNum = 765;BA.debugLine="End Sub";
return "";
}
public static String  _butt3_click() throws Exception{
 //BA.debugLineNum = 785;BA.debugLine="Sub butt3_click";
 //BA.debugLineNum = 786;BA.debugLine="social_init";
_social_init();
 //BA.debugLineNum = 787;BA.debugLine="End Sub";
return "";
}
public static String  _cbut_checkedchange(boolean _checked) throws Exception{
anywheresoftware.b4a.objects.drawable.BitmapDrawable _bg = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _bg1 = null;
 //BA.debugLineNum = 1333;BA.debugLine="Sub cbut_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 1334;BA.debugLine="Dim bg As BitmapDrawable";
_bg = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 1335;BA.debugLine="bg.Initialize(LoadBitmap(File.DirAssets,\"ic_check";
_bg.Initialize((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"ic_check_box_outline_blank_black_36dp.png").getObject()));
 //BA.debugLineNum = 1336;BA.debugLine="Dim bg1 As BitmapDrawable";
_bg1 = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 1337;BA.debugLine="bg1.Initialize(LoadBitmap(File.DirAssets,\"ic_chec";
_bg1.Initialize((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"ic_check_box_black_36dp.png").getObject()));
 //BA.debugLineNum = 1338;BA.debugLine="Select Checked";
switch (BA.switchObjectToInt(_checked,_checked==anywheresoftware.b4a.keywords.Common.True,_checked==anywheresoftware.b4a.keywords.Common.False)) {
case 0: {
 //BA.debugLineNum = 1340;BA.debugLine="cbut.Enabled=False";
mostCurrent._cbut.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 break; }
case 1: {
 //BA.debugLineNum = 1342;BA.debugLine="cbut.Enabled=True";
mostCurrent._cbut.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 break; }
}
;
 //BA.debugLineNum = 1344;BA.debugLine="End Sub";
return "";
}
public static String  _chk_dtas() throws Exception{
 //BA.debugLineNum = 803;BA.debugLine="Sub chk_dtas";
 //BA.debugLineNum = 804;BA.debugLine="packName=finish.Get(0)";
mostCurrent._packname = BA.ObjectToString(mostCurrent._finish.Get((int) (0)));
 //BA.debugLineNum = 805;BA.debugLine="If dtime.ContainsKey(packName) Then";
if (mostCurrent._dtime._containskey(mostCurrent._packname)) { 
 //BA.debugLineNum = 806;BA.debugLine="int_folder";
_int_folder();
 //BA.debugLineNum = 808;BA.debugLine="lst.CheckedColor=mcl.md_green_400";
mostCurrent._lst._checkedcolor = mostCurrent._mcl.getmd_green_400();
 }else {
 //BA.debugLineNum = 811;BA.debugLine="ab1_Click";
_ab1_click();
 };
 //BA.debugLineNum = 814;BA.debugLine="End Sub";
return "";
}
public static String  _cidia_buttonpressed(de.amberhome.materialdialogs.MaterialDialogWrapper _dialog,String _action) throws Exception{
String _s = "";
 //BA.debugLineNum = 908;BA.debugLine="Sub cidia_ButtonPressed (Dialog As MaterialDialog,";
 //BA.debugLineNum = 909;BA.debugLine="Select Action";
switch (BA.switchObjectToInt(_action,_dialog.ACTION_POSITIVE,_dialog.ACTION_NEGATIVE,_dialog.ACTION_NEUTRAL)) {
case 0: {
 //BA.debugLineNum = 911;BA.debugLine="Dim s As String = finish.Get(0)";
_s = BA.ObjectToString(mostCurrent._finish.Get((int) (0)));
 //BA.debugLineNum = 912;BA.debugLine="packName=s";
mostCurrent._packname = _s;
 //BA.debugLineNum = 913;BA.debugLine="name1=s&\".apk\"";
mostCurrent._name1 = _s+".apk";
 //BA.debugLineNum = 914;BA.debugLine="Log(\"cidia: \"&name1)";
anywheresoftware.b4a.keywords.Common.Log("cidia: "+mostCurrent._name1);
 //BA.debugLineNum = 915;BA.debugLine="File.Delete(dir,name1)";
anywheresoftware.b4a.keywords.Common.File.Delete(mostCurrent._dir,mostCurrent._name1);
 //BA.debugLineNum = 916;BA.debugLine="If Not(File.Exists(dir,name1)) Then";
if (anywheresoftware.b4a.keywords.Common.Not(anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._dir,mostCurrent._name1))) { 
 //BA.debugLineNum = 917;BA.debugLine="Dialog.Dismiss";
_dialog.Dismiss();
 };
 break; }
case 1: {
 //BA.debugLineNum = 920;BA.debugLine="ToastMessageShow(\"canceled\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("canceled"),anywheresoftware.b4a.keywords.Common.False);
 break; }
case 2: {
 //BA.debugLineNum = 922;BA.debugLine="ToastMessageShow(\"canceled...\",False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("canceled..."),anywheresoftware.b4a.keywords.Common.False);
 break; }
}
;
 //BA.debugLineNum = 924;BA.debugLine="End Sub";
return "";
}
public static String  _cldia_buttonpressed(de.amberhome.materialdialogs.MaterialDialogWrapper _dialog,String _action) throws Exception{
 //BA.debugLineNum = 562;BA.debugLine="Sub cldia_ButtonPressed (Dialog As MaterialDialog,";
 //BA.debugLineNum = 563;BA.debugLine="Select Action";
switch (BA.switchObjectToInt(_action,_dialog.ACTION_POSITIVE,_dialog.ACTION_NEGATIVE,_dialog.ACTION_NEUTRAL)) {
case 0: {
 //BA.debugLineNum = 565;BA.debugLine="ExitApplication";
anywheresoftware.b4a.keywords.Common.ExitApplication();
 break; }
case 1: {
 //BA.debugLineNum = 567;BA.debugLine="ToastMessageShow(Action, False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(_action),anywheresoftware.b4a.keywords.Common.False);
 break; }
case 2: {
 //BA.debugLineNum = 569;BA.debugLine="ToastMessageShow(\"canceled...\",False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("canceled..."),anywheresoftware.b4a.keywords.Common.False);
 break; }
}
;
 //BA.debugLineNum = 572;BA.debugLine="End Sub";
return "";
}
public static String  _close_click() throws Exception{
de.amberhome.materialdialogs.MaterialDialogBuilderWrapper _builder = null;
 //BA.debugLineNum = 554;BA.debugLine="Sub close_click";
 //BA.debugLineNum = 555;BA.debugLine="icon.Initialize(LoadBitmap(File.DirAssets,\"ic_inf";
mostCurrent._icon.Initialize((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"ic_info_black_36dp.png").getObject()));
 //BA.debugLineNum = 556;BA.debugLine="Dim Builder As MaterialDialogBuilder";
_builder = new de.amberhome.materialdialogs.MaterialDialogBuilderWrapper();
 //BA.debugLineNum = 557;BA.debugLine="Builder.Initialize(\"cldia\")";
_builder.Initialize(mostCurrent.activityBA,"cldia");
 //BA.debugLineNum = 558;BA.debugLine="Builder.Title(\"Exit\").TitleColor(mcl.md_black_100";
_builder.Title(BA.ObjectToCharSequence("Exit")).TitleColor(mostCurrent._mcl.getmd_black_1000()).Icon((android.graphics.drawable.Drawable)(mostCurrent._icon.getObject())).LimitIconToDefaultSize().Theme(_builder.THEME_LIGHT).Content(BA.ObjectToCharSequence("Close Apk Backup(Pro) now?")).ContentLineSpacing((float) (1)).ContentColor(mostCurrent._mcl.getmd_black_1000()).Cancelable(anywheresoftware.b4a.keywords.Common.False).AutoDismiss(anywheresoftware.b4a.keywords.Common.True).PositiveText(BA.ObjectToCharSequence("Yes")).PositiveColor(mostCurrent._mcl.getmd_amber_900()).NeutralText(BA.ObjectToCharSequence("No")).NeutralColor(mostCurrent._mcl.getmd_black_1000());
 //BA.debugLineNum = 559;BA.debugLine="deldia=Builder.Show";
mostCurrent._deldia = _builder.Show();
 //BA.debugLineNum = 560;BA.debugLine="deldia.Show";
mostCurrent._deldia.Show();
 //BA.debugLineNum = 561;BA.debugLine="End Sub";
return "";
}
public static String  _create_lvs() throws Exception{
String _total = "";
String _bcksizie = "";
Object _id = null;
int _i = 0;
 //BA.debugLineNum = 1270;BA.debugLine="Sub create_lvs";
 //BA.debugLineNum = 1271;BA.debugLine="Dim total,bcksizie As String, ID As Object";
_total = "";
_bcksizie = "";
_id = new Object();
 //BA.debugLineNum = 1272;BA.debugLine="pn.Clear";
mostCurrent._pn.Clear();
 //BA.debugLineNum = 1273;BA.debugLine="bcksizie=dtime.ListKeys.Size";
_bcksizie = BA.NumberToString(mostCurrent._dtime._listkeys().getSize());
 //BA.debugLineNum = 1275;BA.debugLine="lst.AddHeader(\"App(s): \"&extra.size&\", Backup(s):";
mostCurrent._lst._addheader("App(s): "+BA.NumberToString(mostCurrent._extra.getSize())+", Backup(s): "+_bcksizie);
 //BA.debugLineNum = 1277;BA.debugLine="For i=0 To   extra.Size-1";
{
final int step5 = 1;
final int limit5 = (int) (mostCurrent._extra.getSize()-1);
_i = (int) (0) ;
for (;(step5 > 0 && _i <= limit5) || (step5 < 0 && _i >= limit5) ;_i = ((int)(0 + _i + step5))  ) {
 //BA.debugLineNum = 1278;BA.debugLine="ID=extra.Get(i)";
_id = mostCurrent._extra.Get(_i);
 //BA.debugLineNum = 1279;BA.debugLine="name1=pak.GetApplicationLabel(extra.Get(i))";
mostCurrent._name1 = _pak.GetApplicationLabel(BA.ObjectToString(mostCurrent._extra.Get(_i)));
 //BA.debugLineNum = 1280;BA.debugLine="total =pak.GetVersionName(extra.Get(i))";
_total = _pak.GetVersionName(BA.ObjectToString(mostCurrent._extra.Get(_i)));
 //BA.debugLineNum = 1282;BA.debugLine="lst.AddCustomItem(ID,CreateItem(name1&\" \"&total,";
mostCurrent._lst._addcustomitem(_id,_createitem(mostCurrent._name1+" "+_total,_i),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 }
};
 //BA.debugLineNum = 1284;BA.debugLine="lst.ResizePanel";
mostCurrent._lst._resizepanel();
 //BA.debugLineNum = 1285;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.objects.PanelWrapper  _createitem(String _h,int _i) throws Exception{
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
anywheresoftware.b4a.objects.LabelWrapper _ver = null;
anywheresoftware.b4a.objects.PanelWrapper _pnl = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _bg = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _bg1 = null;
anywheresoftware.b4a.objects.ImageViewWrapper _iiv = null;
anywheresoftware.b4a.keywords.constants.TypefaceWrapper _cfont = null;
anywheresoftware.b4a.keywords.constants.TypefaceWrapper _font = null;
 //BA.debugLineNum = 1286;BA.debugLine="Sub CreateItem(h As String  , i As Int) As Panel";
 //BA.debugLineNum = 1287;BA.debugLine="Dim lbl,ver As Label";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_ver = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 1288;BA.debugLine="Dim pnl As Panel";
_pnl = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 1289;BA.debugLine="pnl.Initialize(\"\")";
_pnl.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1290;BA.debugLine="Dim bg As BitmapDrawable";
_bg = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 1291;BA.debugLine="bg.Initialize(LoadBitmap(File.DirAssets,\"ic_check";
_bg.Initialize((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"ic_check_box_outline_blank_black_36dp.png").getObject()));
 //BA.debugLineNum = 1292;BA.debugLine="Dim bg1 As BitmapDrawable";
_bg1 = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 1293;BA.debugLine="bg1.Initialize(LoadBitmap(File.DirAssets,\"ic_chec";
_bg1.Initialize((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"ic_check_box_black_36dp.png").getObject()));
 //BA.debugLineNum = 1294;BA.debugLine="Dim iiv As ImageView";
_iiv = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 1295;BA.debugLine="iiv.Initialize(\"\")";
_iiv.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1296;BA.debugLine="Dim cfont As Typeface=Typeface.LoadFromAssets(\"Op";
_cfont = new anywheresoftware.b4a.keywords.constants.TypefaceWrapper();
_cfont.setObject((android.graphics.Typeface)(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("OpenSans.ttf")));
 //BA.debugLineNum = 1298;BA.debugLine="cbut.Initialize(\"cbut\")";
mostCurrent._cbut.Initialize(mostCurrent.activityBA,"cbut");
 //BA.debugLineNum = 1299;BA.debugLine="cbut.Gravity=Gravity.FILL";
mostCurrent._cbut.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 1300;BA.debugLine="cbut.Enabled=False";
mostCurrent._cbut.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1301;BA.debugLine="icon=pak.GetApplicationIcon(extra.Get(i))";
mostCurrent._icon.setObject((android.graphics.drawable.BitmapDrawable)(_pak.GetApplicationIcon(BA.ObjectToString(mostCurrent._extra.Get(_i)))));
 //BA.debugLineNum = 1302;BA.debugLine="icon.Gravity=Gravity.FILL";
mostCurrent._icon.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 1303;BA.debugLine="iiv.Bitmap=icon.Bitmap";
_iiv.setBitmap(mostCurrent._icon.getBitmap());
 //BA.debugLineNum = 1304;BA.debugLine="Dim font As Typeface";
_font = new anywheresoftware.b4a.keywords.constants.TypefaceWrapper();
 //BA.debugLineNum = 1305;BA.debugLine="font=Typeface.CreateNew(Typeface.LoadFromAssets(\"";
_font.setObject((android.graphics.Typeface)(anywheresoftware.b4a.keywords.Common.Typeface.CreateNew(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("OpenSans.ttf"),anywheresoftware.b4a.keywords.Common.Typeface.STYLE_ITALIC)));
 //BA.debugLineNum = 1306;BA.debugLine="If dtime.ContainsKey(extra.Get(i)) Then";
if (mostCurrent._dtime._containskey(BA.ObjectToString(mostCurrent._extra.Get(_i)))) { 
 //BA.debugLineNum = 1307;BA.debugLine="cbut.Checked=True";
mostCurrent._cbut.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1308;BA.debugLine="paint_pan(pnl,2)";
_paint_pan(_pnl,(int) (2));
 }else {
 //BA.debugLineNum = 1310;BA.debugLine="cbut.Checked=False";
mostCurrent._cbut.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1311;BA.debugLine="paint_pan(pnl,0)";
_paint_pan(_pnl,(int) (0));
 };
 //BA.debugLineNum = 1313;BA.debugLine="pnl.AddView(cbut, 0dip, 8dip, 42dip, 42dip)";
_pnl.AddView((android.view.View)(mostCurrent._cbut.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (8)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (42)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (42)));
 //BA.debugLineNum = 1314;BA.debugLine="lbl.Initialize(\"\") 'View #1";
_lbl.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1315;BA.debugLine="lbl.Typeface=cfont";
_lbl.setTypeface((android.graphics.Typeface)(_cfont.getObject()));
 //BA.debugLineNum = 1316;BA.debugLine="lbl.Text= h";
_lbl.setText(BA.ObjectToCharSequence(_h));
 //BA.debugLineNum = 1317;BA.debugLine="lbl.TextColor = mcl.md_blue_700";
_lbl.setTextColor(mostCurrent._mcl.getmd_blue_700());
 //BA.debugLineNum = 1318;BA.debugLine="lbl.TextSize = 18";
_lbl.setTextSize((float) (18));
 //BA.debugLineNum = 1319;BA.debugLine="lbl.Gravity = Gravity.CENTER_VERTICAL";
_lbl.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL);
 //BA.debugLineNum = 1320;BA.debugLine="ver.Initialize(\"\")";
_ver.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1321;BA.debugLine="ver.TextColor=Colors.DarkGray";
_ver.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.DarkGray);
 //BA.debugLineNum = 1322;BA.debugLine="ver.Typeface=font";
_ver.setTypeface((android.graphics.Typeface)(_font.getObject()));
 //BA.debugLineNum = 1323;BA.debugLine="ver.TextSize=13";
_ver.setTextSize((float) (13));
 //BA.debugLineNum = 1324;BA.debugLine="ver.Gravity = Gravity.LEFT";
_ver.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.LEFT);
 //BA.debugLineNum = 1325;BA.debugLine="ver.Text=extra.Get(i)";
_ver.setText(BA.ObjectToCharSequence(mostCurrent._extra.Get(_i)));
 //BA.debugLineNum = 1326;BA.debugLine="iiv.Gravity=Gravity.FILL";
_iiv.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 1327;BA.debugLine="pnl.AddView(lbl, 43dip, 0, lst.getWidth - 130dip,";
_pnl.AddView((android.view.View)(_lbl.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (43)),(int) (0),(int) ((double)(Double.parseDouble(mostCurrent._lst._getwidth()))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (130))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)));
 //BA.debugLineNum = 1328;BA.debugLine="pnl.AddView(ver, 43dip, lbl.Height, lst.getWidth";
_pnl.AddView((android.view.View)(_ver.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (43)),_lbl.getHeight(),(int) ((double)(Double.parseDouble(mostCurrent._lst._getwidth()))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (120))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)));
 //BA.debugLineNum = 1329;BA.debugLine="pnl.AddView(iiv,82%x,3dip,52dip,52dip)";
_pnl.AddView((android.view.View)(_iiv.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (82),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (3)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (52)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (52)));
 //BA.debugLineNum = 1331;BA.debugLine="Return pnl";
if (true) return _pnl;
 //BA.debugLineNum = 1332;BA.debugLine="End Sub";
return null;
}
public static String  _del_dia() throws Exception{
de.amberhome.materialdialogs.MaterialSimpleListItemWrapper _imi = null;
anywheresoftware.b4a.objects.LabelWrapper _dlab = null;
anywheresoftware.b4a.objects.LabelWrapper _dcont = null;
anywheresoftware.b4a.objects.collections.List _dlist = null;
de.amberhome.materialdialogs.MaterialDialogBuilderWrapper _builder = null;
String _i = "";
 //BA.debugLineNum = 598;BA.debugLine="Sub del_dia";
 //BA.debugLineNum = 599;BA.debugLine="Dim imi As MaterialSimpleListItem";
_imi = new de.amberhome.materialdialogs.MaterialSimpleListItemWrapper();
 //BA.debugLineNum = 600;BA.debugLine="Dim dlab,dcont As Label";
_dlab = new anywheresoftware.b4a.objects.LabelWrapper();
_dcont = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 601;BA.debugLine="dlab.Initialize(\"\")";
_dlab.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 602;BA.debugLine="dcont.Initialize(\"\")";
_dcont.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 603;BA.debugLine="dlab.TextSize=15";
_dlab.setTextSize((float) (15));
 //BA.debugLineNum = 604;BA.debugLine="dcont.TextSize=14";
_dcont.setTextSize((float) (14));
 //BA.debugLineNum = 605;BA.debugLine="Dim dlist As List";
_dlist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 606;BA.debugLine="dlist.Initialize";
_dlist.Initialize();
 //BA.debugLineNum = 607;BA.debugLine="dir=datas.GetSimple(\"0\")";
mostCurrent._dir = mostCurrent._datas._getsimple("0");
 //BA.debugLineNum = 609;BA.debugLine="dlist=File.ListFiles(dir)";
_dlist = anywheresoftware.b4a.keywords.Common.File.ListFiles(mostCurrent._dir);
 //BA.debugLineNum = 610;BA.debugLine="dlab.Text=\"Delete Backup Archive ?\"";
_dlab.setText(BA.ObjectToCharSequence("Delete Backup Archive ?"));
 //BA.debugLineNum = 611;BA.debugLine="dcont.Text=\"Files to clear: \"&dlist.size&\" apk´s";
_dcont.setText(BA.ObjectToCharSequence("Files to clear: "+BA.NumberToString(_dlist.getSize())+" apk´s found"));
 //BA.debugLineNum = 612;BA.debugLine="icon.Initialize(LoadBitmap(File.DirAssets,\"ic_sd_";
mostCurrent._icon.Initialize((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"ic_sd_storage_black_36dp.png").getObject()));
 //BA.debugLineNum = 613;BA.debugLine="icon.Gravity=Gravity.FILL";
mostCurrent._icon.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 614;BA.debugLine="Dim Builder As MaterialDialogBuilder";
_builder = new de.amberhome.materialdialogs.MaterialDialogBuilderWrapper();
 //BA.debugLineNum = 615;BA.debugLine="Builder.Initialize(\"Dialog2\")";
_builder.Initialize(mostCurrent.activityBA,"Dialog2");
 //BA.debugLineNum = 616;BA.debugLine="Builder.Title(dlab.text).TitleColor(mcl.md_black_";
_builder.Title(BA.ObjectToCharSequence(_dlab.getText())).TitleColor(mostCurrent._mcl.getmd_black_1000()).Icon((android.graphics.drawable.Drawable)(mostCurrent._icon.getObject())).LimitIconToDefaultSize().Theme(_builder.THEME_LIGHT).Content(BA.ObjectToCharSequence(_dcont.getText())).ContentLineSpacing((float) (1)).ContentColor(mostCurrent._mcl.getmd_black_1000()).Cancelable(anywheresoftware.b4a.keywords.Common.False).AutoDismiss(anywheresoftware.b4a.keywords.Common.True).PositiveText(BA.ObjectToCharSequence("Yes delete")).PositiveColor(mostCurrent._mcl.getmd_amber_900()).NeutralText(BA.ObjectToCharSequence("Cancel")).NeutralColor(mostCurrent._mcl.getmd_black_1000());
 //BA.debugLineNum = 617;BA.debugLine="For Each i As String In dlist";
{
final anywheresoftware.b4a.BA.IterableList group18 = _dlist;
final int groupLen18 = group18.getSize()
;int index18 = 0;
;
for (; index18 < groupLen18;index18++){
_i = BA.ObjectToString(group18.Get(index18));
 //BA.debugLineNum = 618;BA.debugLine="imi.Initialize2(\"ili\",i)";
_imi.Initialize2(processBA,"ili",BA.ObjectToCharSequence(_i));
 //BA.debugLineNum = 619;BA.debugLine="Builder.AddSimpleItem(imi)";
_builder.AddSimpleItem(_imi);
 }
};
 //BA.debugLineNum = 621;BA.debugLine="deldia=Builder.Show";
mostCurrent._deldia = _builder.Show();
 //BA.debugLineNum = 622;BA.debugLine="deldia.Show";
mostCurrent._deldia.Show();
 //BA.debugLineNum = 623;BA.debugLine="End Sub";
return "";
}
public static String  _delete_files() throws Exception{
de.amberhome.materialdialogs.MaterialDialogBuilderWrapper _builder = null;
anywheresoftware.b4a.objects.collections.List _dlist = null;
int _maxs = 0;
String _lf = "";
int _i = 0;
String _s = "";
 //BA.debugLineNum = 625;BA.debugLine="Sub delete_files";
 //BA.debugLineNum = 626;BA.debugLine="Dim Builder As MaterialDialogBuilder";
_builder = new de.amberhome.materialdialogs.MaterialDialogBuilderWrapper();
 //BA.debugLineNum = 627;BA.debugLine="Dim dlist As List";
_dlist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 628;BA.debugLine="Dim maxs As Int";
_maxs = 0;
 //BA.debugLineNum = 629;BA.debugLine="dlist.Initialize";
_dlist.Initialize();
 //BA.debugLineNum = 630;BA.debugLine="dir=datas.GetSimple(\"0\")";
mostCurrent._dir = mostCurrent._datas._getsimple("0");
 //BA.debugLineNum = 632;BA.debugLine="dlist=File.ListFiles(dir)";
_dlist = anywheresoftware.b4a.keywords.Common.File.ListFiles(mostCurrent._dir);
 //BA.debugLineNum = 633;BA.debugLine="maxs=dlist.Size";
_maxs = _dlist.getSize();
 //BA.debugLineNum = 635;BA.debugLine="icon.Initialize(LoadBitmap(File.DirAssets,\"ic_ass";
mostCurrent._icon.Initialize((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"ic_assignment_black_36dp.png").getObject()));
 //BA.debugLineNum = 636;BA.debugLine="Builder.Initialize(\"Dialog4\")";
_builder.Initialize(mostCurrent.activityBA,"Dialog4");
 //BA.debugLineNum = 637;BA.debugLine="Builder.Title(\"cleaning..\").TitleColor(mcl.md_bla";
_builder.Title(BA.ObjectToCharSequence("cleaning..")).TitleColor(mostCurrent._mcl.getmd_black_1000()).TitleGravity(_builder.GRAVITY_START).Icon((android.graphics.drawable.Drawable)(mostCurrent._icon.getObject())).LimitIconToDefaultSize().Theme(_builder.THEME_LIGHT).Content(BA.ObjectToCharSequence("Delete Apk´s from: "+mostCurrent._datas._getsimple("0"))).ContentLineSpacing((float) (1)).ContentColor(mostCurrent._mcl.getmd_black_1000()).Cancelable(anywheresoftware.b4a.keywords.Common.False).AutoDismiss(anywheresoftware.b4a.keywords.Common.True).Build();
 //BA.debugLineNum = 638;BA.debugLine="Builder.progress2(False,maxs,True)";
_builder.Progress2(anywheresoftware.b4a.keywords.Common.False,_maxs,anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 639;BA.debugLine="df=Builder.Show";
mostCurrent._df = _builder.Show();
 //BA.debugLineNum = 640;BA.debugLine="df.Show";
mostCurrent._df.Show();
 //BA.debugLineNum = 641;BA.debugLine="dex.Enabled=True";
_dex.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 642;BA.debugLine="For Each lf As String In dlist";
{
final anywheresoftware.b4a.BA.IterableList group15 = _dlist;
final int groupLen15 = group15.getSize()
;int index15 = 0;
;
for (; index15 < groupLen15;index15++){
_lf = BA.ObjectToString(group15.Get(index15));
 //BA.debugLineNum = 643;BA.debugLine="Label4.Text=lf";
mostCurrent._label4.setText(BA.ObjectToCharSequence(_lf));
 }
};
 //BA.debugLineNum = 645;BA.debugLine="For i = 0 To dlist.Size-1";
{
final int step18 = 1;
final int limit18 = (int) (_dlist.getSize()-1);
_i = (int) (0) ;
for (;(step18 > 0 && _i <= limit18) || (step18 < 0 && _i >= limit18) ;_i = ((int)(0 + _i + step18))  ) {
 //BA.debugLineNum = 646;BA.debugLine="File.Delete(dir,dlist.Get(i))";
anywheresoftware.b4a.keywords.Common.File.Delete(mostCurrent._dir,BA.ObjectToString(_dlist.Get(_i)));
 }
};
 //BA.debugLineNum = 648;BA.debugLine="For Each s As String In dtime.ListKeys";
{
final anywheresoftware.b4a.BA.IterableList group21 = mostCurrent._dtime._listkeys();
final int groupLen21 = group21.getSize()
;int index21 = 0;
;
for (; index21 < groupLen21;index21++){
_s = BA.ObjectToString(group21.Get(index21));
 //BA.debugLineNum = 649;BA.debugLine="dtime.Remove(s)";
mostCurrent._dtime._remove(_s);
 //BA.debugLineNum = 650;BA.debugLine="bcount.Remove(s)";
mostCurrent._bcount._remove(_s);
 }
};
 //BA.debugLineNum = 652;BA.debugLine="End Sub";
return "";
}
public static String  _dev_info() throws Exception{
anywheresoftware.b4a.objects.LabelWrapper _l1 = null;
anywheresoftware.b4a.objects.LabelWrapper _l2 = null;
anywheresoftware.b4a.objects.PanelWrapper _pnl = null;
de.amberhome.materialdialogs.MaterialDialogWrapper _infodia = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _info = null;
de.amberhome.materialdialogs.MaterialDialogBuilderWrapper _builder = null;
 //BA.debugLineNum = 683;BA.debugLine="Sub dev_info";
 //BA.debugLineNum = 684;BA.debugLine="Dim l1,l2 As Label";
_l1 = new anywheresoftware.b4a.objects.LabelWrapper();
_l2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 685;BA.debugLine="Dim pnl As Panel";
_pnl = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 686;BA.debugLine="pnl.Initialize(\"pnl\")";
_pnl.Initialize(mostCurrent.activityBA,"pnl");
 //BA.debugLineNum = 687;BA.debugLine="l1.Initialize(\"\")";
_l1.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 688;BA.debugLine="l2.Initialize(\"\")";
_l2.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 689;BA.debugLine="l2.TextSize=16";
_l2.setTextSize((float) (16));
 //BA.debugLineNum = 690;BA.debugLine="l1.TextSize=15";
_l1.setTextSize((float) (15));
 //BA.debugLineNum = 691;BA.debugLine="l1.textcolor=Colors.ARGB(200,255,255,255)";
_l1.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (200),(int) (255),(int) (255),(int) (255)));
 //BA.debugLineNum = 692;BA.debugLine="l2.textcolor=mcl.md_black_1000";
_l2.setTextColor(mostCurrent._mcl.getmd_black_1000());
 //BA.debugLineNum = 693;BA.debugLine="l1.Gravity=Gravity.TOP";
_l1.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.TOP);
 //BA.debugLineNum = 694;BA.debugLine="l1.Typeface=Typeface.LoadFromAssets(\"OpenSans.ttf";
_l1.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("OpenSans.ttf"));
 //BA.debugLineNum = 695;BA.debugLine="l2.Typeface=Typeface.LoadFromAssets(\"OpenSans.ttf";
_l2.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("OpenSans.ttf"));
 //BA.debugLineNum = 696;BA.debugLine="l2.Text=\"About \"&pak.GetApplicationLabel(\"com.apk";
_l2.setText(BA.ObjectToCharSequence("About "+_pak.GetApplicationLabel("com.apkbackup.de")));
 //BA.debugLineNum = 697;BA.debugLine="l1.Text=pak.GetApplicationLabel(\"com.apkbackup.de";
_l1.setText(BA.ObjectToCharSequence(_pak.GetApplicationLabel("com.apkbackup.de")+", Version: "+_pak.GetVersionName("com.apkbackup.de")+" | Build: "+BA.NumberToString(_pak.GetVersionCode("com.apkbackup.de"))+". Code/Design by D. Trojan, www.battcatt.bplaced.net for Support/Infos. APK Backup(Pro) by APEX All rights reserved ©2017"));
 //BA.debugLineNum = 698;BA.debugLine="pnl.AddView(l1,25dip,1dip,88%x,98%y)";
_pnl.AddView((android.view.View)(_l1.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (88),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (98),mostCurrent.activityBA));
 //BA.debugLineNum = 699;BA.debugLine="Dim infodia As MaterialDialog";
_infodia = new de.amberhome.materialdialogs.MaterialDialogWrapper();
 //BA.debugLineNum = 700;BA.debugLine="Dim info As BitmapDrawable";
_info = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 701;BA.debugLine="info.Initialize(LoadBitmap(File.DirAssets,\"ic_inf";
_info.Initialize((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"ic_info_outline_black_36dp.png").getObject()));
 //BA.debugLineNum = 702;BA.debugLine="Dim Builder As MaterialDialogBuilder";
_builder = new de.amberhome.materialdialogs.MaterialDialogBuilderWrapper();
 //BA.debugLineNum = 703;BA.debugLine="Builder.Initialize(\"Dialog3\")";
_builder.Initialize(mostCurrent.activityBA,"Dialog3");
 //BA.debugLineNum = 704;BA.debugLine="Builder.Title(l2.Text).TitleColor(mcl.md_black_10";
_builder.Title(BA.ObjectToCharSequence(_l2.getText())).TitleColor(mostCurrent._mcl.getmd_black_1000()).Icon((android.graphics.drawable.Drawable)(_info.getObject())).LimitIconToDefaultSize().Theme(_builder.THEME_LIGHT).Content(BA.ObjectToCharSequence(_l1.getText())).ContentLineSpacing((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1)))).ContentColor(mostCurrent._mcl.getmd_black_1000()).Cancelable(anywheresoftware.b4a.keywords.Common.True).NeutralText(BA.ObjectToCharSequence("close")).NeutralColor(mostCurrent._mcl.getmd_blue_600()).Theme(_builder.THEME_LIGHT).ContentGravity(_builder.GRAVITY_START);
 //BA.debugLineNum = 705;BA.debugLine="infodia=Builder.Show";
_infodia = _builder.Show();
 //BA.debugLineNum = 706;BA.debugLine="infodia.Show";
_infodia.Show();
 //BA.debugLineNum = 708;BA.debugLine="End Sub";
return "";
}
public static String  _dex_tick() throws Exception{
 //BA.debugLineNum = 653;BA.debugLine="Sub dex_Tick";
 //BA.debugLineNum = 654;BA.debugLine="If Not(df.IsShowing) Then";
if (anywheresoftware.b4a.keywords.Common.Not(mostCurrent._df.IsShowing())) { 
 //BA.debugLineNum = 655;BA.debugLine="dex.Enabled = False";
_dex.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 656;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 658;BA.debugLine="If df.CurrentProgress = df.MaxProgress Then";
if (mostCurrent._df.getCurrentProgress()==mostCurrent._df.getMaxProgress()) { 
 //BA.debugLineNum = 659;BA.debugLine="dex.Enabled = False";
_dex.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 660;BA.debugLine="df.Content = \"Done! Please wait..\"";
mostCurrent._df.setContent(BA.ObjectToCharSequence("Done! Please wait.."));
 //BA.debugLineNum = 661;BA.debugLine="StartActivity(swit)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._swit.getObject()));
 //BA.debugLineNum = 662;BA.debugLine="df.Dismiss";
mostCurrent._df.Dismiss();
 //BA.debugLineNum = 663;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 664;BA.debugLine="Animator.setanimati(\"slide_from_left\",\"slide_to_";
mostCurrent._animator._setanimati(mostCurrent.activityBA,"slide_from_left","slide_to_left");
 }else {
 //BA.debugLineNum = 666;BA.debugLine="df.IncrementProgress(1)";
mostCurrent._df.IncrementProgress((int) (1));
 };
 //BA.debugLineNum = 668;BA.debugLine="End Sub";
return "";
}
public static String  _dialog2_buttonpressed(de.amberhome.materialdialogs.MaterialDialogWrapper _dialog,String _action) throws Exception{
 //BA.debugLineNum = 669;BA.debugLine="Sub Dialog2_ButtonPressed (Dialog As MaterialDialo";
 //BA.debugLineNum = 670;BA.debugLine="Select Action";
switch (BA.switchObjectToInt(_action,_dialog.ACTION_POSITIVE,_dialog.ACTION_NEGATIVE,_dialog.ACTION_NEUTRAL)) {
case 0: {
 //BA.debugLineNum = 673;BA.debugLine="delete_files";
_delete_files();
 break; }
case 1: {
 //BA.debugLineNum = 676;BA.debugLine="ToastMessageShow(Action, False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(_action),anywheresoftware.b4a.keywords.Common.False);
 break; }
case 2: {
 //BA.debugLineNum = 679;BA.debugLine="ToastMessageShow(\"canceled...\",False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("canceled..."),anywheresoftware.b4a.keywords.Common.False);
 break; }
}
;
 //BA.debugLineNum = 682;BA.debugLine="End Sub";
return "";
}
public static String  _end_msg() throws Exception{
String _dirs = "";
String _s = "";
String _e = "";
String _total = "";
de.amberhome.materialdialogs.MaterialDialogBuilderWrapper _builder = null;
 //BA.debugLineNum = 1040;BA.debugLine="Sub end_msg";
 //BA.debugLineNum = 1041;BA.debugLine="Dim dirs As String";
_dirs = "";
 //BA.debugLineNum = 1042;BA.debugLine="If datas.ContainsKey(\"0\") Then";
if (mostCurrent._datas._containskey("0")) { 
 //BA.debugLineNum = 1043;BA.debugLine="dirs=datas.GetSimple(\"0\")";
_dirs = mostCurrent._datas._getsimple("0");
 };
 //BA.debugLineNum = 1045;BA.debugLine="Dim s As String = finish.Get(0)";
_s = BA.ObjectToString(mostCurrent._finish.Get((int) (0)));
 //BA.debugLineNum = 1046;BA.debugLine="packName=s";
mostCurrent._packname = _s;
 //BA.debugLineNum = 1047;BA.debugLine="Dim e As String=finish.get(0)";
_e = BA.ObjectToString(mostCurrent._finish.Get((int) (0)));
 //BA.debugLineNum = 1048;BA.debugLine="name1=pak.GetApplicationLabel(e)";
mostCurrent._name1 = _pak.GetApplicationLabel(_e);
 //BA.debugLineNum = 1049;BA.debugLine="icon=pak.GetApplicationIcon(s)";
mostCurrent._icon.setObject((android.graphics.drawable.BitmapDrawable)(_pak.GetApplicationIcon(_s)));
 //BA.debugLineNum = 1050;BA.debugLine="Label4.Text=name1";
mostCurrent._label4.setText(BA.ObjectToCharSequence(mostCurrent._name1));
 //BA.debugLineNum = 1051;BA.debugLine="Dim total As String";
_total = "";
 //BA.debugLineNum = 1052;BA.debugLine="total = FormatFileSize(File.Size(GetParentPath(Ge";
_total = _formatfilesize((float) (anywheresoftware.b4a.keywords.Common.File.Size(_getparentpath(_getsourcedir(_getactivitiesinfo(mostCurrent._packname))),_getfilename(_getsourcedir(_getactivitiesinfo(mostCurrent._packname))))));
 //BA.debugLineNum = 1053;BA.debugLine="Dim Builder As MaterialDialogBuilder";
_builder = new de.amberhome.materialdialogs.MaterialDialogBuilderWrapper();
 //BA.debugLineNum = 1054;BA.debugLine="Builder.Initialize(\"endia\")";
_builder.Initialize(mostCurrent.activityBA,"endia");
 //BA.debugLineNum = 1055;BA.debugLine="Builder.Title(\"Complete..\").TitleColor(mcl.md_bla";
_builder.Title(BA.ObjectToCharSequence("Complete..")).TitleColor(mostCurrent._mcl.getmd_black_1000()).Icon((android.graphics.drawable.Drawable)(mostCurrent._icon.getObject())).LimitIconToDefaultSize().Theme(_builder.THEME_LIGHT).Content(BA.ObjectToCharSequence("Backup Info:")).ContentLineSpacing((float) (1)).ContentColor(mostCurrent._mcl.getmd_black_1000()).Cancelable(anywheresoftware.b4a.keywords.Common.False).AutoDismiss(anywheresoftware.b4a.keywords.Common.False).PositiveText(BA.ObjectToCharSequence("Next..")).PositiveColor(mostCurrent._mcl.getmd_amber_900()).NeutralText(BA.ObjectToCharSequence("")).NeutralColor(mostCurrent._mcl.getmd_black_1000()).ForceStacking(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1056;BA.debugLine="Builder.Items(Array As String (packName&\".apk\",to";
_builder.Items((java.lang.CharSequence[])(new String[]{mostCurrent._packname+".apk",_total,"SD:",_dirs}));
 //BA.debugLineNum = 1057;BA.debugLine="deldia=Builder.Show";
mostCurrent._deldia = _builder.Show();
 //BA.debugLineNum = 1058;BA.debugLine="deldia.Show";
mostCurrent._deldia.Show();
 //BA.debugLineNum = 1060;BA.debugLine="End Sub";
return "";
}
public static String  _endia_buttonpressed(de.amberhome.materialdialogs.MaterialDialogWrapper _dialog,String _action) throws Exception{
 //BA.debugLineNum = 1061;BA.debugLine="Sub endia_ButtonPressed (Dialog As MaterialDialog,";
 //BA.debugLineNum = 1062;BA.debugLine="Select Action";
switch (BA.switchObjectToInt(_action,_dialog.ACTION_POSITIVE,_dialog.ACTION_NEGATIVE,_dialog.ACTION_NEUTRAL)) {
case 0: {
 //BA.debugLineNum = 1068;BA.debugLine="file_check";
_file_check();
 //BA.debugLineNum = 1069;BA.debugLine="lst.ResizePanel";
mostCurrent._lst._resizepanel();
 //BA.debugLineNum = 1070;BA.debugLine="pdia.Dismiss";
mostCurrent._pdia.Dismiss();
 //BA.debugLineNum = 1071;BA.debugLine="deldia.Dismiss";
mostCurrent._deldia.Dismiss();
 //BA.debugLineNum = 1072;BA.debugLine="CallSubDelayed(swit,\"rebound\")";
anywheresoftware.b4a.keywords.Common.CallSubDelayed(processBA,(Object)(mostCurrent._swit.getObject()),"rebound");
 //BA.debugLineNum = 1073;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 break; }
case 1: {
 //BA.debugLineNum = 1075;BA.debugLine="ToastMessageShow(Action, False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(_action),anywheresoftware.b4a.keywords.Common.False);
 break; }
case 2: {
 //BA.debugLineNum = 1077;BA.debugLine="ToastMessageShow(Action,False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(_action),anywheresoftware.b4a.keywords.Common.False);
 break; }
}
;
 //BA.debugLineNum = 1080;BA.debugLine="End Sub";
return "";
}
public static String  _ex_pan() throws Exception{
String _fname = "";
String _version = "";
String _s = "";
String _h = "";
anywheresoftware.b4a.objects.LabelWrapper _l1 = null;
anywheresoftware.b4a.objects.LabelWrapper _l2 = null;
anywheresoftware.b4a.objects.LabelWrapper _l3 = null;
String _total = "";
de.amberhome.materialdialogs.MaterialDialogBuilderWrapper _builder = null;
 //BA.debugLineNum = 938;BA.debugLine="Sub ex_pan";
 //BA.debugLineNum = 939;BA.debugLine="Dim fname,version As String";
_fname = "";
_version = "";
 //BA.debugLineNum = 940;BA.debugLine="Dim s As String = finish.Get(0)";
_s = BA.ObjectToString(mostCurrent._finish.Get((int) (0)));
 //BA.debugLineNum = 941;BA.debugLine="fname=pak.GetApplicationLabel(s)";
_fname = _pak.GetApplicationLabel(_s);
 //BA.debugLineNum = 942;BA.debugLine="version=pak.GetVersionName(s)";
_version = _pak.GetVersionName(_s);
 //BA.debugLineNum = 943;BA.debugLine="Log(\"EXPAN: \"&s)";
anywheresoftware.b4a.keywords.Common.Log("EXPAN: "+_s);
 //BA.debugLineNum = 944;BA.debugLine="name1=fname&\"_\"&version&\".apk\"";
mostCurrent._name1 = _fname+"_"+_version+".apk";
 //BA.debugLineNum = 945;BA.debugLine="packName=s";
mostCurrent._packname = _s;
 //BA.debugLineNum = 946;BA.debugLine="Bpath.Text=datas.GetSimple(\"0\")";
mostCurrent._bpath.setText(BA.ObjectToCharSequence(mostCurrent._datas._getsimple("0")));
 //BA.debugLineNum = 947;BA.debugLine="For Each h As String In extra";
{
final anywheresoftware.b4a.BA.IterableList group9 = mostCurrent._extra;
final int groupLen9 = group9.getSize()
;int index9 = 0;
;
for (; index9 < groupLen9;index9++){
_h = BA.ObjectToString(group9.Get(index9));
 //BA.debugLineNum = 948;BA.debugLine="If s=h Then";
if ((_s).equals(_h)) { 
 //BA.debugLineNum = 949;BA.debugLine="Bname.Text=pak.GetApplicationLabel(h)";
mostCurrent._bname.setText(BA.ObjectToCharSequence(_pak.GetApplicationLabel(_h)));
 //BA.debugLineNum = 950;BA.debugLine="icon=pak.GetApplicationIcon(h)";
mostCurrent._icon.setObject((android.graphics.drawable.BitmapDrawable)(_pak.GetApplicationIcon(_h)));
 }else {
 //BA.debugLineNum = 952;BA.debugLine="Bname.Text=name1";
mostCurrent._bname.setText(BA.ObjectToCharSequence(mostCurrent._name1));
 //BA.debugLineNum = 953;BA.debugLine="icon.Initialize(LoadBitmap(File.DirAssets,\"ic_";
mostCurrent._icon.Initialize((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"ic_assignment_black_36dp.png").getObject()));
 };
 }
};
 //BA.debugLineNum = 956;BA.debugLine="Dim l1,l2,l3 As Label";
_l1 = new anywheresoftware.b4a.objects.LabelWrapper();
_l2 = new anywheresoftware.b4a.objects.LabelWrapper();
_l3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 957;BA.debugLine="l1.Initialize(\"\")";
_l1.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 958;BA.debugLine="l2.Initialize(\"\")";
_l2.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 959;BA.debugLine="l3.Initialize(\"\")";
_l3.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 960;BA.debugLine="l3.Text=Bpath.Text";
_l3.setText(BA.ObjectToCharSequence(mostCurrent._bpath.getText()));
 //BA.debugLineNum = 961;BA.debugLine="l2.Text=dtime.GetSimple(s)";
_l2.setText(BA.ObjectToCharSequence(mostCurrent._dtime._getsimple(_s)));
 //BA.debugLineNum = 962;BA.debugLine="Dim total As String";
_total = "";
 //BA.debugLineNum = 963;BA.debugLine="total = \"size: \"&FormatFileSize(File.Size(Bpath.t";
_total = "size: "+_formatfilesize((float) (anywheresoftware.b4a.keywords.Common.File.Size(mostCurrent._bpath.getText(),mostCurrent._name1)));
 //BA.debugLineNum = 964;BA.debugLine="Dim Builder As MaterialDialogBuilder";
_builder = new de.amberhome.materialdialogs.MaterialDialogBuilderWrapper();
 //BA.debugLineNum = 965;BA.debugLine="Builder.Initialize(\"bckdia\")";
_builder.Initialize(mostCurrent.activityBA,"bckdia");
 //BA.debugLineNum = 966;BA.debugLine="Builder.Title(\"Backup Info:\").TitleColor(mcl.md_b";
_builder.Title(BA.ObjectToCharSequence("Backup Info:")).TitleColor(mostCurrent._mcl.getmd_black_1000()).Icon((android.graphics.drawable.Drawable)(mostCurrent._icon.getObject())).LimitIconToDefaultSize().Theme(_builder.THEME_LIGHT).Content(BA.ObjectToCharSequence(mostCurrent._bname.getText())).ContentLineSpacing((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1)))).ContentColor(mostCurrent._mcl.getmd_black_1000()).Cancelable(anywheresoftware.b4a.keywords.Common.False).AutoDismiss(anywheresoftware.b4a.keywords.Common.True).PositiveText(BA.ObjectToCharSequence("Ok")).PositiveColor(mostCurrent._mcl.getmd_amber_900()).NeutralText(BA.ObjectToCharSequence("")).NeutralColor(mostCurrent._mcl.getmd_black_1000()).ItemsColor(mostCurrent._mcl.getmd_blue_600()).ItemsGravity(_builder.GRAVITY_START).MaxIconSize((int) (64)).AlwaysCallSingleChoiceCallback().ForceStacking(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 969;BA.debugLine="Builder.Items(Array As String (packName,total,l2";
_builder.Items((java.lang.CharSequence[])(new String[]{mostCurrent._packname,_total,_l2.getText()}));
 //BA.debugLineNum = 972;BA.debugLine="deldia=Builder.Show";
mostCurrent._deldia = _builder.Show();
 //BA.debugLineNum = 973;BA.debugLine="deldia.Show";
mostCurrent._deldia.Show();
 //BA.debugLineNum = 975;BA.debugLine="End Sub";
return "";
}
public static String  _ex_pan2() throws Exception{
String _a1 = "";
String _version = "";
int _sum = 0;
anywheresoftware.b4a.objects.LabelWrapper _extitel = null;
String _s = "";
String _p = "";
de.amberhome.materialdialogs.MaterialDialogBuilderWrapper _builder = null;
 //BA.debugLineNum = 1175;BA.debugLine="Sub ex_pan2";
 //BA.debugLineNum = 1176;BA.debugLine="Dim a1,version As String";
_a1 = "";
_version = "";
 //BA.debugLineNum = 1177;BA.debugLine="Dim sum As Int";
_sum = 0;
 //BA.debugLineNum = 1178;BA.debugLine="Dim extitel As Label";
_extitel = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 1179;BA.debugLine="extitel.Initialize(\"\")";
_extitel.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1180;BA.debugLine="Dim s,p As String";
_s = "";
_p = "";
 //BA.debugLineNum = 1181;BA.debugLine="s=finish.Get(\"0\")";
_s = BA.ObjectToString(mostCurrent._finish.Get((int)(Double.parseDouble("0"))));
 //BA.debugLineNum = 1182;BA.debugLine="p=GetParentPath(GetSourceDir(GetActivitiesInfo(s)";
_p = _getparentpath(_getsourcedir(_getactivitiesinfo(_s)));
 //BA.debugLineNum = 1183;BA.debugLine="packName=s";
mostCurrent._packname = _s;
 //BA.debugLineNum = 1184;BA.debugLine="name1=pak.GetApplicationLabel(s)";
mostCurrent._name1 = _pak.GetApplicationLabel(_s);
 //BA.debugLineNum = 1185;BA.debugLine="version=pak.GetVersionName(s)";
_version = _pak.GetVersionName(_s);
 //BA.debugLineNum = 1186;BA.debugLine="extitel.Text=name1";
_extitel.setText(BA.ObjectToCharSequence(mostCurrent._name1));
 //BA.debugLineNum = 1187;BA.debugLine="extitel.TextSize=11";
_extitel.setTextSize((float) (11));
 //BA.debugLineNum = 1188;BA.debugLine="icon=pak.GetApplicationIcon(s)";
mostCurrent._icon.setObject((android.graphics.drawable.BitmapDrawable)(_pak.GetApplicationIcon(_s)));
 //BA.debugLineNum = 1189;BA.debugLine="dir=datas.GetSimple(\"0\")";
mostCurrent._dir = mostCurrent._datas._getsimple("0");
 //BA.debugLineNum = 1190;BA.debugLine="size=File.Size(GetParentPath(GetSourceDir(GetActi";
_size = (int) (anywheresoftware.b4a.keywords.Common.File.Size(_getparentpath(_getsourcedir(_getactivitiesinfo(BA.ObjectToString(mostCurrent._finish.Get((int)(Double.parseDouble("0"))))))),_getfilename(_getsourcedir(_getactivitiesinfo(BA.ObjectToString(mostCurrent._finish.Get((int)(Double.parseDouble("0")))))))));
 //BA.debugLineNum = 1191;BA.debugLine="Label4.Text=packName&\"->-> \"&name1&\"_\"&version&\".";
mostCurrent._label4.setText(BA.ObjectToCharSequence(mostCurrent._packname+"->-> "+mostCurrent._name1+"_"+_version+".apk ("+_formatfilesize((float) (_size))+") to: "+mostCurrent._dir));
 //BA.debugLineNum = 1192;BA.debugLine="Label4.TextColor=mcl.md_black_1000";
mostCurrent._label4.setTextColor(mostCurrent._mcl.getmd_black_1000());
 //BA.debugLineNum = 1193;BA.debugLine="Label4.TextSize=12";
mostCurrent._label4.setTextSize((float) (12));
 //BA.debugLineNum = 1194;BA.debugLine="Label4.Gravity=Gravity.FILL";
mostCurrent._label4.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 1195;BA.debugLine="Label4.Typeface=superfont";
mostCurrent._label4.setTypeface((android.graphics.Typeface)(mostCurrent._superfont.getObject()));
 //BA.debugLineNum = 1196;BA.debugLine="Dim Builder As MaterialDialogBuilder";
_builder = new de.amberhome.materialdialogs.MaterialDialogBuilderWrapper();
 //BA.debugLineNum = 1197;BA.debugLine="Builder.Initialize(\"Dialog\")";
_builder.Initialize(mostCurrent.activityBA,"Dialog");
 //BA.debugLineNum = 1198;BA.debugLine="Builder.Title(extitel.Text).TitleColor(mcl.md_blu";
_builder.Title(BA.ObjectToCharSequence(_extitel.getText())).TitleColor(mostCurrent._mcl.getmd_blue_A400()).Icon((android.graphics.drawable.Drawable)(mostCurrent._icon.getObject())).LimitIconToDefaultSize().Theme(_builder.THEME_LIGHT).Content(BA.ObjectToCharSequence(mostCurrent._label4.getText())).ContentLineSpacing((float) (1)).ContentColor(mostCurrent._mcl.getmd_black_1000()).Cancelable(anywheresoftware.b4a.keywords.Common.False).AutoDismiss(anywheresoftware.b4a.keywords.Common.True).Build();
 //BA.debugLineNum = 1199;BA.debugLine="a1=File.Size(p,GetFileName(GetSourceDir(GetActivi";
_a1 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.File.Size(_p,_getfilename(_getsourcedir(_getactivitiesinfo(_s)))));
 //BA.debugLineNum = 1200;BA.debugLine="sum=a1/1024/1024";
_sum = (int) ((double)(Double.parseDouble(_a1))/(double)1024/(double)1024);
 //BA.debugLineNum = 1201;BA.debugLine="Builder.progress2(False,sum,True)";
_builder.Progress2(anywheresoftware.b4a.keywords.Common.False,_sum,anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1202;BA.debugLine="pdia=Builder.Show";
mostCurrent._pdia = _builder.Show();
 //BA.debugLineNum = 1203;BA.debugLine="pdia.CurrentProgress=0";
mostCurrent._pdia.setCurrentProgress((int) (0));
 //BA.debugLineNum = 1204;BA.debugLine="tcount=0";
_tcount = (int) (0);
 //BA.debugLineNum = 1205;BA.debugLine="ex.Enabled=True";
_ex.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1206;BA.debugLine="End Sub";
return "";
}
public static String  _ex_start() throws Exception{
de.amberhome.materialdialogs.MaterialDialogBuilderWrapper _builder = null;
int _maxs = 0;
int _i = 0;
 //BA.debugLineNum = 477;BA.debugLine="Sub ex_start";
 //BA.debugLineNum = 478;BA.debugLine="Dim Builder As MaterialDialogBuilder";
_builder = new de.amberhome.materialdialogs.MaterialDialogBuilderWrapper();
 //BA.debugLineNum = 479;BA.debugLine="Dim maxs As Int";
_maxs = 0;
 //BA.debugLineNum = 480;BA.debugLine="dir=datas.GetSimple(\"0\")";
mostCurrent._dir = mostCurrent._datas._getsimple("0");
 //BA.debugLineNum = 481;BA.debugLine="maxs=exalist.Size";
_maxs = mostCurrent._exalist.getSize();
 //BA.debugLineNum = 482;BA.debugLine="For i =0 To exalist.Size-1";
{
final int step5 = 1;
final int limit5 = (int) (mostCurrent._exalist.getSize()-1);
_i = (int) (0) ;
for (;(step5 > 0 && _i <= limit5) || (step5 < 0 && _i >= limit5) ;_i = ((int)(0 + _i + step5))  ) {
 //BA.debugLineNum = 483;BA.debugLine="size=File.Size(GetParentPath(GetSourceDir(GetAct";
_size = (int) (anywheresoftware.b4a.keywords.Common.File.Size(_getparentpath(_getsourcedir(_getactivitiesinfo(BA.ObjectToString(mostCurrent._exalist.Get(_i))))),_getfilename(_getsourcedir(_getactivitiesinfo(BA.ObjectToString(mostCurrent._exalist.Get(_i))))))*1024*10);
 }
};
 //BA.debugLineNum = 485;BA.debugLine="Label4.Text=\"copying to\"&dir&\"(\"&FormatFileSize(s";
mostCurrent._label4.setText(BA.ObjectToCharSequence("copying to"+mostCurrent._dir+"("+_formatfilesize((float) (_size))+")... this will take a while please standby."));
 //BA.debugLineNum = 486;BA.debugLine="icon.Initialize(LoadBitmap(File.DirAssets,\"ic_ass";
mostCurrent._icon.Initialize((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"ic_assignment_black_36dp.png").getObject()));
 //BA.debugLineNum = 487;BA.debugLine="Builder.Initialize(\"exap\")";
_builder.Initialize(mostCurrent.activityBA,"exap");
 //BA.debugLineNum = 488;BA.debugLine="Builder.Title(\"Backup All:\").TitleColor(mcl.md_bl";
_builder.Title(BA.ObjectToCharSequence("Backup All:")).TitleColor(mostCurrent._mcl.getmd_black_1000()).TitleGravity(_builder.GRAVITY_START).Content(BA.ObjectToCharSequence(mostCurrent._label4.getText())).ContentColor(anywheresoftware.b4a.keywords.Common.Colors.DarkGray).Icon((android.graphics.drawable.Drawable)(mostCurrent._icon.getObject())).LimitIconToDefaultSize().Theme(_builder.THEME_LIGHT).ContentLineSpacing((float) (1)).ContentColor(mostCurrent._mcl.getmd_black_1000()).Cancelable(anywheresoftware.b4a.keywords.Common.False).AutoDismiss(anywheresoftware.b4a.keywords.Common.True).Build();
 //BA.debugLineNum = 489;BA.debugLine="Builder.progress(False,maxs)";
_builder.Progress(anywheresoftware.b4a.keywords.Common.False,_maxs);
 //BA.debugLineNum = 491;BA.debugLine="exa.Enabled=True";
_exa.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 492;BA.debugLine="deldia=Builder.Show";
mostCurrent._deldia = _builder.Show();
 //BA.debugLineNum = 493;BA.debugLine="deldia.Show";
mostCurrent._deldia.Show();
 //BA.debugLineNum = 496;BA.debugLine="End Sub";
return "";
}
public static String  _ex_tick() throws Exception{
int _a1 = 0;
int _a2 = 0;
int _sum = 0;
int _it = 0;
String _p = "";
String _s = "";
String _b = "";
String _fname = "";
String _vers = "";
 //BA.debugLineNum = 1207;BA.debugLine="Sub ex_Tick";
 //BA.debugLineNum = 1208;BA.debugLine="Dim a1,a2,sum,it As Int";
_a1 = 0;
_a2 = 0;
_sum = 0;
_it = 0;
 //BA.debugLineNum = 1209;BA.debugLine="Dim p,s,b,fname,vers As String";
_p = "";
_s = "";
_b = "";
_fname = "";
_vers = "";
 //BA.debugLineNum = 1210;BA.debugLine="s=finish.Get(0)";
_s = BA.ObjectToString(mostCurrent._finish.Get((int) (0)));
 //BA.debugLineNum = 1211;BA.debugLine="fname=pak.GetApplicationLabel(s)";
_fname = _pak.GetApplicationLabel(_s);
 //BA.debugLineNum = 1212;BA.debugLine="vers=pak.GetVersionName(s)";
_vers = _pak.GetVersionName(_s);
 //BA.debugLineNum = 1213;BA.debugLine="p=GetParentPath(GetSourceDir(GetActivitiesInfo(s)";
_p = _getparentpath(_getsourcedir(_getactivitiesinfo(_s)));
 //BA.debugLineNum = 1214;BA.debugLine="b=GetFileName(GetSourceDir(GetActivitiesInfo(s)))";
_b = _getfilename(_getsourcedir(_getactivitiesinfo(_s)));
 //BA.debugLineNum = 1215;BA.debugLine="a1=File.Size(p,GetFileName(GetSourceDir(GetActivi";
_a1 = (int) (anywheresoftware.b4a.keywords.Common.File.Size(_p,_getfilename(_getsourcedir(_getactivitiesinfo(_s)))));
 //BA.debugLineNum = 1216;BA.debugLine="sum=a1/1024/1024";
_sum = (int) (_a1/(double)1024/(double)1024);
 //BA.debugLineNum = 1218;BA.debugLine="If Not(pdia.IsShowing) Then";
if (anywheresoftware.b4a.keywords.Common.Not(mostCurrent._pdia.IsShowing())) { 
 //BA.debugLineNum = 1219;BA.debugLine="ex.Enabled = False";
_ex.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1220;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 1222;BA.debugLine="If pdia.CurrentProgress = pdia.MaxProgress-2 Then";
if (mostCurrent._pdia.getCurrentProgress()==mostCurrent._pdia.getMaxProgress()-2) { 
anywheresoftware.b4a.keywords.Common.File.Copy(_p,_b,mostCurrent._dir,_fname+"_"+_vers+".apk");};
 //BA.debugLineNum = 1223;BA.debugLine="If pdia.CurrentProgress = pdia.MaxProgress Then";
if (mostCurrent._pdia.getCurrentProgress()==mostCurrent._pdia.getMaxProgress()) { 
 //BA.debugLineNum = 1224;BA.debugLine="ex.Enabled = False";
_ex.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1225;BA.debugLine="put_data";
_put_data();
 //BA.debugLineNum = 1226;BA.debugLine="pdia.Content = \"Done! Please wait..\"";
mostCurrent._pdia.setContent(BA.ObjectToCharSequence("Done! Please wait.."));
 //BA.debugLineNum = 1229;BA.debugLine="deldia.Dismiss";
mostCurrent._deldia.Dismiss();
 //BA.debugLineNum = 1231;BA.debugLine="StartActivity(swit)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._swit.getObject()));
 //BA.debugLineNum = 1232;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 }else {
 //BA.debugLineNum = 1235;BA.debugLine="pdia.IncrementProgress(1)";
mostCurrent._pdia.IncrementProgress((int) (1));
 };
 //BA.debugLineNum = 1238;BA.debugLine="End Sub";
return "";
}
public static String  _exa_tick() throws Exception{
 //BA.debugLineNum = 498;BA.debugLine="Sub exa_Tick";
 //BA.debugLineNum = 499;BA.debugLine="If Not(deldia.IsShowing) Then";
if (anywheresoftware.b4a.keywords.Common.Not(mostCurrent._deldia.IsShowing())) { 
 //BA.debugLineNum = 500;BA.debugLine="exa.Enabled = False";
_exa.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 501;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 503;BA.debugLine="If deldia.CurrentProgress = deldia.MaxProgress Th";
if (mostCurrent._deldia.getCurrentProgress()==mostCurrent._deldia.getMaxProgress()) { 
 //BA.debugLineNum = 504;BA.debugLine="exa.Enabled = False";
_exa.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 505;BA.debugLine="deldia.Content = \"Done! Please wait..\"";
mostCurrent._deldia.setContent(BA.ObjectToCharSequence("Done! Please wait.."));
 //BA.debugLineNum = 506;BA.debugLine="StartActivity(swit)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._swit.getObject()));
 //BA.debugLineNum = 507;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 508;BA.debugLine="Animator.setanimati(\"slide_from_left\",\"slide_to_";
mostCurrent._animator._setanimati(mostCurrent.activityBA,"slide_from_left","slide_to_left");
 }else {
 //BA.debugLineNum = 510;BA.debugLine="deldia.IncrementProgress(1)";
mostCurrent._deldia.IncrementProgress((int) (1));
 //BA.debugLineNum = 511;BA.debugLine="file_copy";
_file_copy();
 };
 //BA.debugLineNum = 513;BA.debugLine="End Sub";
return "";
}
public static String  _exadia_buttonpressed(de.amberhome.materialdialogs.MaterialDialogWrapper _dialog,String _action) throws Exception{
 //BA.debugLineNum = 467;BA.debugLine="Sub exadia_ButtonPressed (Dialog As MaterialDialog";
 //BA.debugLineNum = 468;BA.debugLine="Select Action";
switch (BA.switchObjectToInt(_action,_dialog.ACTION_POSITIVE,_dialog.ACTION_NEGATIVE,_dialog.ACTION_NEUTRAL)) {
case 0: {
 //BA.debugLineNum = 470;BA.debugLine="ex_start";
_ex_start();
 break; }
case 1: {
 //BA.debugLineNum = 472;BA.debugLine="ToastMessageShow(\"canceled\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("canceled"),anywheresoftware.b4a.keywords.Common.False);
 break; }
case 2: {
 //BA.debugLineNum = 474;BA.debugLine="ToastMessageShow(\"canceled...\",False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("canceled..."),anywheresoftware.b4a.keywords.Common.False);
 break; }
}
;
 //BA.debugLineNum = 476;BA.debugLine="End Sub";
return "";
}
public static String  _expanditem(anywheresoftware.b4a.objects.PanelWrapper _pnl,Object _id) throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _pnldetails = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
anywheresoftware.b4a.objects.LabelWrapper _iiv = null;
anywheresoftware.b4a.objects.LabelWrapper _update = null;
anywheresoftware.b4a.keywords.constants.TypefaceWrapper _cfont = null;
de.amberhome.objects.appcompat.ACButtonWrapper _butt = null;
de.amberhome.objects.appcompat.ACButtonWrapper _butt2 = null;
de.amberhome.objects.appcompat.ACButtonWrapper _butt3 = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _ib = null;
anywheresoftware.b4a.objects.LabelWrapper _ibb = null;
int _i = 0;
 //BA.debugLineNum = 1359;BA.debugLine="Sub ExpandItem(pnl As Panel, ID As Object)";
 //BA.debugLineNum = 1360;BA.debugLine="Dim pnlDetails As Panel, lbl,iiv,update As Label";
_pnldetails = new anywheresoftware.b4a.objects.PanelWrapper();
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_iiv = new anywheresoftware.b4a.objects.LabelWrapper();
_update = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 1361;BA.debugLine="Dim cfont As Typeface=Typeface.LoadFromAssets(\"Op";
_cfont = new anywheresoftware.b4a.keywords.constants.TypefaceWrapper();
_cfont.setObject((android.graphics.Typeface)(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("OpenSans.ttf")));
 //BA.debugLineNum = 1362;BA.debugLine="pnlDetails.Initialize(\"\")";
_pnldetails.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1363;BA.debugLine="pnlDetails.Elevation=5dip";
_pnldetails.setElevation((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 1364;BA.debugLine="lbl.Initialize(\"\")";
_lbl.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1365;BA.debugLine="lbl.TextColor = Colors.DarkGray";
_lbl.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.DarkGray);
 //BA.debugLineNum = 1366;BA.debugLine="lbl.TextSize = 14";
_lbl.setTextSize((float) (14));
 //BA.debugLineNum = 1367;BA.debugLine="lbl.Typeface=cfont";
_lbl.setTypeface((android.graphics.Typeface)(_cfont.getObject()));
 //BA.debugLineNum = 1368;BA.debugLine="iiv.Initialize(\"\")";
_iiv.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1369;BA.debugLine="iiv.TextSize=14";
_iiv.setTextSize((float) (14));
 //BA.debugLineNum = 1370;BA.debugLine="iiv.Typeface=cfont";
_iiv.setTypeface((android.graphics.Typeface)(_cfont.getObject()));
 //BA.debugLineNum = 1371;BA.debugLine="iiv.TextColor=Colors.DarkGray";
_iiv.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.DarkGray);
 //BA.debugLineNum = 1372;BA.debugLine="update.Initialize(\"\")";
_update.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1373;BA.debugLine="update.Typeface=cfont";
_update.setTypeface((android.graphics.Typeface)(_cfont.getObject()));
 //BA.debugLineNum = 1374;BA.debugLine="update.TextColor=mcl.md_blue_600";
_update.setTextColor(mostCurrent._mcl.getmd_blue_600());
 //BA.debugLineNum = 1375;BA.debugLine="update.TextSize=14";
_update.setTextSize((float) (14));
 //BA.debugLineNum = 1377;BA.debugLine="Dim butt,butt2,butt3 As ACButton";
_butt = new de.amberhome.objects.appcompat.ACButtonWrapper();
_butt2 = new de.amberhome.objects.appcompat.ACButtonWrapper();
_butt3 = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 1378;BA.debugLine="butt.Initialize(\"butt\")";
_butt.Initialize(mostCurrent.activityBA,"butt");
 //BA.debugLineNum = 1379;BA.debugLine="butt2.Initialize(\"butt2\")";
_butt2.Initialize(mostCurrent.activityBA,"butt2");
 //BA.debugLineNum = 1380;BA.debugLine="butt3.Initialize(\"butt3\")";
_butt3.Initialize(mostCurrent.activityBA,"butt3");
 //BA.debugLineNum = 1381;BA.debugLine="butt.Enabled=True";
_butt.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1382;BA.debugLine="butt2.Enabled=True";
_butt2.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1383;BA.debugLine="butt3.Enabled=True";
_butt3.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1384;BA.debugLine="butt.Visible=True";
_butt.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1385;BA.debugLine="butt.Typeface=cfont";
_butt.setTypeface((android.graphics.Typeface)(_cfont.getObject()));
 //BA.debugLineNum = 1386;BA.debugLine="butt2.Typeface=cfont";
_butt2.setTypeface((android.graphics.Typeface)(_cfont.getObject()));
 //BA.debugLineNum = 1387;BA.debugLine="butt3.Typeface=cfont";
_butt3.setTypeface((android.graphics.Typeface)(_cfont.getObject()));
 //BA.debugLineNum = 1388;BA.debugLine="butt.TextSize=13";
_butt.setTextSize((float) (13));
 //BA.debugLineNum = 1389;BA.debugLine="butt2.TextSize=13";
_butt2.setTextSize((float) (13));
 //BA.debugLineNum = 1390;BA.debugLine="butt3.TextSize=13";
_butt3.setTextSize((float) (13));
 //BA.debugLineNum = 1391;BA.debugLine="butt.TextColor=mcl.md_white_1000";
_butt.setTextColor(mostCurrent._mcl.getmd_white_1000());
 //BA.debugLineNum = 1392;BA.debugLine="butt2.TextColor=Colors.black";
_butt2.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 1393;BA.debugLine="butt3.TextColor=Colors.black";
_butt3.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 1395;BA.debugLine="butt.Text=\"Backup\"";
_butt.setText(BA.ObjectToCharSequence("Backup"));
 //BA.debugLineNum = 1396;BA.debugLine="butt2.Text=\"Start\"";
_butt2.setText(BA.ObjectToCharSequence("Start"));
 //BA.debugLineNum = 1397;BA.debugLine="butt3.Text=\"Share\"";
_butt3.setText(BA.ObjectToCharSequence("Share"));
 //BA.debugLineNum = 1398;BA.debugLine="butt.ButtonColor=Colors.ARGB(185,25,118,210)";
_butt.setButtonColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (185),(int) (25),(int) (118),(int) (210)));
 //BA.debugLineNum = 1399;BA.debugLine="butt2.ButtonColor=Colors.ARGB(185,25,118,210)";
_butt2.setButtonColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (185),(int) (25),(int) (118),(int) (210)));
 //BA.debugLineNum = 1400;BA.debugLine="butt3.ButtonColor=Colors.ARGB(185,25,118,210)";
_butt3.setButtonColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (185),(int) (25),(int) (118),(int) (210)));
 //BA.debugLineNum = 1401;BA.debugLine="Dim ib As BitmapDrawable";
_ib = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 1402;BA.debugLine="ib.Initialize(TextToBitmap(Chr(0xE192),20))";
_ib.Initialize((android.graphics.Bitmap)(_texttobitmap(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xe192))),(float) (20)).getObject()));
 //BA.debugLineNum = 1403;BA.debugLine="Dim ibb As Label";
_ibb = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 1404;BA.debugLine="ibb.Initialize(\"\")";
_ibb.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1405;BA.debugLine="ibb.TextSize=16";
_ibb.setTextSize((float) (16));
 //BA.debugLineNum = 1406;BA.debugLine="ibb.Typeface=cfont";
_ibb.setTypeface((android.graphics.Typeface)(_cfont.getObject()));
 //BA.debugLineNum = 1407;BA.debugLine="ibb.Gravity=Gravity.LEFT";
_ibb.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.LEFT);
 //BA.debugLineNum = 1408;BA.debugLine="ibb.TextColor=mcl.md_amber_700";
_ibb.setTextColor(mostCurrent._mcl.getmd_amber_700());
 //BA.debugLineNum = 1409;BA.debugLine="For i = 0 To extra.Size-1";
{
final int step48 = 1;
final int limit48 = (int) (mostCurrent._extra.getSize()-1);
_i = (int) (0) ;
for (;(step48 > 0 && _i <= limit48) || (step48 < 0 && _i >= limit48) ;_i = ((int)(0 + _i + step48))  ) {
 //BA.debugLineNum = 1410;BA.debugLine="If dtime.ContainsKey(ID) Then";
if (mostCurrent._dtime._containskey(BA.ObjectToString(_id))) { 
 //BA.debugLineNum = 1411;BA.debugLine="update.Text=\"Date/Time: \"&dtime.GetSimple(ID)";
_update.setText(BA.ObjectToCharSequence("Date/Time: "+mostCurrent._dtime._getsimple(BA.ObjectToString(_id))));
 //BA.debugLineNum = 1412;BA.debugLine="ibb.Text=\"Backup Info:\"";
_ibb.setText(BA.ObjectToCharSequence("Backup Info:"));
 //BA.debugLineNum = 1413;BA.debugLine="lbl.Text=\"size: \"&FormatFileSize(File.Size(GetP";
_lbl.setText(BA.ObjectToCharSequence("size: "+_formatfilesize((float) (anywheresoftware.b4a.keywords.Common.File.Size(_getparentpath(_getsourcedir(_getactivitiesinfo(BA.ObjectToString(_id)))),_getfilename(_getsourcedir(_getactivitiesinfo(BA.ObjectToString(_id)))))))));
 //BA.debugLineNum = 1414;BA.debugLine="iiv.Text=\"SD: \"&datas.GetSimple(\"0\")'GetParentP";
_iiv.setText(BA.ObjectToCharSequence("SD: "+mostCurrent._datas._getsimple("0")));
 }else {
 //BA.debugLineNum = 1416;BA.debugLine="update.Text=\"-/-\"";
_update.setText(BA.ObjectToCharSequence("-/-"));
 //BA.debugLineNum = 1417;BA.debugLine="iiv.Text=\"Folder: -/-\"";
_iiv.setText(BA.ObjectToCharSequence("Folder: -/-"));
 //BA.debugLineNum = 1418;BA.debugLine="ibb.Text=\"No Archived Backup found !\"";
_ibb.setText(BA.ObjectToCharSequence("No Archived Backup found !"));
 //BA.debugLineNum = 1419;BA.debugLine="lbl.Text=\"Size: -/-\"";
_lbl.setText(BA.ObjectToCharSequence("Size: -/-"));
 };
 //BA.debugLineNum = 1421;BA.debugLine="If ID =extra.Get(i) Then";
if ((_id).equals(mostCurrent._extra.Get(_i))) { 
 //BA.debugLineNum = 1422;BA.debugLine="finish.Clear";
mostCurrent._finish.Clear();
 //BA.debugLineNum = 1423;BA.debugLine="finish.Add(ID)";
mostCurrent._finish.Add(_id);
 //BA.debugLineNum = 1424;BA.debugLine="pnlDetails.AddView(ibb, 0dip, 0,pnl.Width-1dip,";
_pnldetails.AddView((android.view.View)(_ibb.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),(int) (0),(int) (_pnl.getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
 //BA.debugLineNum = 1425;BA.debugLine="pnlDetails.AddView(update, 0dip, ibb.Height+1di";
_pnldetails.AddView((android.view.View)(_update.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),(int) (_ibb.getHeight()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (95),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)));
 //BA.debugLineNum = 1426;BA.debugLine="pnlDetails.AddView(lbl, 0dip, update.Height+ibb";
_pnldetails.AddView((android.view.View)(_lbl.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),(int) (_update.getHeight()+_ibb.getHeight()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (95),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)));
 //BA.debugLineNum = 1427;BA.debugLine="pnlDetails.AddView(iiv, 0dip, update.Height+lbl";
_pnldetails.AddView((android.view.View)(_iiv.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),(int) (_update.getHeight()+_lbl.getHeight()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (98),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (55)));
 //BA.debugLineNum = 1428;BA.debugLine="pnlDetails.AddView(butt2, 0dip, iiv.Height+lbl.";
_pnldetails.AddView((android.view.View)(_butt2.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),(int) (_iiv.getHeight()+_lbl.getHeight()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (45)));
 //BA.debugLineNum = 1429;BA.debugLine="pnlDetails.AddView(butt, 90dip, iiv.Height+lbl.";
_pnldetails.AddView((android.view.View)(_butt.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90)),(int) (_iiv.getHeight()+_lbl.getHeight()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (45)));
 //BA.debugLineNum = 1430;BA.debugLine="pnlDetails.AddView(butt3, 180dip, iiv.Height+lb";
_pnldetails.AddView((android.view.View)(_butt3.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (180)),(int) (_iiv.getHeight()+_lbl.getHeight()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (45)));
 };
 //BA.debugLineNum = 1432;BA.debugLine="lst.ExtendItem(ID, pnlDetails, 140dip)";
mostCurrent._lst._extenditem(_id,(anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(_pnldetails.getObject())),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (140)));
 }
};
 //BA.debugLineNum = 1435;BA.debugLine="End Sub";
return "";
}
public static String  _explor_set() throws Exception{
String _ffs = "";
 //BA.debugLineNum = 729;BA.debugLine="Sub explor_set";
 //BA.debugLineNum = 730;BA.debugLine="svList.SetVisibleAnimated(400,False)";
mostCurrent._svlist.SetVisibleAnimated((int) (400),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 731;BA.debugLine="dir =datas.GetSimple(\"0\")";
mostCurrent._dir = mostCurrent._datas._getsimple("0");
 //BA.debugLineNum = 732;BA.debugLine="ff.Initialize(Activity,dir,\".apk\", True, False, \"";
mostCurrent._ff._initialize(mostCurrent.activityBA,mostCurrent._activity,mostCurrent._dir,".apk",anywheresoftware.b4a.keywords.Common.True,anywheresoftware.b4a.keywords.Common.False,"Close");
 //BA.debugLineNum = 733;BA.debugLine="ff.Explorer2(False)";
mostCurrent._ff._explorer2(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 734;BA.debugLine="ff.FastScrollEnabled=True";
mostCurrent._ff._fastscrollenabled = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 735;BA.debugLine="Dim ffs As String";
_ffs = "";
 //BA.debugLineNum = 736;BA.debugLine="If Not (ff.Selection.Canceled Or ff.Selection.Cho";
if (anywheresoftware.b4a.keywords.Common.Not(mostCurrent._ff._selection.Canceled || (mostCurrent._ff._selection.ChosenFile).equals(""))) { 
 //BA.debugLineNum = 737;BA.debugLine="ffs= ff.Selection.ChosenFile.SubString2(0,ff.Sel";
_ffs = mostCurrent._ff._selection.ChosenFile.substring((int) (0),(int) (mostCurrent._ff._selection.ChosenFile.length()-4));
 //BA.debugLineNum = 738;BA.debugLine="finish.Clear";
mostCurrent._finish.Clear();
 //BA.debugLineNum = 739;BA.debugLine="finish.Add(ffs)";
mostCurrent._finish.Add((Object)(_ffs));
 //BA.debugLineNum = 740;BA.debugLine="dataliste.Clear";
mostCurrent._dataliste.Clear();
 //BA.debugLineNum = 741;BA.debugLine="dataliste.AddTwoLines2(ff.Selection.ChosenFile,f";
mostCurrent._dataliste.AddTwoLines2(BA.ObjectToCharSequence(mostCurrent._ff._selection.ChosenFile),BA.ObjectToCharSequence(mostCurrent._ff._selection.ChosenPath),(Object)("0"));
 //BA.debugLineNum = 742;BA.debugLine="dataliste.AddTwoLines2(\"Backup Folder:\",datas.Ge";
mostCurrent._dataliste.AddTwoLines2(BA.ObjectToCharSequence("Backup Folder:"),BA.ObjectToCharSequence(mostCurrent._datas._getsimple("0")),(Object)("1"));
 //BA.debugLineNum = 743;BA.debugLine="dataliste.AddTwoLines2(\"Last Backup:\",dtime.GetS";
mostCurrent._dataliste.AddTwoLines2(BA.ObjectToCharSequence("Last Backup:"),BA.ObjectToCharSequence(mostCurrent._dtime._getsimple(_ffs)),(Object)("2"));
 //BA.debugLineNum = 744;BA.debugLine="ex_pan";
_ex_pan();
 }else {
 //BA.debugLineNum = 746;BA.debugLine="svList.SetVisibleAnimated(400,True)";
mostCurrent._svlist.SetVisibleAnimated((int) (400),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 747;BA.debugLine="ToastMessageShow(\"closed\",False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("closed"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 749;BA.debugLine="End Sub";
return "";
}
public static String  _fc_newdata(byte[] _buffer) throws Exception{
 //BA.debugLineNum = 542;BA.debugLine="Sub fc_NewData (Buffer() As Byte)";
 //BA.debugLineNum = 544;BA.debugLine="End Sub";
return "";
}
public static String  _fc_newstream(String _dir1,String _filename) throws Exception{
 //BA.debugLineNum = 545;BA.debugLine="Sub fc_NewStream (dir1 As String, FileName As Stri";
 //BA.debugLineNum = 549;BA.debugLine="File.Copy(dir1,FileName,dir1,FileName&\".apk\")";
anywheresoftware.b4a.keywords.Common.File.Copy(_dir1,_filename,_dir1,_filename+".apk");
 //BA.debugLineNum = 550;BA.debugLine="FileName=FileName&\".apk\"";
_filename = _filename+".apk";
 //BA.debugLineNum = 551;BA.debugLine="Log(FileName)";
anywheresoftware.b4a.keywords.Common.Log(_filename);
 //BA.debugLineNum = 552;BA.debugLine="End Sub";
return "";
}
public static String  _file_check() throws Exception{
String _fname = "";
String _version = "";
String _g = "";
 //BA.debugLineNum = 1082;BA.debugLine="Sub file_check";
 //BA.debugLineNum = 1083;BA.debugLine="Dim fname,version As String";
_fname = "";
_version = "";
 //BA.debugLineNum = 1084;BA.debugLine="dir=datas.GetSimple(\"0\")";
mostCurrent._dir = mostCurrent._datas._getsimple("0");
 //BA.debugLineNum = 1085;BA.debugLine="For Each g As String In extra";
{
final anywheresoftware.b4a.BA.IterableList group3 = mostCurrent._extra;
final int groupLen3 = group3.getSize()
;int index3 = 0;
;
for (; index3 < groupLen3;index3++){
_g = BA.ObjectToString(group3.Get(index3));
 //BA.debugLineNum = 1086;BA.debugLine="fname=pak.GetApplicationLabel(g)";
_fname = _pak.GetApplicationLabel(_g);
 //BA.debugLineNum = 1087;BA.debugLine="version=pak.GetVersionName(g)";
_version = _pak.GetVersionName(_g);
 //BA.debugLineNum = 1088;BA.debugLine="If File.Exists(dir,fname&\"_\"&version&\".apk\") Then";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._dir,_fname+"_"+_version+".apk")) { 
 //BA.debugLineNum = 1089;BA.debugLine="If Not (dtime.ContainsKey(g)) Then";
if (anywheresoftware.b4a.keywords.Common.Not(mostCurrent._dtime._containskey(_g))) { 
 //BA.debugLineNum = 1090;BA.debugLine="dtime.PutSimple(g,date&\" - \"&time)";
mostCurrent._dtime._putsimple(_g,(Object)(_date+" - "+_time));
 //BA.debugLineNum = 1091;BA.debugLine="Log(g&\" added\")";
anywheresoftware.b4a.keywords.Common.Log(_g+" added");
 //BA.debugLineNum = 1092;BA.debugLine="cbut.Checked=True";
mostCurrent._cbut.setChecked(anywheresoftware.b4a.keywords.Common.True);
 };
 };
 //BA.debugLineNum = 1095;BA.debugLine="If Not(File.Exists(dir,fname&\"_\"&version&\".apk\")";
if (anywheresoftware.b4a.keywords.Common.Not(anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._dir,_fname+"_"+_version+".apk"))) { 
 //BA.debugLineNum = 1096;BA.debugLine="If dtime.ContainsKey(g) Then";
if (mostCurrent._dtime._containskey(_g)) { 
 //BA.debugLineNum = 1097;BA.debugLine="dtime.Remove(g)";
mostCurrent._dtime._remove(_g);
 //BA.debugLineNum = 1098;BA.debugLine="Log(g&\" removed\")";
anywheresoftware.b4a.keywords.Common.Log(_g+" removed");
 //BA.debugLineNum = 1099;BA.debugLine="cbut.Checked=False";
mostCurrent._cbut.setChecked(anywheresoftware.b4a.keywords.Common.False);
 };
 };
 }
};
 //BA.debugLineNum = 1103;BA.debugLine="End Sub";
return "";
}
public static String  _file_copy() throws Exception{
int _a1 = 0;
int _a2 = 0;
int _sum = 0;
int _it = 0;
String _p = "";
String _s = "";
String _b = "";
String _fname = "";
String _vers = "";
byte[] _buf = null;
anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper _out = null;
anywheresoftware.b4a.objects.streams.File.InputStreamWrapper _inn = null;
int _h = 0;
 //BA.debugLineNum = 515;BA.debugLine="Sub file_copy";
 //BA.debugLineNum = 517;BA.debugLine="Dim a1,a2,sum,it As Int";
_a1 = 0;
_a2 = 0;
_sum = 0;
_it = 0;
 //BA.debugLineNum = 518;BA.debugLine="Dim p,s,b,fname,vers As String";
_p = "";
_s = "";
_b = "";
_fname = "";
_vers = "";
 //BA.debugLineNum = 519;BA.debugLine="Dim buf() As Byte";
_buf = new byte[(int) (0)];
;
 //BA.debugLineNum = 520;BA.debugLine="Dim out As OutputStream";
_out = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
 //BA.debugLineNum = 521;BA.debugLine="out.InitializeToBytesArray(0)";
_out.InitializeToBytesArray((int) (0));
 //BA.debugLineNum = 522;BA.debugLine="Dim inn As InputStream";
_inn = new anywheresoftware.b4a.objects.streams.File.InputStreamWrapper();
 //BA.debugLineNum = 523;BA.debugLine="For  h = 0 To  exalist.Size-1";
{
final int step7 = 1;
final int limit7 = (int) (mostCurrent._exalist.getSize()-1);
_h = (int) (0) ;
for (;(step7 > 0 && _h <= limit7) || (step7 < 0 && _h >= limit7) ;_h = ((int)(0 + _h + step7))  ) {
 //BA.debugLineNum = 524;BA.debugLine="dtime.PutSimple(exalist.Get(h),date&\" - \"&time)";
mostCurrent._dtime._putsimple(BA.ObjectToString(mostCurrent._exalist.Get(_h)),(Object)(_date+" - "+_time));
 //BA.debugLineNum = 525;BA.debugLine="s=exalist.Get(h)";
_s = BA.ObjectToString(mostCurrent._exalist.Get(_h));
 //BA.debugLineNum = 526;BA.debugLine="fname=pak.GetApplicationLabel(s)";
_fname = _pak.GetApplicationLabel(_s);
 //BA.debugLineNum = 527;BA.debugLine="vers=pak.GetVersionName(s)";
_vers = _pak.GetVersionName(_s);
 //BA.debugLineNum = 528;BA.debugLine="p=GetParentPath(GetSourceDir(GetActivitiesInfo(s";
_p = _getparentpath(_getsourcedir(_getactivitiesinfo(_s)));
 //BA.debugLineNum = 529;BA.debugLine="b=GetFileName(GetSourceDir(GetActivitiesInfo(s))";
_b = _getfilename(_getsourcedir(_getactivitiesinfo(_s)));
 //BA.debugLineNum = 530;BA.debugLine="dir=datas.GetSimple(\"0\")";
mostCurrent._dir = mostCurrent._datas._getsimple("0");
 //BA.debugLineNum = 531;BA.debugLine="size= File.Size(GetParentPath(GetSourceDir(GetAc";
_size = (int) (anywheresoftware.b4a.keywords.Common.File.Size(_getparentpath(_getsourcedir(_getactivitiesinfo(BA.ObjectToString(mostCurrent._exalist.Get(_h))))),_getfilename(_getsourcedir(_getactivitiesinfo(BA.ObjectToString(mostCurrent._exalist.Get(_h)))))));
 //BA.debugLineNum = 532;BA.debugLine="If File.Exists(dir,fname&\"_\"&vers&\".apk\") Then";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._dir,_fname+"_"+_vers+".apk")) { 
 //BA.debugLineNum = 533;BA.debugLine="deldia.Content=\"file exist\"&fname";
mostCurrent._deldia.setContent(BA.ObjectToCharSequence("file exist"+_fname));
 }else {
 //BA.debugLineNum = 535;BA.debugLine="dtime.DeleteAll";
mostCurrent._dtime._deleteall();
 //BA.debugLineNum = 536;BA.debugLine="File.Copy(p,b,dir,fname&\"_\"&vers&\".apk\")";
anywheresoftware.b4a.keywords.Common.File.Copy(_p,_b,mostCurrent._dir,_fname+"_"+_vers+".apk");
 //BA.debugLineNum = 538;BA.debugLine="deldia.Content=\"copying...\"&fname&\"_\"&vers&\".ap";
mostCurrent._deldia.setContent(BA.ObjectToCharSequence("copying..."+_fname+"_"+_vers+".apk"));
 };
 }
};
 //BA.debugLineNum = 541;BA.debugLine="End Sub";
return "";
}
public static String  _fold_set() throws Exception{
 //BA.debugLineNum = 864;BA.debugLine="Sub fold_set";
 //BA.debugLineNum = 865;BA.debugLine="explor_set";
_explor_set();
 //BA.debugLineNum = 867;BA.debugLine="End Sub";
return "";
}
public static String  _formatfilesize(float _bytes) throws Exception{
String[] _unit = null;
double _po = 0;
double _si = 0;
int _i = 0;
 //BA.debugLineNum = 1495;BA.debugLine="Sub FormatFileSize(Bytes As Float) As String";
 //BA.debugLineNum = 1497;BA.debugLine="Private Unit() As String = Array As String(\" Byte";
_unit = new String[]{" Byte"," KB"," MB"," GB"," TB"," PB"," EB"," ZB"," YB"};
 //BA.debugLineNum = 1499;BA.debugLine="If Bytes = 0 Then";
if (_bytes==0) { 
 //BA.debugLineNum = 1500;BA.debugLine="Return \"0 Bytes\"";
if (true) return "0 Bytes";
 }else {
 //BA.debugLineNum = 1502;BA.debugLine="Private Po, Si As Double";
_po = 0;
_si = 0;
 //BA.debugLineNum = 1503;BA.debugLine="Private I As Int";
_i = 0;
 //BA.debugLineNum = 1504;BA.debugLine="Bytes = Abs(Bytes)";
_bytes = (float) (anywheresoftware.b4a.keywords.Common.Abs(_bytes));
 //BA.debugLineNum = 1505;BA.debugLine="I = Floor(Logarithm(Bytes, 1024))";
_i = (int) (anywheresoftware.b4a.keywords.Common.Floor(anywheresoftware.b4a.keywords.Common.Logarithm(_bytes,1024)));
 //BA.debugLineNum = 1506;BA.debugLine="Po = Power(1024, I)";
_po = anywheresoftware.b4a.keywords.Common.Power(1024,_i);
 //BA.debugLineNum = 1507;BA.debugLine="Si = Bytes / Po";
_si = _bytes/(double)_po;
 //BA.debugLineNum = 1508;BA.debugLine="Return NumberFormat(Si, 1, 2) & Unit(I)";
if (true) return anywheresoftware.b4a.keywords.Common.NumberFormat(_si,(int) (1),(int) (2))+_unit[_i];
 };
 //BA.debugLineNum = 1510;BA.debugLine="End Sub";
return "";
}
public static Object  _get_respath(String _package) throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
 //BA.debugLineNum = 1553;BA.debugLine="Sub get_respath(package As String) As Object";
 //BA.debugLineNum = 1554;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 1555;BA.debugLine="r.Target = r.GetContext";
_r.Target = (Object)(_r.GetContext(processBA));
 //BA.debugLineNum = 1556;BA.debugLine="r.Target = r.RunMethod(\"getPackageManager\")";
_r.Target = _r.RunMethod("getPackageManager");
 //BA.debugLineNum = 1557;BA.debugLine="r.Target = r.RunMethod3(\"getApplicationInfo\", pac";
_r.Target = _r.RunMethod3("getApplicationInfo",_package,"java.lang.String",BA.NumberToString(0x00000001),"java.lang.int");
 //BA.debugLineNum = 1558;BA.debugLine="Return r.GetField(\"dataDir\")";
if (true) return _r.GetField("dataDir");
 //BA.debugLineNum = 1559;BA.debugLine="End Sub";
return null;
}
public static Object  _getactivitiesinfo(String _package) throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
 //BA.debugLineNum = 1535;BA.debugLine="Sub GetActivitiesInfo(package As String) As Object";
 //BA.debugLineNum = 1536;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 1537;BA.debugLine="r.Target = r.GetContext";
_r.Target = (Object)(_r.GetContext(processBA));
 //BA.debugLineNum = 1538;BA.debugLine="r.Target = r.RunMethod(\"getPackageManager\")";
_r.Target = _r.RunMethod("getPackageManager");
 //BA.debugLineNum = 1539;BA.debugLine="r.Target = r.RunMethod3(\"getPackageInfo\", package";
_r.Target = _r.RunMethod3("getPackageInfo",_package,"java.lang.String",BA.NumberToString(0x00000001),"java.lang.int");
 //BA.debugLineNum = 1540;BA.debugLine="Return r.GetField(\"applicationInfo\")";
if (true) return _r.GetField("applicationInfo");
 //BA.debugLineNum = 1541;BA.debugLine="End Sub";
return null;
}
public static String  _getfilename(String _fullpath) throws Exception{
 //BA.debugLineNum = 1512;BA.debugLine="Sub GetFileName(FullPath As String) As String";
 //BA.debugLineNum = 1513;BA.debugLine="Return FullPath.SubString(FullPath.LastIndexOf(\"/";
if (true) return _fullpath.substring((int) (_fullpath.lastIndexOf("/")+1));
 //BA.debugLineNum = 1514;BA.debugLine="End Sub";
return "";
}
public static String  _getparentpath(String _path) throws Exception{
String _path1 = "";
 //BA.debugLineNum = 1517;BA.debugLine="Sub GetParentPath(path As String) As String";
 //BA.debugLineNum = 1518;BA.debugLine="Dim Path1 As String";
_path1 = "";
 //BA.debugLineNum = 1519;BA.debugLine="If path = \"/\" Then";
if ((_path).equals("/")) { 
 //BA.debugLineNum = 1520;BA.debugLine="Return \"/\"";
if (true) return "/";
 };
 //BA.debugLineNum = 1522;BA.debugLine="L = path.LastIndexOf(\"/\")";
mostCurrent._l = BA.NumberToString(_path.lastIndexOf("/"));
 //BA.debugLineNum = 1523;BA.debugLine="If L = path.Length - 1 Then";
if ((mostCurrent._l).equals(BA.NumberToString(_path.length()-1))) { 
 //BA.debugLineNum = 1524;BA.debugLine="Path1 = path.SubString2(0,L)";
_path1 = _path.substring((int) (0),(int)(Double.parseDouble(mostCurrent._l)));
 }else {
 //BA.debugLineNum = 1526;BA.debugLine="Path1 = path";
_path1 = _path;
 };
 //BA.debugLineNum = 1528;BA.debugLine="L = path.LastIndexOf(\"/\")";
mostCurrent._l = BA.NumberToString(_path.lastIndexOf("/"));
 //BA.debugLineNum = 1529;BA.debugLine="If L = 0 Then";
if ((mostCurrent._l).equals(BA.NumberToString(0))) { 
 //BA.debugLineNum = 1530;BA.debugLine="L = 1";
mostCurrent._l = BA.NumberToString(1);
 };
 //BA.debugLineNum = 1532;BA.debugLine="Return Path1.SubString2(0,L)";
if (true) return _path1.substring((int) (0),(int)(Double.parseDouble(mostCurrent._l)));
 //BA.debugLineNum = 1533;BA.debugLine="End Sub";
return "";
}
public static String  _getsourcedir(Object _appinfo) throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
 //BA.debugLineNum = 1543;BA.debugLine="Sub GetSourceDir(AppInfo As Object) As String";
 //BA.debugLineNum = 1544;BA.debugLine="Try";
try { //BA.debugLineNum = 1545;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 1546;BA.debugLine="r.Target = AppInfo";
_r.Target = _appinfo;
 //BA.debugLineNum = 1547;BA.debugLine="Return r.GetField(\"sourceDir\")";
if (true) return BA.ObjectToString(_r.GetField("sourceDir"));
 } 
       catch (Exception e6) {
			processBA.setLastException(e6); //BA.debugLineNum = 1549;BA.debugLine="Return \"\"";
if (true) return "";
 };
 //BA.debugLineNum = 1551;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 31;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 32;BA.debugLine="Dim dir As String";
mostCurrent._dir = "";
 //BA.debugLineNum = 33;BA.debugLine="Private applist,extra,extra2,finish,datal As List";
mostCurrent._applist = new anywheresoftware.b4a.objects.collections.List();
mostCurrent._extra = new anywheresoftware.b4a.objects.collections.List();
mostCurrent._extra2 = new anywheresoftware.b4a.objects.collections.List();
mostCurrent._finish = new anywheresoftware.b4a.objects.collections.List();
mostCurrent._datal = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 34;BA.debugLine="Dim cbut As CheckBox";
mostCurrent._cbut = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Dim ABHelper As ACActionBar";
mostCurrent._abhelper = new de.amberhome.objects.appcompat.ACActionBar();
 //BA.debugLineNum = 36;BA.debugLine="Dim AC As AppCompat";
mostCurrent._ac = new de.amberhome.objects.appcompat.AppCompatBase();
 //BA.debugLineNum = 37;BA.debugLine="Dim args(1) As Object";
mostCurrent._args = new Object[(int) (1)];
{
int d0 = mostCurrent._args.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._args[i0] = new Object();
}
}
;
 //BA.debugLineNum = 38;BA.debugLine="Dim tcount As Int";
_tcount = 0;
 //BA.debugLineNum = 39;BA.debugLine="Dim Obj1, Obj2, Obj3,obj4,obj5,obj6 As Reflector";
mostCurrent._obj1 = new anywheresoftware.b4a.agraham.reflection.Reflection();
mostCurrent._obj2 = new anywheresoftware.b4a.agraham.reflection.Reflection();
mostCurrent._obj3 = new anywheresoftware.b4a.agraham.reflection.Reflection();
mostCurrent._obj4 = new anywheresoftware.b4a.agraham.reflection.Reflection();
mostCurrent._obj5 = new anywheresoftware.b4a.agraham.reflection.Reflection();
mostCurrent._obj6 = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 40;BA.debugLine="Dim size,flags,csize,sdk,itime As Int";
_size = 0;
_flags = 0;
_csize = 0;
_sdk = 0;
_itime = 0;
 //BA.debugLineNum = 41;BA.debugLine="Private name1,l,Types(1),packName,idt,apath As St";
mostCurrent._name1 = "";
mostCurrent._l = "";
mostCurrent._types = new String[(int) (1)];
java.util.Arrays.fill(mostCurrent._types,"");
mostCurrent._packname = "";
mostCurrent._idt = "";
mostCurrent._apath = "";
 //BA.debugLineNum = 42;BA.debugLine="Private icon,icon2 As BitmapDrawable";
mostCurrent._icon = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
mostCurrent._icon2 = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 43;BA.debugLine="Private mcl As MaterialColors";
mostCurrent._mcl = new com.tchart.materialcolors.MaterialColors();
 //BA.debugLineNum = 44;BA.debugLine="Private iv1,iv2 As ImageView";
mostCurrent._iv1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
mostCurrent._iv2 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Private Label5 As Label";
mostCurrent._label5 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 46;BA.debugLine="Private Bname As Label";
mostCurrent._bname = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Dim bdia As BetterDialogs";
mostCurrent._bdia = new flm.b4a.betterdialogs.BetterDialogs();
 //BA.debugLineNum = 48;BA.debugLine="Dim dias As CustomDialog2";
mostCurrent._dias = new anywheresoftware.b4a.agraham.dialogs.InputDialog.CustomDialog2();
 //BA.debugLineNum = 49;BA.debugLine="Private Label4 As Label";
mostCurrent._label4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 50;BA.debugLine="Private Bpath As Label";
mostCurrent._bpath = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 51;BA.debugLine="Private Bcontent As Label";
mostCurrent._bcontent = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 52;BA.debugLine="Dim ie As INTENTID";
mostCurrent._ie = new b4a.example.intentid();
 //BA.debugLineNum = 53;BA.debugLine="Private datas,dtime,bcount,cop As KeyValueStore";
mostCurrent._datas = new com.apkbackup.de.keyvaluestore();
mostCurrent._dtime = new com.apkbackup.de.keyvaluestore();
mostCurrent._bcount = new com.apkbackup.de.keyvaluestore();
mostCurrent._cop = new com.apkbackup.de.keyvaluestore();
 //BA.debugLineNum = 55;BA.debugLine="Private Bbpanel As Panel";
mostCurrent._bbpanel = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 56;BA.debugLine="Private dataliste As ListView";
mostCurrent._dataliste = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 57;BA.debugLine="Dim bdia2 As CustomDialog2";
mostCurrent._bdia2 = new anywheresoftware.b4a.agraham.dialogs.InputDialog.CustomDialog2();
 //BA.debugLineNum = 58;BA.debugLine="Dim dia3 As MaterialDialogsManager";
mostCurrent._dia3 = new de.amberhome.materialdialogs.MaterialDialogsActivity.MDManager();
 //BA.debugLineNum = 59;BA.debugLine="Dim svList As ScrollView";
mostCurrent._svlist = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 60;BA.debugLine="Dim lst As ClsPaklist";
mostCurrent._lst = new com.apkbackup.de.clspaklist();
 //BA.debugLineNum = 61;BA.debugLine="Private pdia As MaterialDialog";
mostCurrent._pdia = new de.amberhome.materialdialogs.MaterialDialogWrapper();
 //BA.debugLineNum = 62;BA.debugLine="Dim pn As List";
mostCurrent._pn = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 63;BA.debugLine="Dim sd As MLfiles";
mostCurrent._sd = new MLfiles.Fileslib.MLfiles();
 //BA.debugLineNum = 64;BA.debugLine="Dim ff As ClsExplorer";
mostCurrent._ff = new com.apkbackup.de.clsexplorer();
 //BA.debugLineNum = 65;BA.debugLine="Dim mb As ACFlatButton";
mostCurrent._mb = new de.amberhome.objects.appcompat.ACFlatButtonWrapper();
 //BA.debugLineNum = 66;BA.debugLine="Private asr As AHSwipeToRefresh";
mostCurrent._asr = new de.amberhome.strefresh.AHSwipeToRefresh();
 //BA.debugLineNum = 67;BA.debugLine="Dim ref As Button";
mostCurrent._ref = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 68;BA.debugLine="Dim Pid As List";
mostCurrent._pid = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 69;BA.debugLine="Private toolbar As ACToolBarLight";
mostCurrent._toolbar = new de.amberhome.objects.appcompat.ACToolbarLightWrapper();
 //BA.debugLineNum = 70;BA.debugLine="Dim superfont As Typeface";
mostCurrent._superfont = new anywheresoftware.b4a.keywords.constants.TypefaceWrapper();
 //BA.debugLineNum = 71;BA.debugLine="Dim deldia As MaterialDialog";
mostCurrent._deldia = new de.amberhome.materialdialogs.MaterialDialogWrapper();
 //BA.debugLineNum = 72;BA.debugLine="Dim chk As ACFlatButton";
mostCurrent._chk = new de.amberhome.objects.appcompat.ACFlatButtonWrapper();
 //BA.debugLineNum = 73;BA.debugLine="Dim lvMenu As ListView";
mostCurrent._lvmenu = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 74;BA.debugLine="Dim lblInfo As Label";
mostCurrent._lblinfo = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 75;BA.debugLine="Dim menulist As List";
mostCurrent._menulist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 76;BA.debugLine="Dim lvMenu As ListView";
mostCurrent._lvmenu = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 77;BA.debugLine="Private sm As SlidingMenu";
mostCurrent._sm = new anywheresoftware.b4a.objects.SlidingMenuWrapper();
 //BA.debugLineNum = 78;BA.debugLine="Private rvmenu As ListView";
mostCurrent._rvmenu = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 79;BA.debugLine="Private rftext As Label";
mostCurrent._rftext = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 80;BA.debugLine="Dim rightlv As ListView";
mostCurrent._rightlv = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 81;BA.debugLine="Private fbox As Spinner";
mostCurrent._fbox = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 82;BA.debugLine="Private otb As ACToolBarLight";
mostCurrent._otb = new de.amberhome.objects.appcompat.ACToolbarLightWrapper();
 //BA.debugLineNum = 83;BA.debugLine="Private Panel1 As Panel";
mostCurrent._panel1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 84;BA.debugLine="Private btb As ACToolBarLight";
mostCurrent._btb = new de.amberhome.objects.appcompat.ACToolbarLightWrapper();
 //BA.debugLineNum = 85;BA.debugLine="Private acp As ACSpinner";
mostCurrent._acp = new de.amberhome.objects.appcompat.ACSpinnerWrapper();
 //BA.debugLineNum = 86;BA.debugLine="Dim df As MaterialDialog";
mostCurrent._df = new de.amberhome.materialdialogs.MaterialDialogWrapper();
 //BA.debugLineNum = 87;BA.debugLine="Private acs As bcCheckBox";
mostCurrent._acs = new b4a.community.donmanfred.widget.bcCheckBox();
 //BA.debugLineNum = 88;BA.debugLine="Dim exalist As List";
mostCurrent._exalist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 89;BA.debugLine="Dim storage As env";
mostCurrent._storage = new de.donmanfred.storage();
 //BA.debugLineNum = 90;BA.debugLine="Dim paths As Map";
mostCurrent._paths = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 91;BA.debugLine="Private astream As AsyncStreams";
mostCurrent._astream = new anywheresoftware.b4a.randomaccessfile.AsyncStreams();
 //BA.debugLineNum = 93;BA.debugLine="End Sub";
return "";
}
public static String  _idia_buttonpressed(de.amberhome.materialdialogs.MaterialDialogWrapper _dialog,String _action) throws Exception{
 //BA.debugLineNum = 925;BA.debugLine="Sub idia_ButtonPressed (Dialog As MaterialDialog,";
 //BA.debugLineNum = 926;BA.debugLine="Select Action";
switch (BA.switchObjectToInt(_action,_dialog.ACTION_POSITIVE,_dialog.ACTION_NEGATIVE,_dialog.ACTION_NEUTRAL)) {
case 0: {
 break; }
case 1: {
 break; }
case 2: {
 //BA.debugLineNum = 933;BA.debugLine="Dialog.Dismiss";
_dialog.Dismiss();
 break; }
}
;
 //BA.debugLineNum = 936;BA.debugLine="End Sub";
return "";
}
public static String  _idia_simpleitemselected(de.amberhome.materialdialogs.MaterialDialogWrapper _dialog,int _position,de.amberhome.materialdialogs.MaterialSimpleListItemWrapper _item) throws Exception{
 //BA.debugLineNum = 900;BA.debugLine="Sub idia_SimpleItemSelected (Dialog As MaterialDia";
 //BA.debugLineNum = 901;BA.debugLine="If Position=Item.id Then";
if (_position==_item.getId()) { 
 //BA.debugLineNum = 902;BA.debugLine="finish.Clear";
mostCurrent._finish.Clear();
 //BA.debugLineNum = 903;BA.debugLine="finish.Add(Item.Content.SubString2(0,Item.Conte";
mostCurrent._finish.Add((Object)(_item.getContent().substring((int) (0),(int) (_item.getContent().length()-4))));
 //BA.debugLineNum = 904;BA.debugLine="ToastMessageShow(Item.Content.SubString2(0,Item.";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(_item.getContent().substring((int) (0),(int) (_item.getContent().length()-4))),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 906;BA.debugLine="End Sub";
return "";
}
public static String  _ini_extract() throws Exception{
String _s = "";
String _b = "";
String _p = "";
String _d1 = "";
String _d2 = "";
String _master = "";
 //BA.debugLineNum = 1008;BA.debugLine="Sub ini_extract";
 //BA.debugLineNum = 1009;BA.debugLine="Dim s As String = finish.Get(0)";
_s = BA.ObjectToString(mostCurrent._finish.Get((int) (0)));
 //BA.debugLineNum = 1011;BA.debugLine="Dim b As String = GetFileName(GetSourceDir(GetAct";
_b = _getfilename(_getsourcedir(_getactivitiesinfo(BA.ObjectToString(mostCurrent._finish.Get((int) (0))))));
 //BA.debugLineNum = 1012;BA.debugLine="Dim p As String =  GetParentPath(get_respath(fini";
_p = _getparentpath(BA.ObjectToString(_get_respath(BA.ObjectToString(mostCurrent._finish.Get((int) (0))))));
 //BA.debugLineNum = 1014;BA.debugLine="name1=b";
mostCurrent._name1 = _b;
 //BA.debugLineNum = 1015;BA.debugLine="Label4.text=name1";
mostCurrent._label4.setText(BA.ObjectToCharSequence(mostCurrent._name1));
 //BA.debugLineNum = 1016;BA.debugLine="Dim d1,d2,master As String";
_d1 = "";
_d2 = "";
_master = "";
 //BA.debugLineNum = 1017;BA.debugLine="d1=File.DirRootExternal&\"/APEX\"";
_d1 = anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/APEX";
 //BA.debugLineNum = 1018;BA.debugLine="d2=datas.GetSimple(\"0\")";
_d2 = mostCurrent._datas._getsimple("0");
 //BA.debugLineNum = 1019;BA.debugLine="If datas.ContainsKey(\"0\") Then";
if (mostCurrent._datas._containskey("0")) { 
 //BA.debugLineNum = 1020;BA.debugLine="master=d2";
_master = _d2;
 }else {
 //BA.debugLineNum = 1022;BA.debugLine="master=d1";
_master = _d1;
 };
 //BA.debugLineNum = 1025;BA.debugLine="If File.Exists(master,s&\".apk\") Then";
if (anywheresoftware.b4a.keywords.Common.File.Exists(_master,_s+".apk")) { 
 //BA.debugLineNum = 1026;BA.debugLine="put_data";
_put_data();
 //BA.debugLineNum = 1027;BA.debugLine="int_folder";
_int_folder();
 }else {
 //BA.debugLineNum = 1031;BA.debugLine="cop.DeleteAll";
mostCurrent._cop._deleteall();
 //BA.debugLineNum = 1032;BA.debugLine="cop.PutSimple(\"1\",p)";
mostCurrent._cop._putsimple("1",(Object)(_p));
 //BA.debugLineNum = 1033;BA.debugLine="cop.PutSimple(\"2\",s)";
mostCurrent._cop._putsimple("2",(Object)(_s));
 //BA.debugLineNum = 1034;BA.debugLine="cop.PutSimple(\"3\",b)";
mostCurrent._cop._putsimple("3",(Object)(_b));
 //BA.debugLineNum = 1035;BA.debugLine="put_data";
_put_data();
 //BA.debugLineNum = 1036;BA.debugLine="ex_pan2";
_ex_pan2();
 };
 //BA.debugLineNum = 1038;BA.debugLine="End Sub";
return "";
}
public static String  _int_folder() throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _pnl = null;
String _d1 = "";
String _d2 = "";
String _master = "";
anywheresoftware.b4a.objects.LabelWrapper _sf = null;
String _s = "";
String _error = "";
String _errorc = "";
anywheresoftware.b4a.objects.LabelWrapper _errorb = null;
anywheresoftware.b4a.objects.LabelWrapper _errord = null;
de.amberhome.materialdialogs.MaterialDialogBuilderWrapper _builder = null;
 //BA.debugLineNum = 1119;BA.debugLine="Sub int_folder";
 //BA.debugLineNum = 1120;BA.debugLine="Dim pnl As Panel";
_pnl = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 1121;BA.debugLine="pnl.Initialize(\"\")";
_pnl.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1122;BA.debugLine="Dim d1,d2,master As String";
_d1 = "";
_d2 = "";
_master = "";
 //BA.debugLineNum = 1123;BA.debugLine="d1=File.DirRootExternal&\"/APEX\"";
_d1 = anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/APEX";
 //BA.debugLineNum = 1124;BA.debugLine="d2=datas.GetSimple(\"0\")";
_d2 = mostCurrent._datas._getsimple("0");
 //BA.debugLineNum = 1125;BA.debugLine="Dim sf As Label";
_sf = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 1126;BA.debugLine="sf.Initialize(\"\")";
_sf.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1127;BA.debugLine="For Each s As String In finish";
{
final anywheresoftware.b4a.BA.IterableList group8 = mostCurrent._finish;
final int groupLen8 = group8.getSize()
;int index8 = 0;
;
for (; index8 < groupLen8;index8++){
_s = BA.ObjectToString(group8.Get(index8));
 //BA.debugLineNum = 1128;BA.debugLine="sf=pnl.GetView(s)";
_sf.setObject((android.widget.TextView)(_pnl.GetView((int)(Double.parseDouble(_s))).getObject()));
 }
};
 //BA.debugLineNum = 1130;BA.debugLine="If datas.ContainsKey(\"0\") Then";
if (mostCurrent._datas._containskey("0")) { 
 //BA.debugLineNum = 1131;BA.debugLine="master=d2";
_master = _d2;
 };
 //BA.debugLineNum = 1133;BA.debugLine="Dim error As String =\"Backup found!\"";
_error = "Backup found!";
 //BA.debugLineNum = 1134;BA.debugLine="Dim errorc As String =master";
_errorc = _master;
 //BA.debugLineNum = 1135;BA.debugLine="Dim errorb,errord As Label";
_errorb = new anywheresoftware.b4a.objects.LabelWrapper();
_errord = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 1136;BA.debugLine="errorb.Initialize(\"errorb\")";
_errorb.Initialize(mostCurrent.activityBA,"errorb");
 //BA.debugLineNum = 1137;BA.debugLine="errord.Initialize(\"errord\")";
_errord.Initialize(mostCurrent.activityBA,"errord");
 //BA.debugLineNum = 1138;BA.debugLine="errorb.Text=error";
_errorb.setText(BA.ObjectToCharSequence(_error));
 //BA.debugLineNum = 1139;BA.debugLine="errord.Text=errorc";
_errord.setText(BA.ObjectToCharSequence(_errorc));
 //BA.debugLineNum = 1140;BA.debugLine="errorb.Gravity=Gravity.FILL";
_errorb.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 1141;BA.debugLine="errorb.TextColor=mcl.md_black_1000";
_errorb.setTextColor(mostCurrent._mcl.getmd_black_1000());
 //BA.debugLineNum = 1142;BA.debugLine="errorb.TextSize=17";
_errorb.setTextSize((float) (17));
 //BA.debugLineNum = 1143;BA.debugLine="icon=pak.GetApplicationIcon(s)";
mostCurrent._icon.setObject((android.graphics.drawable.BitmapDrawable)(_pak.GetApplicationIcon(_s)));
 //BA.debugLineNum = 1144;BA.debugLine="Dim Builder As MaterialDialogBuilder";
_builder = new de.amberhome.materialdialogs.MaterialDialogBuilderWrapper();
 //BA.debugLineNum = 1145;BA.debugLine="Builder.Initialize(\"intdia\")";
_builder.Initialize(mostCurrent.activityBA,"intdia");
 //BA.debugLineNum = 1146;BA.debugLine="Builder.Title(\"APK Info\").TitleColor(mcl.md_black";
_builder.Title(BA.ObjectToCharSequence("APK Info")).TitleColor(mostCurrent._mcl.getmd_black_1000()).Icon((android.graphics.drawable.Drawable)(mostCurrent._icon.getObject())).LimitIconToDefaultSize().Theme(_builder.THEME_LIGHT).Content(BA.ObjectToCharSequence(_errorb.getText()+","+_errord.getText()+_s+".apk")).ContentLineSpacing((float) (1)).ContentColor(mostCurrent._mcl.getmd_black_1000()).Cancelable(anywheresoftware.b4a.keywords.Common.True).AutoDismiss(anywheresoftware.b4a.keywords.Common.True).PositiveText(BA.ObjectToCharSequence("Ok")).PositiveColor(mostCurrent._mcl.getmd_amber_900()).NeutralText(BA.ObjectToCharSequence("")).NeutralColor(mostCurrent._mcl.getmd_black_1000());
 //BA.debugLineNum = 1147;BA.debugLine="deldia=Builder.Show";
mostCurrent._deldia = _builder.Show();
 //BA.debugLineNum = 1148;BA.debugLine="deldia.Show";
mostCurrent._deldia.Show();
 //BA.debugLineNum = 1149;BA.debugLine="End Sub";
return "";
}
public static String  _item_show() throws Exception{
anywheresoftware.b4a.objects.drawable.BitmapDrawable _ico = null;
de.amberhome.materialdialogs.MaterialSimpleListItemWrapper _imi = null;
anywheresoftware.b4a.objects.LabelWrapper _dlab = null;
anywheresoftware.b4a.objects.LabelWrapper _dcont = null;
anywheresoftware.b4a.objects.collections.List _dlist = null;
de.amberhome.materialdialogs.MaterialDialogBuilderWrapper _builder = null;
int _i = 0;
 //BA.debugLineNum = 869;BA.debugLine="Sub item_show";
 //BA.debugLineNum = 870;BA.debugLine="Dim ico As BitmapDrawable";
_ico = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 871;BA.debugLine="Dim imi As MaterialSimpleListItem";
_imi = new de.amberhome.materialdialogs.MaterialSimpleListItemWrapper();
 //BA.debugLineNum = 872;BA.debugLine="Dim dlab,dcont As Label";
_dlab = new anywheresoftware.b4a.objects.LabelWrapper();
_dcont = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 873;BA.debugLine="dlab.Initialize(\"\")";
_dlab.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 874;BA.debugLine="dcont.Initialize(\"\")";
_dcont.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 875;BA.debugLine="dlab.TextSize=15";
_dlab.setTextSize((float) (15));
 //BA.debugLineNum = 876;BA.debugLine="dcont.TextSize=14";
_dcont.setTextSize((float) (14));
 //BA.debugLineNum = 877;BA.debugLine="Dim dlist As List";
_dlist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 878;BA.debugLine="dlist.Initialize";
_dlist.Initialize();
 //BA.debugLineNum = 879;BA.debugLine="dir=datas.GetSimple(\"0\")";
mostCurrent._dir = mostCurrent._datas._getsimple("0");
 //BA.debugLineNum = 881;BA.debugLine="dlist=File.ListFiles(dir)";
_dlist = anywheresoftware.b4a.keywords.Common.File.ListFiles(mostCurrent._dir);
 //BA.debugLineNum = 882;BA.debugLine="dlab.Text=\"Backup Archive:\"";
_dlab.setText(BA.ObjectToCharSequence("Backup Archive:"));
 //BA.debugLineNum = 883;BA.debugLine="dcont.Text=dlist.size&\" apk´s\"";
_dcont.setText(BA.ObjectToCharSequence(BA.NumberToString(_dlist.getSize())+" apk´s"));
 //BA.debugLineNum = 884;BA.debugLine="ico.Initialize(LoadBitmap(File.DirAssets,\"ic_sd_s";
_ico.Initialize((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"ic_sd_storage_black_36dp.png").getObject()));
 //BA.debugLineNum = 885;BA.debugLine="icon.Initialize(LoadBitmap(File.DirAssets,\"ic_ass";
mostCurrent._icon.Initialize((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"ic_assignment_turned_in_black_24dp.png").getObject()));
 //BA.debugLineNum = 886;BA.debugLine="icon.Gravity=Gravity.CENTER";
mostCurrent._icon.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 887;BA.debugLine="Dim Builder As MaterialDialogBuilder";
_builder = new de.amberhome.materialdialogs.MaterialDialogBuilderWrapper();
 //BA.debugLineNum = 888;BA.debugLine="Builder.Initialize(\"idia\")";
_builder.Initialize(mostCurrent.activityBA,"idia");
 //BA.debugLineNum = 889;BA.debugLine="Builder.Title(dlab.text).TitleColor(mcl.md_black_";
_builder.Title(BA.ObjectToCharSequence(_dlab.getText())).TitleColor(mostCurrent._mcl.getmd_black_1000()).Icon((android.graphics.drawable.Drawable)(_ico.getObject())).LimitIconToDefaultSize().Theme(_builder.THEME_LIGHT).Content(BA.ObjectToCharSequence(_dcont.getText())).ContentLineSpacing((float) (1)).ContentColor(mostCurrent._mcl.getmd_black_1000()).Cancelable(anywheresoftware.b4a.keywords.Common.True).AutoDismiss(anywheresoftware.b4a.keywords.Common.False).NeutralText(BA.ObjectToCharSequence("Ok")).NeutralColor(mostCurrent._mcl.getmd_black_1000()).ItemsCallback().Build();
 //BA.debugLineNum = 890;BA.debugLine="Builder.ForceStacking(True)";
_builder.ForceStacking(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 891;BA.debugLine="For  i =0 To  dlist.Size-1";
{
final int step21 = 1;
final int limit21 = (int) (_dlist.getSize()-1);
_i = (int) (0) ;
for (;(step21 > 0 && _i <= limit21) || (step21 < 0 && _i >= limit21) ;_i = ((int)(0 + _i + step21))  ) {
 //BA.debugLineNum = 892;BA.debugLine="imi.Initialize(icon,dlist.Get(i))";
_imi.Initialize(processBA,(android.graphics.drawable.Drawable)(mostCurrent._icon.getObject()),BA.ObjectToCharSequence(_dlist.Get(_i)));
 //BA.debugLineNum = 893;BA.debugLine="Builder.AddSimpleItem(imi)";
_builder.AddSimpleItem(_imi);
 //BA.debugLineNum = 894;BA.debugLine="imi.Id=i";
_imi.setId((long) (_i));
 }
};
 //BA.debugLineNum = 896;BA.debugLine="Builder.ItemsCallbacksingleChoice(Builder.SimpleI";
_builder.ItemsCallbackSingleChoice(_builder.getSimpleItemCount()).LimitIconToDefaultSize();
 //BA.debugLineNum = 897;BA.debugLine="deldia=Builder.Show";
mostCurrent._deldia = _builder.Show();
 //BA.debugLineNum = 898;BA.debugLine="deldia.Show";
mostCurrent._deldia.Show();
 //BA.debugLineNum = 899;BA.debugLine="End Sub";
return "";
}
public static String  _lst_click(anywheresoftware.b4a.objects.PanelWrapper _pnl,Object _id) throws Exception{
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
 //BA.debugLineNum = 1437;BA.debugLine="Sub lst_Click(pnl As Panel, ID As Object)";
 //BA.debugLineNum = 1438;BA.debugLine="Dim lbl As Label";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 1439;BA.debugLine="lbl = pnl.GetView(1)";
_lbl.setObject((android.widget.TextView)(_pnl.GetView((int) (1)).getObject()));
 //BA.debugLineNum = 1440;BA.debugLine="If lst.HasExtraContent And lst.ExtendedItemID = I";
if (mostCurrent._lst._hasextracontent() && (mostCurrent._lst._extendeditemid()).equals(_id)) { 
 //BA.debugLineNum = 1442;BA.debugLine="lst.CollapseItem";
mostCurrent._lst._collapseitem();
 }else {
 //BA.debugLineNum = 1446;BA.debugLine="ExpandItem(pnl, ID)";
_expanditem(_pnl,_id);
 };
 //BA.debugLineNum = 1448;BA.debugLine="finish.Clear";
mostCurrent._finish.Clear();
 //BA.debugLineNum = 1449;BA.debugLine="finish.Add(ID)";
mostCurrent._finish.Add(_id);
 //BA.debugLineNum = 1450;BA.debugLine="End Sub";
return "";
}
public static String  _lvmenu_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 368;BA.debugLine="Sub lvmenu_ItemClick (Position As Int, Value As Ob";
 //BA.debugLineNum = 369;BA.debugLine="Select Value";
switch (BA.switchObjectToInt(_value,(Object)("0"),(Object)("1"),(Object)("2"),(Object)("3"))) {
case 0: {
 //BA.debugLineNum = 371;BA.debugLine="sm.HideMenus";
mostCurrent._sm.HideMenus();
 //BA.debugLineNum = 372;BA.debugLine="item_show";
_item_show();
 break; }
case 1: {
 //BA.debugLineNum = 375;BA.debugLine="sm.HideMenus";
mostCurrent._sm.HideMenus();
 //BA.debugLineNum = 376;BA.debugLine="pre_start";
_pre_start();
 break; }
case 2: {
 //BA.debugLineNum = 378;BA.debugLine="sm.HideMenus";
mostCurrent._sm.HideMenus();
 //BA.debugLineNum = 379;BA.debugLine="del_dia";
_del_dia();
 break; }
case 3: {
 //BA.debugLineNum = 381;BA.debugLine="sm.HideMenus";
mostCurrent._sm.HideMenus();
 //BA.debugLineNum = 382;BA.debugLine="sm.ShowSecondaryMenu";
mostCurrent._sm.ShowSecondaryMenu();
 break; }
}
;
 //BA.debugLineNum = 384;BA.debugLine="End Sub";
return "";
}
public static String  _menu_three() throws Exception{
anywheresoftware.b4a.keywords.constants.TypefaceWrapper _font = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _fim = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _him = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _eim = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _climg = null;
 //BA.debugLineNum = 329;BA.debugLine="Sub menu_three";
 //BA.debugLineNum = 330;BA.debugLine="Dim font As Typeface=Typeface.LoadFromAssets(\"Ope";
_font = new anywheresoftware.b4a.keywords.constants.TypefaceWrapper();
_font.setObject((android.graphics.Typeface)(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("OpenSans.ttf")));
 //BA.debugLineNum = 331;BA.debugLine="rftext.Typeface=font";
mostCurrent._rftext.setTypeface((android.graphics.Typeface)(_font.getObject()));
 //BA.debugLineNum = 332;BA.debugLine="rftext.Text=\"FileWatcher Service coming up next V";
mostCurrent._rftext.setText(BA.ObjectToCharSequence("FileWatcher Service coming up next Version.."));
 //BA.debugLineNum = 333;BA.debugLine="rftext.TextSize=12";
mostCurrent._rftext.setTextSize((float) (12));
 //BA.debugLineNum = 334;BA.debugLine="Dim fim,him,eim As Bitmap";
_fim = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
_him = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
_eim = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 335;BA.debugLine="Dim climg As BitmapDrawable";
_climg = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 336;BA.debugLine="fim.Initialize(File.DirAssets,\"ic_label_white_36d";
_fim.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"ic_label_white_36dp.png");
 //BA.debugLineNum = 337;BA.debugLine="climg.Initialize(LoadBitmap(File.DirAssets,\"ic_fo";
_climg.Initialize((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"ic_folder_open_white_36dp.png").getObject()));
 //BA.debugLineNum = 338;BA.debugLine="eim.Initialize(File.DirAssets,\"ic_share_white_36d";
_eim.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"ic_share_white_36dp.png");
 //BA.debugLineNum = 339;BA.debugLine="him.Initialize(File.DirAssets,\"ic_info_outline_wh";
_him.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"ic_info_outline_white_36dp.png");
 //BA.debugLineNum = 340;BA.debugLine="rvmenu.Clear";
mostCurrent._rvmenu.Clear();
 //BA.debugLineNum = 341;BA.debugLine="rvmenu.AddTwoLinesAndBitmap2(\"Share\",\"share\"&pak.";
mostCurrent._rvmenu.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Share"),BA.ObjectToCharSequence("share"+_pak.GetApplicationLabel("com.apkbackup.de")),(android.graphics.Bitmap)(_eim.getObject()),(Object)("0"));
 //BA.debugLineNum = 342;BA.debugLine="rvmenu.AddTwoLinesAndBitmap2(\"Help\",\"FAQ and rece";
mostCurrent._rvmenu.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Help"),BA.ObjectToCharSequence("FAQ and recent Info´s"),(android.graphics.Bitmap)(_fim.getObject()),(Object)("1"));
 //BA.debugLineNum = 343;BA.debugLine="rvmenu.AddTwoLinesAndBitmap2(\"About\",\"APK Backup(";
mostCurrent._rvmenu.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("About"),BA.ObjectToCharSequence("APK Backup(Pro) version etc.."),(android.graphics.Bitmap)(_him.getObject()),(Object)("2"));
 //BA.debugLineNum = 344;BA.debugLine="dir=File.dirrootexternal&\"/APEX/backups\"";
mostCurrent._dir = anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/APEX/backups";
 //BA.debugLineNum = 345;BA.debugLine="If datas.ContainsKey(\"0\") Then";
if (mostCurrent._datas._containskey("0")) { 
 //BA.debugLineNum = 346;BA.debugLine="acp.Add2(datas.GetSimple(\"0\"),climg)";
mostCurrent._acp.Add2(BA.ObjectToCharSequence(mostCurrent._datas._getsimple("0")),(android.graphics.drawable.Drawable)(_climg.getObject()));
 }else {
 //BA.debugLineNum = 348;BA.debugLine="datas.PutSimple(\"0\",dir)";
mostCurrent._datas._putsimple("0",(Object)(mostCurrent._dir));
 //BA.debugLineNum = 349;BA.debugLine="acp.Add2(dir,climg)";
mostCurrent._acp.Add2(BA.ObjectToCharSequence(mostCurrent._dir),(android.graphics.drawable.Drawable)(_climg.getObject()));
 };
 //BA.debugLineNum = 351;BA.debugLine="End Sub";
return "";
}
public static String  _menu_two() throws Exception{
 //BA.debugLineNum = 320;BA.debugLine="Sub menu_two";
 //BA.debugLineNum = 321;BA.debugLine="lvMenu.Clear";
mostCurrent._lvmenu.Clear();
 //BA.debugLineNum = 322;BA.debugLine="lvMenu.AddTwoLinesAndBitmap2(\"Explorer\",\"explore";
mostCurrent._lvmenu.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Explorer"),BA.ObjectToCharSequence("explore your backup folder"),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"ic_folder_open_white_36dp.png").getObject()),(Object)("0"));
 //BA.debugLineNum = 323;BA.debugLine="lvMenu.AddTwoLinesAndBitmap2(\"Backup All\",\"export";
mostCurrent._lvmenu.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Backup All"),BA.ObjectToCharSequence("export all user apps at once"),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"ic_cached_white_36dp.png").getObject()),(Object)("1"));
 //BA.debugLineNum = 324;BA.debugLine="lvMenu.AddTwoLinesAndBitmap2(\"Clear All\",\"deletes";
mostCurrent._lvmenu.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Clear All"),BA.ObjectToCharSequence("deletes all backup files"),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"ic_block_white_36dp.png").getObject()),(Object)("2"));
 //BA.debugLineNum = 325;BA.debugLine="lvMenu.AddTwoLinesAndBitmap2(\"Settings Panel\",\"op";
mostCurrent._lvmenu.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence("Settings Panel"),BA.ObjectToCharSequence("open the Main-settings"),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"ic_exit_to_app_white_48dp.png").getObject()),(Object)("3"));
 //BA.debugLineNum = 326;BA.debugLine="menu_three";
_menu_three();
 //BA.debugLineNum = 327;BA.debugLine="End Sub";
return "";
}
public static String  _menulist_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 723;BA.debugLine="Sub menulist_ItemClick (Position As Int, Value As";
 //BA.debugLineNum = 724;BA.debugLine="If Position=1 Then";
if (_position==1) { 
 //BA.debugLineNum = 725;BA.debugLine="explor_set";
_explor_set();
 };
 //BA.debugLineNum = 727;BA.debugLine="End Sub";
return "";
}
public static String  _minutes_hours(int _ms) throws Exception{
int _val = 0;
int _hours = 0;
int _minutes = 0;
 //BA.debugLineNum = 1487;BA.debugLine="Sub minutes_hours ( ms As Int ) As String";
 //BA.debugLineNum = 1488;BA.debugLine="Dim val,hours,minutes As Int";
_val = 0;
_hours = 0;
_minutes = 0;
 //BA.debugLineNum = 1489;BA.debugLine="val = ms";
_val = _ms;
 //BA.debugLineNum = 1490;BA.debugLine="hours = Floor(val / 60)";
_hours = (int) (anywheresoftware.b4a.keywords.Common.Floor(_val/(double)60));
 //BA.debugLineNum = 1491;BA.debugLine="minutes = val Mod 60";
_minutes = (int) (_val%60);
 //BA.debugLineNum = 1492;BA.debugLine="Return NumberFormat(hours, 1, 0) & \":\" & NumberFo";
if (true) return anywheresoftware.b4a.keywords.Common.NumberFormat(_hours,(int) (1),(int) (0))+":"+anywheresoftware.b4a.keywords.Common.NumberFormat(_minutes,(int) (2),(int) (0));
 //BA.debugLineNum = 1493;BA.debugLine="End Sub";
return "";
}
public static String  _otb_navigationitemclick() throws Exception{
 //BA.debugLineNum = 392;BA.debugLine="Sub otb_NavigationItemClick";
 //BA.debugLineNum = 393;BA.debugLine="sm.HideMenus";
mostCurrent._sm.HideMenus();
 //BA.debugLineNum = 394;BA.debugLine="End Sub";
return "";
}
public static String  _paint_pan(anywheresoftware.b4a.objects.PanelWrapper _pnl,int _state) throws Exception{
 //BA.debugLineNum = 1346;BA.debugLine="Sub paint_pan(pnl As Panel,State As Int)";
 //BA.debugLineNum = 1347;BA.debugLine="Select State";
switch (_state) {
case 0: 
case 1: {
 //BA.debugLineNum = 1349;BA.debugLine="pnl.Color = Colors.ARGB(120,255,255,255)";
_pnl.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (120),(int) (255),(int) (255),(int) (255)));
 break; }
case 2: 
case 3: {
 //BA.debugLineNum = 1351;BA.debugLine="pnl.Color =Colors.ARGB(110,139,195,74)";
_pnl.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (110),(int) (139),(int) (195),(int) (74)));
 break; }
case 4: {
 //BA.debugLineNum = 1353;BA.debugLine="pnl.Color =Colors.ARGB(120,108,113,109)";
_pnl.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (120),(int) (108),(int) (113),(int) (109)));
 break; }
}
;
 //BA.debugLineNum = 1356;BA.debugLine="Return pnl";
if (true) return BA.ObjectToString(_pnl);
 //BA.debugLineNum = 1357;BA.debugLine="End Sub";
return "";
}
public static String  _pak_info(String _path) throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
anywheresoftware.b4a.agraham.reflection.Reflection _r2 = null;
String _fullpath = "";
 //BA.debugLineNum = 1477;BA.debugLine="Sub pak_info(path As String)";
 //BA.debugLineNum = 1478;BA.debugLine="Dim r, r2 As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
_r2 = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 1479;BA.debugLine="Dim fullpath As String";
_fullpath = "";
 //BA.debugLineNum = 1480;BA.debugLine="fullpath =path";
_fullpath = _path;
 //BA.debugLineNum = 1481;BA.debugLine="r.Target = r.GetContext";
_r.Target = (Object)(_r.GetContext(processBA));
 //BA.debugLineNum = 1482;BA.debugLine="r.Target = r.RunMethod(\"getPackageManager\")";
_r.Target = _r.RunMethod("getPackageManager");
 //BA.debugLineNum = 1483;BA.debugLine="r2.Target = r.RunMethod3(\"getPackageArchiveInfo\",";
_r2.Target = _r.RunMethod3("getPackageArchiveInfo",_fullpath,"java.lang.String",BA.NumberToString(1),"java.lang.int");
 //BA.debugLineNum = 1485;BA.debugLine="End Sub";
return "";
}
public static String  _path_explorer() throws Exception{
String _ffs = "";
 //BA.debugLineNum = 577;BA.debugLine="Sub path_explorer";
 //BA.debugLineNum = 578;BA.debugLine="svList.SetVisibleAnimated(400,False)";
mostCurrent._svlist.SetVisibleAnimated((int) (400),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 579;BA.debugLine="ff.Initialize(Activity,File.DirRootExternal,\"*.*\"";
mostCurrent._ff._initialize(mostCurrent.activityBA,mostCurrent._activity,anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"*.*",anywheresoftware.b4a.keywords.Common.True,anywheresoftware.b4a.keywords.Common.True,"Select");
 //BA.debugLineNum = 580;BA.debugLine="ff.Explorer2(False)";
mostCurrent._ff._explorer2(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 581;BA.debugLine="ff.FastScrollEnabled=True";
mostCurrent._ff._fastscrollenabled = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 582;BA.debugLine="Dim ffs As String";
_ffs = "";
 //BA.debugLineNum = 583;BA.debugLine="icon.Initialize(LoadBitmap(File.DirAssets,\"ic_fin";
mostCurrent._icon.Initialize((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"ic_find_in_page_black_36dp.png").getObject()));
 //BA.debugLineNum = 584;BA.debugLine="If  Not(ff.Selection.Canceled Or ff.Selection.Cho";
if (anywheresoftware.b4a.keywords.Common.Not(mostCurrent._ff._selection.Canceled || (mostCurrent._ff._selection.ChosenPath).equals(""))) { 
 //BA.debugLineNum = 586;BA.debugLine="datas.DeleteAll";
mostCurrent._datas._deleteall();
 //BA.debugLineNum = 587;BA.debugLine="datas.PutSimple(\"0\",ff.Selection.ChosenPath)";
mostCurrent._datas._putsimple("0",(Object)(mostCurrent._ff._selection.ChosenPath));
 //BA.debugLineNum = 588;BA.debugLine="ToastMessageShow(ff.Selection.ChosenPath,False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(mostCurrent._ff._selection.ChosenPath),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 589;BA.debugLine="svList.SetVisibleAnimated(400,True)";
mostCurrent._svlist.SetVisibleAnimated((int) (400),anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 591;BA.debugLine="datas.DeleteAll";
mostCurrent._datas._deleteall();
 //BA.debugLineNum = 592;BA.debugLine="datas.PutSimple(\"0\",dir)";
mostCurrent._datas._putsimple("0",(Object)(mostCurrent._dir));
 //BA.debugLineNum = 593;BA.debugLine="svList.SetVisibleAnimated(400,True)";
mostCurrent._svlist.SetVisibleAnimated((int) (400),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 594;BA.debugLine="ToastMessageShow(\"closed\",False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("closed"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 596;BA.debugLine="End Sub";
return "";
}
public static String  _pre_start() throws Exception{
de.amberhome.materialdialogs.MaterialSimpleListItemWrapper _imi = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _ico = null;
anywheresoftware.b4a.objects.LabelWrapper _dlab = null;
anywheresoftware.b4a.objects.LabelWrapper _dcont = null;
de.amberhome.materialdialogs.MaterialDialogBuilderWrapper _builder = null;
int _i = 0;
 //BA.debugLineNum = 431;BA.debugLine="Sub pre_start";
 //BA.debugLineNum = 432;BA.debugLine="Dim imi As MaterialSimpleListItem";
_imi = new de.amberhome.materialdialogs.MaterialSimpleListItemWrapper();
 //BA.debugLineNum = 433;BA.debugLine="Dim ico As BitmapDrawable";
_ico = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 434;BA.debugLine="ico.Initialize(LoadBitmap(File.DirAssets,\"ic_now_";
_ico.Initialize((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"ic_now_widgets_black_18dp.png").getObject()));
 //BA.debugLineNum = 435;BA.debugLine="Dim dlab,dcont As Label";
_dlab = new anywheresoftware.b4a.objects.LabelWrapper();
_dcont = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 437;BA.debugLine="dlab.Initialize(\"\")";
_dlab.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 438;BA.debugLine="dcont.Initialize(\"\")";
_dcont.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 439;BA.debugLine="dlab.TextSize=15";
_dlab.setTextSize((float) (15));
 //BA.debugLineNum = 440;BA.debugLine="dcont.TextSize=14";
_dcont.setTextSize((float) (14));
 //BA.debugLineNum = 441;BA.debugLine="dir=datas.GetSimple(\"0\")";
mostCurrent._dir = mostCurrent._datas._getsimple("0");
 //BA.debugLineNum = 442;BA.debugLine="dlab.Text=\"Backup\"";
_dlab.setText(BA.ObjectToCharSequence("Backup"));
 //BA.debugLineNum = 444;BA.debugLine="icon.Gravity=Gravity.FILL";
mostCurrent._icon.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 445;BA.debugLine="Dim Builder As MaterialDialogBuilder";
_builder = new de.amberhome.materialdialogs.MaterialDialogBuilderWrapper();
 //BA.debugLineNum = 446;BA.debugLine="exalist.Clear";
mostCurrent._exalist.Clear();
 //BA.debugLineNum = 447;BA.debugLine="Builder.Initialize(\"exadia\")";
_builder.Initialize(mostCurrent.activityBA,"exadia");
 //BA.debugLineNum = 448;BA.debugLine="For i=0 To extra.Size-1";
{
final int step15 = 1;
final int limit15 = (int) (mostCurrent._extra.getSize()-1);
_i = (int) (0) ;
for (;(step15 > 0 && _i <= limit15) || (step15 < 0 && _i >= limit15) ;_i = ((int)(0 + _i + step15))  ) {
 //BA.debugLineNum = 449;BA.debugLine="exalist.Add(extra.Get(i))";
mostCurrent._exalist.Add(mostCurrent._extra.Get(_i));
 //BA.debugLineNum = 450;BA.debugLine="size= File.Size(GetParentPath(GetSourceDir(GetAc";
_size = (int) (anywheresoftware.b4a.keywords.Common.File.Size(_getparentpath(_getsourcedir(_getactivitiesinfo(BA.ObjectToString(mostCurrent._extra.Get(_i))))),_getfilename(_getsourcedir(_getactivitiesinfo(BA.ObjectToString(mostCurrent._extra.Get(_i)))))));
 //BA.debugLineNum = 451;BA.debugLine="icon=pak.GetApplicationIcon(extra.Get(i))";
mostCurrent._icon.setObject((android.graphics.drawable.BitmapDrawable)(_pak.GetApplicationIcon(BA.ObjectToString(mostCurrent._extra.Get(_i)))));
 //BA.debugLineNum = 452;BA.debugLine="packName=extra.Get(i)";
mostCurrent._packname = BA.ObjectToString(mostCurrent._extra.Get(_i));
 //BA.debugLineNum = 453;BA.debugLine="imi.Initialize(icon,pak.GetApplicationLabel(extr";
_imi.Initialize(processBA,(android.graphics.drawable.Drawable)(mostCurrent._icon.getObject()),BA.ObjectToCharSequence(_pak.GetApplicationLabel(BA.ObjectToString(mostCurrent._extra.Get(_i)))+" - "+_formatfilesize((float) (_size))));
 //BA.debugLineNum = 454;BA.debugLine="Builder.AddSimpleItem(imi)";
_builder.AddSimpleItem(_imi);
 //BA.debugLineNum = 455;BA.debugLine="dcont.Text=\"Backup all unsaved App´s at once..(\"";
_dcont.setText(BA.ObjectToCharSequence("Backup all unsaved App´s at once..("+_formatfilesize((float) (_size*1024*10))+")"));
 }
};
 //BA.debugLineNum = 457;BA.debugLine="Builder.Title(dlab.text).TitleColor(mcl.md_black_";
_builder.Title(BA.ObjectToCharSequence(_dlab.getText())).TitleColor(mostCurrent._mcl.getmd_black_1000()).Icon((android.graphics.drawable.Drawable)(_ico.getObject())).LimitIconToDefaultSize().Theme(_builder.THEME_LIGHT).Content(BA.ObjectToCharSequence(_dcont.getText())).ContentLineSpacing((float) (1)).ContentColor(mostCurrent._mcl.getmd_black_1000()).Cancelable(anywheresoftware.b4a.keywords.Common.False).AutoDismiss(anywheresoftware.b4a.keywords.Common.True).PositiveText(BA.ObjectToCharSequence("Start")).PositiveColor(mostCurrent._mcl.getmd_amber_700()).NeutralText(BA.ObjectToCharSequence("Cancel")).NeutralColor(mostCurrent._mcl.getmd_black_1000());
 //BA.debugLineNum = 463;BA.debugLine="deldia=Builder.Show";
mostCurrent._deldia = _builder.Show();
 //BA.debugLineNum = 464;BA.debugLine="deldia.Show";
mostCurrent._deldia.Show();
 //BA.debugLineNum = 466;BA.debugLine="End Sub";
return "";
}

public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        main._process_globals();
swit._process_globals();
animator._process_globals();
savemodule._process_globals();
starter._process_globals();
fileobserve._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 22;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 24;BA.debugLine="Private pak As PackageManager";
_pak = new anywheresoftware.b4a.phone.PackageManagerWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Dim time,date As String";
_time = "";
_date = "";
 //BA.debugLineNum = 26;BA.debugLine="Dim dex As Timer";
_dex = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 27;BA.debugLine="Dim ex As Timer";
_ex = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 28;BA.debugLine="Dim exa As Timer";
_exa = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 29;BA.debugLine="End Sub";
return "";
}
public static String  _prog_start() throws Exception{
String _version = "";
String _s = "";
String _ss = "";
int _sum = 0;
 //BA.debugLineNum = 1152;BA.debugLine="Sub prog_start";
 //BA.debugLineNum = 1153;BA.debugLine="Dim  version As String";
_version = "";
 //BA.debugLineNum = 1154;BA.debugLine="Dim s As String = finish.Get(0)";
_s = BA.ObjectToString(mostCurrent._finish.Get((int) (0)));
 //BA.debugLineNum = 1155;BA.debugLine="Dim ss As String";
_ss = "";
 //BA.debugLineNum = 1156;BA.debugLine="Dim sum As Int";
_sum = 0;
 //BA.debugLineNum = 1157;BA.debugLine="sum=0";
_sum = (int) (0);
 //BA.debugLineNum = 1158;BA.debugLine="dir=datas.Getsimple(\"0\")";
mostCurrent._dir = mostCurrent._datas._getsimple("0");
 //BA.debugLineNum = 1159;BA.debugLine="name1=pak.GetApplicationLabel(s)";
mostCurrent._name1 = _pak.GetApplicationLabel(_s);
 //BA.debugLineNum = 1160;BA.debugLine="icon=pak.GetApplicationIcon(s)";
mostCurrent._icon.setObject((android.graphics.drawable.BitmapDrawable)(_pak.GetApplicationIcon(_s)));
 //BA.debugLineNum = 1162;BA.debugLine="version=pak.GetVersionName(s)";
_version = _pak.GetVersionName(_s);
 //BA.debugLineNum = 1163;BA.debugLine="If File.Exists(dir,name1&\"_\"&version&\".apk\") Then";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._dir,mostCurrent._name1+"_"+_version+".apk")) { 
 //BA.debugLineNum = 1165;BA.debugLine="ss=s";
_ss = _s;
 //BA.debugLineNum = 1166;BA.debugLine="dataliste.Clear";
mostCurrent._dataliste.Clear();
 //BA.debugLineNum = 1167;BA.debugLine="dataliste.AddTwoLinesAndBitmap(name1,FormatFileS";
mostCurrent._dataliste.AddTwoLinesAndBitmap(BA.ObjectToCharSequence(mostCurrent._name1),BA.ObjectToCharSequence(_formatfilesize((float) (_size))),mostCurrent._icon.getBitmap());
 //BA.debugLineNum = 1168;BA.debugLine="dataliste.AddTwoLinesAndBitmap(\"Folder:\",datas.G";
mostCurrent._dataliste.AddTwoLinesAndBitmap(BA.ObjectToCharSequence("Folder:"),BA.ObjectToCharSequence(mostCurrent._datas._getsimple("0")),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"ic_storage_white_18dp.png").getObject()));
 //BA.debugLineNum = 1169;BA.debugLine="dataliste.AddTwoLinesAndBitmap(\"Last:\",dtime.Get";
mostCurrent._dataliste.AddTwoLinesAndBitmap(BA.ObjectToCharSequence("Last:"),BA.ObjectToCharSequence(mostCurrent._dtime._getsimple(_ss)),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"ic_storage_white_18dp.png").getObject()));
 //BA.debugLineNum = 1170;BA.debugLine="ex_pan";
_ex_pan();
 }else {
 //BA.debugLineNum = 1172;BA.debugLine="ex_pan2";
_ex_pan2();
 };
 //BA.debugLineNum = 1174;BA.debugLine="End Sub";
return "";
}
public static String  _put_data() throws Exception{
String _s = "";
 //BA.debugLineNum = 1104;BA.debugLine="Sub put_data";
 //BA.debugLineNum = 1105;BA.debugLine="Dim s As String = finish.Get(0)";
_s = BA.ObjectToString(mostCurrent._finish.Get((int) (0)));
 //BA.debugLineNum = 1106;BA.debugLine="packName=s";
mostCurrent._packname = _s;
 //BA.debugLineNum = 1107;BA.debugLine="If bcount.ContainsKey(packName) Then";
if (mostCurrent._bcount._containskey(mostCurrent._packname)) { 
 //BA.debugLineNum = 1108;BA.debugLine="dtime.Remove(packName)";
mostCurrent._dtime._remove(mostCurrent._packname);
 //BA.debugLineNum = 1109;BA.debugLine="dtime.PutSimple(packName,date&\" - \"&time)";
mostCurrent._dtime._putsimple(mostCurrent._packname,(Object)(_date+" - "+_time));
 //BA.debugLineNum = 1110;BA.debugLine="bcount.Remove(packName)";
mostCurrent._bcount._remove(mostCurrent._packname);
 //BA.debugLineNum = 1111;BA.debugLine="bcount.PutSimple(\"0\",packName)";
mostCurrent._bcount._putsimple("0",(Object)(mostCurrent._packname));
 }else {
 //BA.debugLineNum = 1113;BA.debugLine="dtime.PutSimple(packName,date&\" - \"&time)";
mostCurrent._dtime._putsimple(mostCurrent._packname,(Object)(_date+" - "+_time));
 //BA.debugLineNum = 1114;BA.debugLine="bcount.PutSimple(\"0\",packName)";
mostCurrent._bcount._putsimple("0",(Object)(mostCurrent._packname));
 };
 //BA.debugLineNum = 1117;BA.debugLine="End Sub";
return "";
}
public static String  _put_pack(String _pname) throws Exception{
Object _ob1 = null;
 //BA.debugLineNum = 1468;BA.debugLine="Sub put_pack( pname As String )";
 //BA.debugLineNum = 1469;BA.debugLine="Dim ob1 As Object";
_ob1 = new Object();
 //BA.debugLineNum = 1470;BA.debugLine="ob1=pname";
_ob1 = (Object)(_pname);
 //BA.debugLineNum = 1471;BA.debugLine="finish.Clear";
mostCurrent._finish.Clear();
 //BA.debugLineNum = 1472;BA.debugLine="finish.Add(pname)";
mostCurrent._finish.Add((Object)(_pname));
 //BA.debugLineNum = 1474;BA.debugLine="ab1_Click";
_ab1_click();
 //BA.debugLineNum = 1475;BA.debugLine="End Sub";
return "";
}
public static String  _rvmenu_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 353;BA.debugLine="Sub rvmenu_ItemClick (Position As Int, Value As Ob";
 //BA.debugLineNum = 354;BA.debugLine="Select Position";
switch (_position) {
case 0: {
 //BA.debugLineNum = 356;BA.debugLine="app_share";
_app_share();
 break; }
case 1: {
 //BA.debugLineNum = 359;BA.debugLine="ToastMessageShow(\"Sorry coming on next Update ;";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Sorry coming on next Update ;("),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 360;BA.debugLine="sm.HideMenus";
mostCurrent._sm.HideMenus();
 break; }
case 2: {
 //BA.debugLineNum = 363;BA.debugLine="CallSub(Me,dev_info)";
anywheresoftware.b4a.keywords.Common.CallSubNew(processBA,main.getObject(),_dev_info());
 //BA.debugLineNum = 364;BA.debugLine="sm.HideMenus";
mostCurrent._sm.HideMenus();
 break; }
}
;
 //BA.debugLineNum = 367;BA.debugLine="End Sub";
return "";
}
public static String  _social_init() throws Exception{
 //BA.debugLineNum = 710;BA.debugLine="Sub social_init";
 //BA.debugLineNum = 711;BA.debugLine="ToastMessageShow(\"avaiable at next update..\",Fals";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("avaiable at next update.."),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 712;BA.debugLine="End Sub";
return "";
}
public static String  _stadia_buttonpressed(de.amberhome.materialdialogs.MaterialDialogWrapper _dialog,String _action) throws Exception{
 //BA.debugLineNum = 853;BA.debugLine="Sub stadia_ButtonPressed (Dialog As MaterialDialog";
 //BA.debugLineNum = 854;BA.debugLine="Select Action";
switch (BA.switchObjectToInt(_action,_dialog.ACTION_POSITIVE,_dialog.ACTION_NEGATIVE,_dialog.ACTION_NEUTRAL)) {
case 0: {
 //BA.debugLineNum = 856;BA.debugLine="prog_start";
_prog_start();
 break; }
case 1: {
 //BA.debugLineNum = 858;BA.debugLine="ToastMessageShow(Action, False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(_action),anywheresoftware.b4a.keywords.Common.False);
 break; }
case 2: {
 //BA.debugLineNum = 860;BA.debugLine="ToastMessageShow(Action&\"...\",False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(_action+"..."),anywheresoftware.b4a.keywords.Common.False);
 break; }
}
;
 //BA.debugLineNum = 863;BA.debugLine="End Sub";
return "";
}
public static String  _stadia_itemselected(de.amberhome.materialdialogs.MaterialDialogWrapper _dialog,int _position,String _text) throws Exception{
 //BA.debugLineNum = 841;BA.debugLine="Sub stadia_ItemSelected (Dialog As MaterialDialog,";
 //BA.debugLineNum = 842;BA.debugLine="If Position=2 Then";
if (_position==2) { 
 //BA.debugLineNum = 843;BA.debugLine="path_explorer";
_path_explorer();
 }else {
 //BA.debugLineNum = 845;BA.debugLine="prog_start";
_prog_start();
 };
 //BA.debugLineNum = 847;BA.debugLine="End Sub";
return "";
}
public static String  _stadia_singlechoiceitemselected(de.amberhome.materialdialogs.MaterialDialogWrapper _dialog,int _position,String _text) throws Exception{
 //BA.debugLineNum = 848;BA.debugLine="Sub stadia_SingleChoiceItemSelected (Dialog As Mat";
 //BA.debugLineNum = 849;BA.debugLine="If Position=2 Then";
if (_position==2) { 
 //BA.debugLineNum = 850;BA.debugLine="path_explorer";
_path_explorer();
 };
 //BA.debugLineNum = 852;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper  _texttobitmap(String _s,float _fontsize) throws Exception{
anywheresoftware.b4a.objects.LabelWrapper _lblfont = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _bmp = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper _cvs = null;
double _h = 0;
 //BA.debugLineNum = 1456;BA.debugLine="Sub TextToBitmap (s As String, FontSize As Float)";
 //BA.debugLineNum = 1457;BA.debugLine="Dim lblfont As Label";
_lblfont = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 1458;BA.debugLine="lblfont.Initialize(\"\")";
_lblfont.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1459;BA.debugLine="lblfont.Text=s";
_lblfont.setText(BA.ObjectToCharSequence(_s));
 //BA.debugLineNum = 1460;BA.debugLine="Dim bmp As Bitmap";
_bmp = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 1461;BA.debugLine="bmp.InitializeMutable(32dip, 32dip)";
_bmp.InitializeMutable(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (32)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (32)));
 //BA.debugLineNum = 1462;BA.debugLine="Dim cvs As Canvas";
_cvs = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
 //BA.debugLineNum = 1463;BA.debugLine="cvs.Initialize2(bmp)";
_cvs.Initialize2((android.graphics.Bitmap)(_bmp.getObject()));
 //BA.debugLineNum = 1464;BA.debugLine="Dim h As Double = cvs.MeasureStringHeight(s, lblf";
_h = _cvs.MeasureStringHeight(_s,_lblfont.getTypeface(),_fontsize);
 //BA.debugLineNum = 1465;BA.debugLine="cvs.DrawText(s, bmp.Width / 2, bmp.Height / 2 + h";
_cvs.DrawText(mostCurrent.activityBA,_s,(float) (_bmp.getWidth()/(double)2),(float) (_bmp.getHeight()/(double)2+_h/(double)2),_lblfont.getTypeface(),_fontsize,anywheresoftware.b4a.keywords.Common.Colors.White,BA.getEnumFromString(android.graphics.Paint.Align.class,"CENTER"));
 //BA.debugLineNum = 1466;BA.debugLine="Return bmp";
if (true) return _bmp;
 //BA.debugLineNum = 1467;BA.debugLine="End Sub";
return null;
}
public static String  _toolbar_menuitemclick(de.amberhome.objects.appcompat.ACMenuItemWrapper _item) throws Exception{
 //BA.debugLineNum = 399;BA.debugLine="Sub toolbar_MenuItemClick (Item As ACMenuItem)";
 //BA.debugLineNum = 400;BA.debugLine="Select Item.Id";
switch (BA.switchObjectToInt(_item.getId(),(int)(Double.parseDouble("0")),(int)(Double.parseDouble("1")),(int)(Double.parseDouble("2")),(int)(Double.parseDouble("3")))) {
case 0: {
 //BA.debugLineNum = 402;BA.debugLine="sm.ShowMenu";
mostCurrent._sm.ShowMenu();
 break; }
case 1: {
 //BA.debugLineNum = 404;BA.debugLine="sm.ShowSecondaryMenu";
mostCurrent._sm.ShowSecondaryMenu();
 break; }
case 2: {
 //BA.debugLineNum = 406;BA.debugLine="close_click";
_close_click();
 break; }
case 3: {
 break; }
}
;
 //BA.debugLineNum = 410;BA.debugLine="End Sub";
return "";
}
public static String  _toolbar_navigationitemclick() throws Exception{
 //BA.debugLineNum = 385;BA.debugLine="Sub toolbar_NavigationItemClick";
 //BA.debugLineNum = 386;BA.debugLine="If sm.Visible=True Then";
if (mostCurrent._sm.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 387;BA.debugLine="sm.HideMenus";
mostCurrent._sm.HideMenus();
 }else {
 //BA.debugLineNum = 389;BA.debugLine="sm.ShowMenu";
mostCurrent._sm.ShowMenu();
 };
 //BA.debugLineNum = 391;BA.debugLine="End Sub";
return "";
}

public boolean _onCreateOptionsMenu(android.view.Menu menu) {
    if (processBA.subExists("activity_createmenu")) {
        processBA.raiseEvent2(null, true, "activity_createmenu", false, new de.amberhome.objects.appcompat.ACMenuWrapper(menu));
        return true;
    }
    else
        return false;
}
}

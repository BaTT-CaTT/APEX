package com.apkbackup.de;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.objects.ServiceHelper;
import anywheresoftware.b4a.debug.*;

public class fileobserve extends  android.app.Service{
	public static class fileobserve_BR extends android.content.BroadcastReceiver {

		@Override
		public void onReceive(android.content.Context context, android.content.Intent intent) {
			android.content.Intent in = new android.content.Intent(context, fileobserve.class);
			if (intent != null)
				in.putExtra("b4a_internal_intent", intent);
			context.startService(in);
		}

	}
    static fileobserve mostCurrent;
	public static BA processBA;
    private ServiceHelper _service;
    public static Class<?> getObject() {
		return fileobserve.class;
	}
	@Override
	public void onCreate() {
        super.onCreate();
        mostCurrent = this;
        if (processBA == null) {
		    processBA = new BA(this, null, null, "com.apkbackup.de", "com.apkbackup.de.fileobserve");
            if (BA.isShellModeRuntimeCheck(processBA)) {
                processBA.raiseEvent2(null, true, "SHELL", false);
		    }
            try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            processBA.loadHtSubs(this.getClass());
            ServiceHelper.init();
        }
        _service = new ServiceHelper(this);
        processBA.service = this;
        
        if (BA.isShellModeRuntimeCheck(processBA)) {
			processBA.raiseEvent2(null, true, "CREATE", true, "com.apkbackup.de.fileobserve", processBA, _service, anywheresoftware.b4a.keywords.Common.Density);
		}
        if (!false && ServiceHelper.StarterHelper.startFromServiceCreate(processBA, false) == false) {
				
		}
		else {
            processBA.setActivityPaused(false);
            BA.LogInfo("*** Service (fileobserve) Create ***");
            processBA.raiseEvent(null, "service_create");
        }
        processBA.runHook("oncreate", this, null);
        if (false) {
			ServiceHelper.StarterHelper.runWaitForLayouts();
		}
    }
		@Override
	public void onStart(android.content.Intent intent, int startId) {
		onStartCommand(intent, 0, 0);
    }
    @Override
    public int onStartCommand(final android.content.Intent intent, int flags, int startId) {
    	if (ServiceHelper.StarterHelper.onStartCommand(processBA, new Runnable() {
            public void run() {
                handleStart(intent);
            }}))
			;
		else {
			ServiceHelper.StarterHelper.addWaitForLayout (new Runnable() {
				public void run() {
                    processBA.setActivityPaused(false);
                    BA.LogInfo("** Service (fileobserve) Create **");
                    processBA.raiseEvent(null, "service_create");
					handleStart(intent);
                    ServiceHelper.StarterHelper.removeWaitForLayout();
				}
			});
		}
        processBA.runHook("onstartcommand", this, new Object[] {intent, flags, startId});
		return android.app.Service.START_NOT_STICKY;
    }
    public void onTaskRemoved(android.content.Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        if (false)
            processBA.raiseEvent(null, "service_taskremoved");
            
    }
    private void handleStart(android.content.Intent intent) {
    	BA.LogInfo("** Service (fileobserve) Start **");
    	java.lang.reflect.Method startEvent = processBA.htSubs.get("service_start");
    	if (startEvent != null) {
    		if (startEvent.getParameterTypes().length > 0) {
    			anywheresoftware.b4a.objects.IntentWrapper iw = new anywheresoftware.b4a.objects.IntentWrapper();
    			if (intent != null) {
    				if (intent.hasExtra("b4a_internal_intent"))
    					iw.setObject((android.content.Intent) intent.getParcelableExtra("b4a_internal_intent"));
    				else
    					iw.setObject(intent);
    			}
    			processBA.raiseEvent(null, "service_start", iw);
    		}
    		else {
    			processBA.raiseEvent(null, "service_start");
    		}
    	}
    }
	
	@Override
	public void onDestroy() {
        super.onDestroy();
        BA.LogInfo("** Service (fileobserve) Destroy **");
		processBA.raiseEvent(null, "service_destroy");
        processBA.service = null;
		mostCurrent = null;
		processBA.setActivityPaused(true);
        processBA.runHook("ondestroy", this, null);
	}

@Override
	public android.os.IBinder onBind(android.content.Intent intent) {
		return null;
	}public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4a.phone.PhoneEvents _pe = null;
public static anywheresoftware.b4a.phone.Phone.PhoneId _phoneid = null;
public static anywheresoftware.b4a.objects.NotificationWrapper _cast = null;
public static anywheresoftware.b4a.objects.collections.List _plist = null;
public static anywheresoftware.b4a.phone.PackageManagerWrapper _pak = null;
public static anywheresoftware.b4a.agraham.reflection.Reflection _obj1 = null;
public static anywheresoftware.b4a.agraham.reflection.Reflection _obj2 = null;
public static anywheresoftware.b4a.agraham.reflection.Reflection _obj3 = null;
public static anywheresoftware.b4a.agraham.reflection.Reflection _obj4 = null;
public static anywheresoftware.b4a.agraham.reflection.Reflection _obj5 = null;
public static anywheresoftware.b4a.agraham.reflection.Reflection _obj6 = null;
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
public static Object[] _args = null;
public static anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _icon = null;
public static anywheresoftware.b4a.objects.collections.List _applist = null;
public static String _version = "";
public static com.apkbackup.de.keyvaluestore _datas = null;
public static com.apkbackup.de.keyvaluestore _dtime = null;
public static com.apkbackup.de.keyvaluestore _count = null;
public static String _time = "";
public static String _date = "";
public static String _dir = "";
public com.apkbackup.de.main _main = null;
public com.apkbackup.de.swit _swit = null;
public com.apkbackup.de.animator _animator = null;
public com.apkbackup.de.savemodule _savemodule = null;
public com.apkbackup.de.starter _starter = null;
public static String  _apps_start() throws Exception{
int _i = 0;
 //BA.debugLineNum = 78;BA.debugLine="Sub apps_start";
 //BA.debugLineNum = 80;BA.debugLine="applist=pak.GetInstalledPackages";
_applist = _pak.GetInstalledPackages();
 //BA.debugLineNum = 81;BA.debugLine="Obj1.Target = Obj1.GetContext";
_obj1.Target = (Object)(_obj1.GetContext(processBA));
 //BA.debugLineNum = 82;BA.debugLine="Obj1.Target = Obj1.RunMethod(\"getPackageManager\")";
_obj1.Target = _obj1.RunMethod("getPackageManager");
 //BA.debugLineNum = 83;BA.debugLine="Obj2.Target = Obj1.RunMethod2(\"getInstalledPackag";
_obj2.Target = _obj1.RunMethod2("getInstalledPackages",BA.NumberToString(0),"java.lang.int");
 //BA.debugLineNum = 84;BA.debugLine="size = Obj2.RunMethod(\"size\")";
_size = (int)(BA.ObjectToNumber(_obj2.RunMethod("size")));
 //BA.debugLineNum = 85;BA.debugLine="For i = 0 To size -1";
{
final int step6 = 1;
final int limit6 = (int) (_size-1);
_i = (int) (0) ;
for (;(step6 > 0 && _i <= limit6) || (step6 < 0 && _i >= limit6) ;_i = ((int)(0 + _i + step6))  ) {
 //BA.debugLineNum = 86;BA.debugLine="Obj3.Target = Obj2.RunMethod2(\"get\", i, \"java.la";
_obj3.Target = _obj2.RunMethod2("get",BA.NumberToString(_i),"java.lang.int");
 //BA.debugLineNum = 87;BA.debugLine="size = Obj2.RunMethod(\"size\")";
_size = (int)(BA.ObjectToNumber(_obj2.RunMethod("size")));
 //BA.debugLineNum = 88;BA.debugLine="Obj3.Target = Obj3.GetField(\"applicationInfo\") '";
_obj3.Target = _obj3.GetField("applicationInfo");
 //BA.debugLineNum = 89;BA.debugLine="idt= Obj3.GetField(\"nativeLibraryDir\")";
_idt = BA.ObjectToString(_obj3.GetField("nativeLibraryDir"));
 //BA.debugLineNum = 90;BA.debugLine="flags = Obj3.GetField(\"flags\")";
_flags = (int)(BA.ObjectToNumber(_obj3.GetField("flags")));
 //BA.debugLineNum = 91;BA.debugLine="packName = Obj3.GetField(\"packageName\")";
_packname = BA.ObjectToString(_obj3.GetField("packageName"));
 //BA.debugLineNum = 92;BA.debugLine="sdk= Obj3.GetField(\"targetSdkVersion\")";
_sdk = (int)(BA.ObjectToNumber(_obj3.GetField("targetSdkVersion")));
 //BA.debugLineNum = 93;BA.debugLine="apath= Obj3.GetField(\"sourceDir\")";
_apath = BA.ObjectToString(_obj3.GetField("sourceDir"));
 //BA.debugLineNum = 95;BA.debugLine="If Bit.And(flags, 1)  = 0 Then";
if (anywheresoftware.b4a.keywords.Common.Bit.And(_flags,(int) (1))==0) { 
 //BA.debugLineNum = 97;BA.debugLine="args(0) = Obj3.Target";
_args[(int) (0)] = _obj3.Target;
 //BA.debugLineNum = 98;BA.debugLine="Types(0) = \"android.content.pm.ApplicationInfo\"";
_types[(int) (0)] = "android.content.pm.ApplicationInfo";
 //BA.debugLineNum = 99;BA.debugLine="name1 = Obj1.RunMethod4(\"getApplicationLabel\",";
_name1 = BA.ObjectToString(_obj1.RunMethod4("getApplicationLabel",_args,_types));
 //BA.debugLineNum = 100;BA.debugLine="icon = Obj1.RunMethod4(\"getApplicationIcon\", ar";
_icon.setObject((android.graphics.Bitmap)(_obj1.RunMethod4("getApplicationIcon",_args,_types)));
 //BA.debugLineNum = 101;BA.debugLine="version=pak.GetVersionName(packName)";
_version = _pak.GetVersionName(_packname);
 //BA.debugLineNum = 102;BA.debugLine="plist.Add(version)";
_plist.Add((Object)(_version));
 //BA.debugLineNum = 103;BA.debugLine="count.PutSimple(packName,version)";
_count._putsimple(_packname,(Object)(_version));
 };
 }
};
 //BA.debugLineNum = 108;BA.debugLine="End Sub";
return "";
}
public static String  _pe_packageadded(String _package,anywheresoftware.b4a.objects.IntentWrapper _intent) throws Exception{
int _v1 = 0;
int _v2 = 0;
String _t = "";
 //BA.debugLineNum = 54;BA.debugLine="Sub pe_PackageAdded (Package As String, Intent As";
 //BA.debugLineNum = 55;BA.debugLine="Log(Intent.ExtrasToString)";
anywheresoftware.b4a.keywords.Common.Log(_intent.ExtrasToString());
 //BA.debugLineNum = 56;BA.debugLine="plist.Clear";
_plist.Clear();
 //BA.debugLineNum = 57;BA.debugLine="plist.Add(Package)";
_plist.Add((Object)(_package));
 //BA.debugLineNum = 58;BA.debugLine="cast.SetInfo(\"New: \"&Package&\" backup?\",\"Start cr";
_cast.SetInfo(processBA,"New: "+_package+" backup?","Start creating backup?",(Object)(mostCurrent._main.getObject()));
 //BA.debugLineNum = 59;BA.debugLine="cast.Notify(0)";
_cast.Notify((int) (0));
 //BA.debugLineNum = 60;BA.debugLine="Dim v1,v2 As Int";
_v1 = 0;
_v2 = 0;
 //BA.debugLineNum = 61;BA.debugLine="v1=pak.GetVersionCode(Package)";
_v1 = _pak.GetVersionCode(_package);
 //BA.debugLineNum = 62;BA.debugLine="For Each t As String In dtime.ListKeys";
{
final anywheresoftware.b4a.BA.IterableList group8 = _dtime._listkeys();
final int groupLen8 = group8.getSize()
;int index8 = 0;
;
for (; index8 < groupLen8;index8++){
_t = BA.ObjectToString(group8.Get(index8));
 //BA.debugLineNum = 63;BA.debugLine="v2=pak.GetVersionCode(t)";
_v2 = _pak.GetVersionCode(_t);
 //BA.debugLineNum = 65;BA.debugLine="If v1>v2 Then";
if (_v1>_v2) { 
 //BA.debugLineNum = 66;BA.debugLine="cast.SetInfo(Package&\" newer App Version found!";
_cast.SetInfo(processBA,_package+" newer App Version found!","your backup is older than the installed Version of "+_package,(Object)(mostCurrent._main.getObject()));
 //BA.debugLineNum = 67;BA.debugLine="cast.Notify(0)";
_cast.Notify((int) (0));
 }else {
 //BA.debugLineNum = 69;BA.debugLine="ToastMessageShow(\"Version: Ok\",False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Version: Ok"),anywheresoftware.b4a.keywords.Common.False);
 };
 }
};
 //BA.debugLineNum = 72;BA.debugLine="End Sub";
return "";
}
public static String  _pe_packageremoved(String _package,anywheresoftware.b4a.objects.IntentWrapper _intent) throws Exception{
 //BA.debugLineNum = 47;BA.debugLine="Sub PE_PackageRemoved (Package As String, Intent A";
 //BA.debugLineNum = 48;BA.debugLine="Log(\"PackageRemoved: \" & Package)";
anywheresoftware.b4a.keywords.Common.Log("PackageRemoved: "+_package);
 //BA.debugLineNum = 49;BA.debugLine="Log(Intent.ExtrasToString)";
anywheresoftware.b4a.keywords.Common.Log(_intent.ExtrasToString());
 //BA.debugLineNum = 50;BA.debugLine="cast.SetInfo(Package&\" removed!\",\"Start APK Backu";
_cast.SetInfo(processBA,_package+" removed!","Start APK Backup ?",(Object)(mostCurrent._main.getObject()));
 //BA.debugLineNum = 51;BA.debugLine="cast.Notify(0)";
_cast.Notify((int) (0));
 //BA.debugLineNum = 52;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Dim PE As PhoneEvents";
_pe = new anywheresoftware.b4a.phone.PhoneEvents();
 //BA.debugLineNum = 10;BA.debugLine="Dim PhoneId As PhoneId";
_phoneid = new anywheresoftware.b4a.phone.Phone.PhoneId();
 //BA.debugLineNum = 11;BA.debugLine="Private cast As Notification";
_cast = new anywheresoftware.b4a.objects.NotificationWrapper();
 //BA.debugLineNum = 12;BA.debugLine="Dim plist As List";
_plist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 13;BA.debugLine="Dim pak As PackageManager";
_pak = new anywheresoftware.b4a.phone.PackageManagerWrapper();
 //BA.debugLineNum = 14;BA.debugLine="Dim Obj1, Obj2, Obj3,obj4,obj5,obj6 As Reflector";
_obj1 = new anywheresoftware.b4a.agraham.reflection.Reflection();
_obj2 = new anywheresoftware.b4a.agraham.reflection.Reflection();
_obj3 = new anywheresoftware.b4a.agraham.reflection.Reflection();
_obj4 = new anywheresoftware.b4a.agraham.reflection.Reflection();
_obj5 = new anywheresoftware.b4a.agraham.reflection.Reflection();
_obj6 = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 15;BA.debugLine="Dim size,flags,csize,sdk,itime As Int";
_size = 0;
_flags = 0;
_csize = 0;
_sdk = 0;
_itime = 0;
 //BA.debugLineNum = 16;BA.debugLine="Private name1,l,Types(1),packName,idt,apath As St";
_name1 = "";
_l = "";
_types = new String[(int) (1)];
java.util.Arrays.fill(_types,"");
_packname = "";
_idt = "";
_apath = "";
 //BA.debugLineNum = 17;BA.debugLine="Dim args(1) As Object";
_args = new Object[(int) (1)];
{
int d0 = _args.length;
for (int i0 = 0;i0 < d0;i0++) {
_args[i0] = new Object();
}
}
;
 //BA.debugLineNum = 18;BA.debugLine="Dim icon As Bitmap";
_icon = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private applist As List";
_applist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 20;BA.debugLine="Dim version As String";
_version = "";
 //BA.debugLineNum = 21;BA.debugLine="Private datas,dtime,count As KeyValueStore";
_datas = new com.apkbackup.de.keyvaluestore();
_dtime = new com.apkbackup.de.keyvaluestore();
_count = new com.apkbackup.de.keyvaluestore();
 //BA.debugLineNum = 22;BA.debugLine="Dim time,date As String";
_time = "";
_date = "";
 //BA.debugLineNum = 23;BA.debugLine="Dim dir As String = File.DirRootExternal&\"/APEX\"";
_dir = anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/APEX";
 //BA.debugLineNum = 24;BA.debugLine="End Sub";
return "";
}
public static String  _service_create() throws Exception{
 //BA.debugLineNum = 27;BA.debugLine="Sub Service_Create";
 //BA.debugLineNum = 28;BA.debugLine="time=DateTime.Time(DateTime.Now)";
_time = anywheresoftware.b4a.keywords.Common.DateTime.Time(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 29;BA.debugLine="DateTime.TimeFormat=\"HH:mm\"";
anywheresoftware.b4a.keywords.Common.DateTime.setTimeFormat("HH:mm");
 //BA.debugLineNum = 30;BA.debugLine="DateTime.DateFormat=\"dd/MM/yyyy\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("dd/MM/yyyy");
 //BA.debugLineNum = 31;BA.debugLine="date=DateTime.Date(DateTime.Now)";
_date = anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 32;BA.debugLine="PE.InitializeWithPhoneState(\"PE\",PhoneId)";
_pe.InitializeWithPhoneState(processBA,"PE",_phoneid);
 //BA.debugLineNum = 33;BA.debugLine="cast.Initialize";
_cast.Initialize();
 //BA.debugLineNum = 34;BA.debugLine="cast.Icon=\"icon\"";
_cast.setIcon("icon");
 //BA.debugLineNum = 35;BA.debugLine="plist.Initialize";
_plist.Initialize();
 //BA.debugLineNum = 36;BA.debugLine="applist.Initialize";
_applist.Initialize();
 //BA.debugLineNum = 37;BA.debugLine="datas.Initialize(File.DirDefaultExternal,\"datasto";
_datas._initialize(processBA,anywheresoftware.b4a.keywords.Common.File.getDirDefaultExternal(),"datastore_1");
 //BA.debugLineNum = 38;BA.debugLine="dtime.Initialize(File.DirDefaultExternal,\"datasto";
_dtime._initialize(processBA,anywheresoftware.b4a.keywords.Common.File.getDirDefaultExternal(),"datastore_2");
 //BA.debugLineNum = 39;BA.debugLine="count.Initialize(File.DirDefaultExternal,\"datasto";
_count._initialize(processBA,anywheresoftware.b4a.keywords.Common.File.getDirDefaultExternal(),"datastore_pak");
 //BA.debugLineNum = 40;BA.debugLine="End Sub";
return "";
}
public static String  _service_destroy() throws Exception{
 //BA.debugLineNum = 74;BA.debugLine="Sub Service_Destroy";
 //BA.debugLineNum = 76;BA.debugLine="End Sub";
return "";
}
public static String  _service_start(anywheresoftware.b4a.objects.IntentWrapper _startingintent) throws Exception{
 //BA.debugLineNum = 42;BA.debugLine="Sub Service_Start (StartingIntent As Intent)";
 //BA.debugLineNum = 43;BA.debugLine="cast.SetInfo(\"FileWatcher Service: Start\",date&\"";
_cast.SetInfo(processBA,"FileWatcher Service: Start",_date+" - "+_time,(Object)(mostCurrent._main.getObject()));
 //BA.debugLineNum = 44;BA.debugLine="cast.Notify(0)";
_cast.Notify((int) (0));
 //BA.debugLineNum = 45;BA.debugLine="End Sub";
return "";
}
public static String  _version_check(String _pack) throws Exception{
 //BA.debugLineNum = 110;BA.debugLine="Sub version_check(Pack As String)";
 //BA.debugLineNum = 112;BA.debugLine="End Sub";
return "";
}
}

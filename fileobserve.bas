Type=Service
Version=7.01
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Region  Service Attributes 
	#StartAtBoot: false
	
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.
	Dim PE As PhoneEvents
	Dim PhoneId As PhoneId
  	Private cast As Notification
	Dim plist As List 
	Dim pak As PackageManager
	Dim Obj1, Obj2, Obj3,obj4,obj5,obj6 As Reflector
	Dim size,flags,csize,sdk,itime As Int
	Private name1,l,Types(1),packName,idt,apath As String
	Dim args(1) As Object
	Dim icon As Bitmap
	Private applist As List 
	Dim version As String 
	Private datas,dtime,count As KeyValueStore
	Dim time,date As String
	Dim dir As String = File.DirRootExternal&"/APEX"
End Sub 


Sub Service_Create
	time=DateTime.Time(DateTime.Now)
	DateTime.TimeFormat="HH:mm"
	DateTime.DateFormat="dd/MM/yyyy"
	date=DateTime.Date(DateTime.Now)
	PE.InitializeWithPhoneState("PE",PhoneId)
	cast.Initialize
	cast.Icon="icon"
	plist.Initialize
	applist.Initialize
	datas.Initialize(File.DirDefaultExternal,"datastore_1")
	dtime.Initialize(File.DirDefaultExternal,"datastore_2")
	count.Initialize(File.DirDefaultExternal,"datastore_pak")
End Sub

Sub Service_Start (StartingIntent As Intent)
	cast.SetInfo("FileWatcher Service: Start",date&" - "&time,Main)
	cast.Notify(0)
End Sub

Sub PE_PackageRemoved (Package As String, Intent As Intent)
	Log("PackageRemoved: " & Package)
	Log(Intent.ExtrasToString)
	cast.SetInfo(Package&" removed!","Start APK Backup ?",Main)
	cast.Notify(0)
End Sub

Sub pe_PackageAdded (Package As String, Intent As Intent)
	Log(Intent.ExtrasToString)
	plist.Clear
	plist.Add(Package)
	cast.SetInfo("New: "&Package&" backup?","Start creating backup?",Main)
	cast.Notify(0)
	Dim v1,v2 As Int
	v1=pak.GetVersionCode(Package)
	For Each t As String In dtime.ListKeys
		v2=pak.GetVersionCode(t)
	
		If v1>v2 Then
			cast.SetInfo(Package&" newer App Version found!","your backup is older than the installed Version of "&Package,Main)
			cast.Notify(0)
		Else
			ToastMessageShow("Version: Ok",False)
		End If
	Next
End Sub

Sub Service_Destroy

End Sub

Sub apps_start
	
	applist=pak.GetInstalledPackages
	Obj1.Target = Obj1.GetContext
	Obj1.Target = Obj1.RunMethod("getPackageManager") ' PackageManager
	Obj2.Target = Obj1.RunMethod2("getInstalledPackages", 0, "java.lang.int")
	size = Obj2.RunMethod("size")
	For i = 0 To size -1
		Obj3.Target = Obj2.RunMethod2("get", i, "java.lang.int") ' PackageInfo
		size = Obj2.RunMethod("size")
		Obj3.Target = Obj3.GetField("applicationInfo") ' ApplicationInfo
		idt= Obj3.GetField("nativeLibraryDir")
		flags = Obj3.GetField("flags")
		packName = Obj3.GetField("packageName")
		sdk= Obj3.GetField("targetSdkVersion")
		apath= Obj3.GetField("sourceDir")
		'itime=Obj2.RunMethod("firstInstallTime")
		If Bit.And(flags, 1)  = 0 Then
			'app is not in the system image
			args(0) = Obj3.Target
			Types(0) = "android.content.pm.ApplicationInfo"
			name1 = Obj1.RunMethod4("getApplicationLabel", args, Types)
			icon = Obj1.RunMethod4("getApplicationIcon", args, Types)
			version=pak.GetVersionName(packName)
			plist.Add(version)
			count.PutSimple(packName,version)
		End If
			
	Next

End Sub

Sub version_check(Pack As String)
	
End Sub
Type=Activity
Version=7.01
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: False
	#IncludeTitle: True
#End Region
#Extends: de.amberhome.materialdialogs.MaterialDialogsActivity
Sub Process_Globals
	
End Sub

Sub Globals
	Private mcl As MaterialColors
	Private Label1 As Label
	Private datas,setting As KeyValueStore
	Dim dir As String = File.DirRootExternal&"/APEX/id"
	Dim dir1 As String =File.DirRootExternal&"/APEX"
	Dim path As String 
	Dim intern,sd As String 
	Private files As MLfiles 
	Dim pak As PackageManager
	'Private axt As Spinner
	Private b1 As  	ACButton
	'Private loglist As ListView
	Private infolist As List
	Private kbv1 As bcCircularProgressView
	Private Label2 As Label
	'Private cb1 As bcCheckBox
	Private dia,dia2 As BetterDialogs
	Dim text As Label
	Dim font As Typeface
	
	Dim subtext As String
	Dim subtext2 As String
	Dim subtext3 As String
	Dim fman As MaterialDialogsManager
	Dim mm As List
	Dim id As List
	Dim sp As Spinner
	Dim chk,chkb As ACCheckBox
	Dim lv As ListView
	Dim mainp As Panel 
	Dim titel As String="Settings/Info"
	Dim subtitel As String="Backup settings"
	Dim ht,st As Label
	Private parents As Panel
	Private Top As Int
	Dim sptext,atext As Label
	Dim ff As ClsExplorer
	Dim manager As PreferenceManager
	Dim screen As PreferenceScreen
	Dim lbl,serv As Label
	Dim font As Typeface=Typeface.LoadFromAssets("OpenSans.ttf")
	Dim switch As ACSwitch
End Sub

Sub Activity_Create(FirstTime As Boolean)
	datas.Initialize(File.DirDefaultExternal,"datastore_1")
	setting.Initialize(File.DirDefaultExternal,"datastore_main")
	b1.Initialize("b1")
	sp.Initialize("sp")
	chk.Initialize("chk")
	chkb.Initialize("chkb")
	lv.Initialize("lv")
	ht.Initialize("")
	st.Initialize("")
	serv.Initialize("")
	serv.Textsize=13
	serv.TextColor=mcl.md_grey_600
	atext.Initialize("atext")
	fman.Initialize("fman")
	switch.Initialize("switch")
	switch.Typeface=font
	serv.Typeface=font
	serv.Text="Switch FileWatcher Service On/Off"
	switch.TextColor=Colors.ARGB(120,255,255,255)
	serv.TextColor=Colors.ARGB(120,255,255,255)
	serv.Gravity=Gravity.CENTER_VERTICAL
	ht.Text=titel
	sp.Initialize("sp")
	ht.TextSize=30
	ht.TextColor=mcl.md_white_1000
	st.Text=subtitel
	st.TextSize=18
	st.Typeface=font.DEFAULT_BOLD
	st.TextColor=Colors.ARGB(150,255,255,255)
	sptext.Initialize("sptext")
	sptext.Text=subtitel
	sptext.TextColor=mcl.md_amber_700
	atext.Color=Colors.ARGB(0,255,255,255)
	sptext.Color=Colors.ARGB(0,255,255,255)
	atext.Gravity=Gravity.CENTER_VERTICAL
	chkb.TextSize=12
	sptext.TextSize=15
	sptext.Typeface=font
	b1.Typeface=font
	atext.TextSize=15
	atext.Typeface=font
	sptext.Gravity=Gravity.CENTER_HORIZONTAL
	ht.Gravity=Gravity.CENTER_HORIZONTAL
	ht.Typeface=font
	mainp.Initialize("mainp")
	lv.Enabled=True
	lbl.Initialize("lbl")
	lbl.Typeface=font
	lbl.TextSize=15
	lbl.Gravity=Gravity.FILL
	lbl.Text="Use APK Backup default folder"
	lbl.TextColor=Colors.LightGray
	lbl.Color=Colors.ARGB(0,255,255,255)

	Activity.Color=Colors.ARGB(255,55,71,79)
	Activity.AddView(mainp,0,0,98%x,30dip)
	Activity.AddView(ht,0dip,0dip,98%x,37dip)
	Activity.AddView(sptext,1dip,ht.Height+5dip,98%x,35dip)
	Activity.AddView(lbl,15dip,mainp.Height+sptext.Height+30dip,98%x,50dip)
	Activity.AddView(chkb,80%x,mainp.Height+sptext.Height+29dip,50dip,50dip)
	Activity.AddView(sp,0,26%y,100%x,50dip)
	Activity.AddView(lv,1dip,38%y,99%x,250dip)
	Activity.AddView(b1,30%x,90%y,120dip,60dip)
	Activity.AddView(switch,67%x,mainp.Height+sptext.Height+lbl.Height+sp.Height+40dip,80dip,25dip)
	Activity.AddView(serv,15dip,mainp.Height+sptext.Height+sp.Height+lbl.Height+30dip,98%x,50dip)
	AddDivider(7%x,mainp.Height+sptext.Height,85%x,1dip)
	AddDivider(0,mainp.Height+sptext.Height+sp.Height+25dip,100%x,1dip)
	AddDivider(0,mainp.Height+sptext.Height+sp.Height+lbl.Height+30dip,100%x,1dip)
	'chkb.Color=Colors.ARGB(10,255,255,255)
	infolist.Initialize
	id.Initialize
	
	mainp.Enabled=True
	sp.Enabled=True
	chk.Enabled=True
	lv.Enabled=True
	st.Enabled=True
	ht.Enabled=True
	sptext.Enabled=True
	mm.Initialize
	mainp.Color=Colors.Transparent
	text.Initialize("text")
	text.Color=Colors.Transparent
	text.TextColor=mcl.md_grey_600
	text.TextSize=15
	sp.TextSize=11
	sp.Add(datas.GetSimple("0"))
	sp.DropdownBackgroundColor=Colors.White
	sp.DropdownTextColor=Colors.Black
	sp.Color=Colors.Transparent
	sp.TextColor=Colors.LightGray
	sp.Prompt="Select Backup folder"
	sd=files.Sdcard
	intern=File.DirDefaultExternal
	Dim sdready As String = files.SdcardReady
	If sdready="mounted" Then 
		File.WriteString(File.DirRootExternal,"sdLog.txt",files.GetDiskstats(sd))
		 datas.PutSimple("0",sd&"APEX")
	Else
		File.WriteString(File.DirRootExternal,"sdLog.txt",files.GetDiskstats(intern))
		datas.PutSimple("0",intern&"/APEX")
	End If 
	b1.TextSize=12
	b1.TextColor=mcl.md_white_1000
	b1.ButtonColor=mcl.md_grey_800
	b1.Text="save"
	ff.BackgroundColor=mcl.md_grey_700
	
	If SaveModule.RestoreState(Activity, "sett",0) = False Then
		'set the default values
		Log("load default")
		sp.Enabled=False
		chkb.Checked=True
		switch.Checked=False
		sp.Color=Colors.ARGB(30,255,255,0)
		sp.TextColor=mcl.md_grey_200
		sp.Enabled=False
		lbl.TextColor=Colors.LightGray
		serv.TextColor=Colors.LightGray
	End If
	
	
	Dim AutoUpdate As Boolean
	AutoUpdate = SaveModule.GetSetting2("AutoUpdate", False)
	
	
End Sub

Sub Activity_Resume
	
End Sub

Sub Activity_Pause (UserClosed As Boolean)
	If UserClosed Then
		SaveModule.ResetState("sett")
	Else
		SaveModule.SaveState(Activity, "sett")
	End If
	SaveModule.SaveSettings
End Sub

Sub Activity_KeyPress(keyCode As Int) As Boolean
	If keyCode=KeyCodes.KEYCODE_BACK Then 
		
		CallSub(Main,"low_start")
	End If 
	Return False
End Sub
Sub reloop_set
	'CallSubDelayed(Main,"file_check")
	StartActivity(Main)
End Sub

Sub switch_CheckedChange(Checked As Boolean)
	If Checked=True Then 
		SaveModule.SetSetting("Autoupdate",Checked)
		StartService(fileobserve)
		ToastMessageShow("FileWatcher started..",False)
		
		switch.TextOn="Service ON"
	Else
		SaveModule.SetSetting("Autoupdate",Checked)
		CancelScheduledService(fileobserve)
		switch.TextOff="Service OFF"
		StopService(fileobserve)
		ToastMessageShow("FileWatcher finished!",False)
	End If
End Sub

Sub check1_CheckedChange(Checked As Boolean)
	SaveModule.SetSetting("Autoupdate",Checked)
End Sub
Sub edit1_TextChanged (Old As String, New As String)
	datas.Remove("0")
	datas.PutSimple("0",New)
End Sub

Sub sp_ItemClick (Position As Int, Value As Object)
	'ff.CustomDialog("folder",100%x,100%y,folder,100%x,100%y,0,mcl.md_grey_700,"select","cancel","",False,"fold")
	If Position=0 Then 
		explor_set
	End If 
End Sub

Sub explor_set
	b1.SetVisibleAnimated(600,False)
	Dim directory As String
	dir1 =File.DirRootExternal&"/APEX"
	ff.Initialize(Activity,dir1,".apk", True, True, "OK")
	ff.Explorer2(True)
	ff.FastScrollEnabled=True
	If Not (ff.Selection.Canceled Or ff.Selection.ChosenPath="") Then
		If datas.ContainsKey("0") Then
			datas.Remove("0")
			datas.PutSimple("0",ff.Selection.ChosenPath)
			b1.SetVisibleAnimated(500,True)
		Else
			datas.PutSimple("0",ff.Selection.ChosenPath)
			b1.SetVisibleAnimated(500,True)
		End If
	Else
		ToastMessageShow("canceled..",False)
	End If
End Sub

Sub list1_ItemClick (Position As Int, Value As Object)
	Log("click")
	For i = 0 To mm.Size-1
		Value=mm.Get(i)
		Log("Set-Key: "&i)
	Next
	If Position=1 Then 
		sd_read
	End If
	If Position=0 Then
		about
	End If

End Sub

Sub data_check
	b1.SetVisibleAnimated(450,True)
	
End Sub

Sub b1_Click
	ProgressDialogShow("Saving settings...")
	SaveModule.SaveState(Activity, "sett")
	SaveModule.SaveSettings
	StartActivity(Main)
	Activity.Finish
End Sub

Sub chkb_CheckedChange(Checked As Boolean)
		If Checked=True Then 
		sp.Enabled=False
		setting.PutSimple("c1",Checked)
		sp.Color=Colors.ARGB(30,255,255,0)
		sp.TextColor=mcl.md_grey_200
		sp.Enabled=False
		lbl.TextColor=Colors.LightGray
		serv.TextColor=Colors.LightGray
	Else
		datas.DeleteAll
		chkb.Color=Colors.ARGB(0,255,255,255)
		switch.Color=Colors.ARGB(20,255,255,255)
		lbl.TextColor=Colors.ARGB(100,255,255,255)
		sp.Enabled=True
		sp.color=Colors.ARGB(5,255,255,255)
	End If 
End Sub

Sub about
	Dim box As BetterDialogs
	box.MsgBox("About APK Backup(Pro)",subtext&", "&subtext2&", "&subtext3,"<i>Close</i>","","",LoadBitmap(File.DirAssets,"ic_description_white_18dp.png"))
End Sub

Sub sd_read
	If File.Exists(dir1,"sdLog.txt") Then 
		Try
			text.Text=File.ReadString(dir1,"sdLog.txt")
		Catch
			Log(LastException)
		End Try
	End If
	dia.MsgBox("<h5><font color="&mcl.md_amber_600&">SD Card Info:</font></h5>","<h>"&text.Text&"</h>","","<i><b>GotIt</i></b>","",LoadBitmap(File.DirAssets,"ic_sd_storage_white_48dp.png"))
End Sub

Sub AddDivider(left As Int,Top1 As Int, width As Int, hight As Int ) 
	
	Dim lb As Label
	lb.Initialize("")
	lb.Color = Colors.ARGB(85,255,255,255)'mcl.md_amber_400
	Activity.AddView(lb,left,Top1,width,1dip)
	
End Sub
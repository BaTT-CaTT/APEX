Type=Activity
Version=6.8
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: False
	#IncludeTitle: True
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	Private mcl As MaterialColors
	Private Label1 As Label
	Private Panel2 As Panel
	Private datas As KeyValueStore
	Dim dir As String = File.DirRootExternal&"/APEX/id"
	Dim dir1 As String =File.DirRootExternal&"/APEX"
	Dim path As String 
	Dim intern,sd As String 
	Private files As MLfiles 
	Private Label2 As Label
	Dim fils As FileSelect
	Dim dlgFileExpl As ClsExplorer
	Dim pak As PackageManager
	Private axt As Spinner
	Private b1 As Button
	Private mct As msCheckedTextView
	Private loglist As ListView
	Private mst As msCheckedTextView
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("sett")
	Activity.AddMenuItem3("Licenses","liz",LoadBitmap(File.DirAssets,"street-1.png"),True)
	Activity.Color=mcl.md_blue_grey_800
	datas.Initialize(File.DirDefaultExternal,"datastore_1")
	'filo.ShowOnlyFolders=True
	axt.Color=Colors.ARGB(110,255,255,255)
	axt.Clear
	axt.Prompt=datas.GetSimple("0")
	''axt.TextColor=mcl.md_white_1000
	axt.Color=Colors.Transparent
	axt.TextSize=14
	sd=files.GetExtSd
	intern=File.DirRootExternal
	Label1.TextSize=18
	Label1.Text="Set your Backup Path:"
	Dim sdready As String = files.SdcardReady
	If sdready="mounted" Then 
	mst.Text=files.GetDiskstats(sd)
	Else
		mst.Text=files.GetDiskstats(intern)
	End If 
	
	Dim l1,l2 As Label 
	l1.Initialize("l1")
	l2.Initialize("l2")
	l1=loglist.TwoLinesAndBitmap.Label
	l2=loglist.TwoLinesAndBitmap.SecondLabel
	l1.TextSize=15
	l1.TextColor=mcl.md_amber_A400
	l2.TextColor=mcl.md_white_1000
	l2.TextSize=13
	loglist.TwoLinesAndBitmap.ItemHeight=50dip
	loglist.TwoLinesAndBitmap.ImageView.Height=36dip
	loglist.TwoLinesAndBitmap.ImageView.Width=36dip
	loglist.AddTwoLinesAndBitmap("System Info:","aviable on next Update....",LoadBitmap(File.DirAssets,"blueprint.png"))
	loglist.AddTwoLinesAndBitmap2("License Info:","APEX & Google License-Agreement",LoadBitmap(File.DirAssets,"street-1.png"),3)
	
	
	
	
	mst.TextColor=mcl.md_amber_A400
	mst.Textsize=13.5
	mst.Checked=True
	data_check
End Sub

Sub Activity_Resume
	data_check
End Sub

Sub Activity_Pause (UserClosed As Boolean)
	If dlgFileExpl.IsInitialized Then
		If dlgFileExpl.IsActive Then
			 Return
		End If
	End If
End Sub

Sub Activity_BackKeyPressed As Boolean
	If dlgFileExpl.IsInitialized Then
		If dlgFileExpl.IsActive Then 
			Return True 
		End If
	End If 
End Sub

Sub call_start
	Panel2.Visible=False
	mst.SetVisibleAnimated(80,False)
	'label2.SetVisibleAnimated(80,False)
	b1.SetVisibleAnimated(80,False)
	loglist.SetVisibleAnimated(80,False)
	file_set
End Sub

Sub file_set
	
	dlgFileExpl.Initialize(Activity,intern, Null,True, True, "Select")
	dlgFileExpl.FastScrollEnabled = True
	dlgFileExpl.Explorer2(True)
	Dim pathname As String=dlgFileExpl.Selection.ChosenPath
	If Not(dlgFileExpl.Selection.Canceled Or dlgFileExpl.Selection.ChosenPath = "") Then
		Log("User Path: "&dlgFileExpl.Selection.ChosenPath)
		datas.DeleteAll
		axt.Clear
		datas.PutSimple("0",pathname)
		axt.Add(datas.GetSimple("0"))
		Panel2.Visible=True
	
		b1.SetVisibleAnimated(80,True)
		mst.SetVisibleAnimated(80,True)
		loglist.SetVisibleAnimated(80,True)
	End If
End Sub

Sub data_check
	axt.Clear
	Dim fpath As String 
	For Each s As String In datas.ListKeys
		fpath=datas.GetSimple(s)
		Next 
	axt.Add(fpath)
End Sub

Sub axt_TextChanged (Old As String, New As String)

End Sub

Sub axt_ItemClick (Position As Int, Value As Object)
	Panel2.Visible=False
	mst.SetVisibleAnimated(80,False)
	b1.SetVisibleAnimated(80,False)
	loglist.SetVisibleAnimated(80,False)
	call_start
End Sub

Sub b1_Click
	
	StartActivity(Main)
	Animator.SetAnimati("slide_from_left","slide_to_left")
End Sub

Sub loglist_ItemClick (Position As Int, Value As Object)
		If Value=3 Then 
		Msgbox(pak.GetApplicationLabel("com.apex")&", Version: "&pak.GetVersionName("com.apex")&" | Build: "&pak.GetVersionCode("com.apex")&"| Written in: B4A. Java is a Open-Source Software and is subject to the free GNU Public license. Android is under the google license, all associated names and content are protected by the Google Inc. Software agreement. For more information, visit www.google.com/policies/terms . All rights To the code And the design are reserved To BaTTCaTT And its owners.Code by D. Trojan, published by SuloMedia™ www.battcatt.bplaced.net For Recent Infos. All Rights Reserved APEX ©2017","License Info:")
		End If
End Sub

Sub mst_onClick(v As Object)
	
End Sub
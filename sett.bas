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
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("sett")
	Activity.Color=mcl.md_blue_grey_800
	datas.Initialize(File.DirDefaultExternal,"datastore_1")
	'filo.ShowOnlyFolders=True
	axt.Color=Colors.ARGB(120,255,255,255)
	axt.Clear
	axt.Prompt=datas.GetSimple("0")
	''axt.TextColor=mcl.md_white_1000
	axt.Color=Colors.Transparent
	sd=files.GetExtSd
	intern=File.DirRootExternal
	Label1.Text="Set Your Apk Backup Path:"
	mct.Text=pak.GetApplicationLabel("com.apex")&", Version: "&pak.GetVersionName("com.apex")&" | Build: "&pak.GetVersionCode("com.apex")&"| Written in: B4A. Java is a Open-Source Software and is subject to the free GNU Public license. Android is under the google license, all associated names and content are protected by the Google Inc. Software agreement. For more information, visit www.google.com/policies/terms . All rights To the code And the design are reserved To BaTTCaTT And its owners.Code by D. Trojan, published by SuloMedia™ www.battcatt.bplaced.net For Recent Infos. All Rights Reserved APEX ©2017"
End Sub

Sub Activity_Resume
	axt.Clear
	axt.Add(datas.GetSimple("0"))
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
		If dlgFileExpl.IsActive Then Return True
	End If 
End Sub

Sub file_set
	axt.Clear
	dlgFileExpl.Initialize(Activity,intern, Null,False, True, "Select Folder")
	dlgFileExpl.FastScrollEnabled = True
	dlgFileExpl.Explorer2(True)
	If Not(dlgFileExpl.Selection.Canceled Or dlgFileExpl.Selection.ChosenPath = "") Then
		Log(dlgFileExpl.Selection.ChosenPath)
		datas.PutSimple("0",dlgFileExpl.Selection.ChosenPath)
		axt.Add(datas.GetSimple("0"))
		Panel2.Visible=True
		Label2.SetVisibleAnimated(80,True)
		b1.SetVisibleAnimated(80,True)
		mct.SetVisibleAnimated(80,True)
	End If
End Sub

Sub data_check
	Dim fpath As String 
	
	If datas.ContainsKey("0") Then 
		fpath=datas.GetSimple("0")
			Label2.Text=fpath
		Else
			fpath=dir1
	Label2.Text=fpath
	End If
End Sub

Sub axt_TextChanged (Old As String, New As String)

End Sub

Sub axt_ItemClick (Position As Int, Value As Object)
	Panel2.Visible=False
	mct.SetVisibleAnimated(80,False)
	Label2.SetVisibleAnimated(80,False)
	b1.SetVisibleAnimated(80,False)
	file_set
End Sub

Sub b1_Click
	Activity.Finish
End Sub
Type=Activity
Version=7.01
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: False
	#IncludeTitle: True
#End Region

Sub Process_Globals
	
	Dim t3 As Timer
End Sub

Sub Globals


	Dim co As Int
	Private Label1 As Label
	Dim mcl As MaterialColors

	Private lvg As LVCircularRing
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("reswit")
	t3.Initialize("t3",1000)
	co=0
	t3.Interval=100
	t3.Enabled=False
	Label1.Initialize("")
	Activity.AddView(Label1,5%x,25%y,250dip,85dip)
	Label1.TextColor=mcl.md_white_1000
	Label1.TextSize=25
	Label1.Gravity=Gravity.CENTER_HORIZONTAL
	Label1.Typeface=Typeface.LoadFromAssets("OpenSans.ttf")
	Label1.Text="please wait while reloading Applist..."
	lvg.startAnim
End Sub

Sub Activity_Resume

	If t3.IsInitialized Then 
		t3.Enabled=True
		Activity.Finish
		Else 
		t3.Initialize("t3",1000)
		t3.Enabled=True
		lvg.startAnim
	End If
End Sub

Sub Activity_Pause (UserClosed As Boolean)
	t3.Enabled=False 
	lvg.stopAnim
	
End Sub

Sub t3_Tick
	 Log("tick")
	co=co+1
	If co = 1 Then 
		t3.Enabled=False
		co=0
		StartActivity(Main)
		Animator.setanimati("slide_from_left","slide_to_left")
	End If
End Sub

Sub rebound
	StartActivity(Main)
	Animator.setanimati("slide_from_left","slide_to_left")
End Sub


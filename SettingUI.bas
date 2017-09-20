Type=Class
Version=7.01
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@

Private Sub Class_Globals	
	Private parents As Panel
	Private Top As Int
	Private sFontname As String
	Private manager As AHPreferenceManager
	Private sv As ScrollView
	Private mcl As MaterialColors
End Sub

'Author:www.iranapp.org
'Initializes the object. You can add parameters to this method if needed.
Public Sub Initialize(Parent As Panel,HeaderColor As Int,Fontname As String,Title As String,SubTitle As String)
	
	parents = Parent
	sFontname = Fontname
	sv.Initialize(0)
	
	Dim p As Panel
	p.Initialize("")
	p.Color = HeaderColor
	Parent.AddView(p,0,0,Parent.Width,90dip)
	
	Dim lb As Label
	lb.Initialize("")
	lb.Text = Title
	lb.TextColor = Colors.ARGB(200,255,255,255)
	lb.TextSize = 30
	lb.Typeface = Typeface.LoadFromAssets(Fontname)
	lb.Gravity = Bit.Or(Gravity.CENTER_HORIZONTAL,Gravity.CENTER_VERTICAL)
	parents.AddView(lb,0,10dip,Parent.Width,50dip)
	
	Top = Top + 45dip
	
	Dim lb2 As Label
	lb2.Initialize("")
	lb2.Text = SubTitle
	lb2.TextColor = Colors.RGB(218,218,218)
	lb2.TextSize = 19
	lb2.Typeface = Typeface.LoadFromAssets(Fontname)
	lb2.Gravity = Bit.Or(Gravity.CENTER_HORIZONTAL,Gravity.CENTER_VERTICAL)
	parents.AddView(lb2,0,Top,Parent.Width,50dip)
	
	Top = p.Height + 10dip
	
	Parent.AddView(sv,0,Top,Parent.Width,Parent.Height - p.Height)
	sv.Panel.Width = Parent.Width
	Top = 0
	
End Sub

Public Sub AddCheckbox(Key As String,Title As String,DefaultValue As Boolean)
	
	Dim chk As CheckBox
	chk.Initialize("checkbox")
	chk.Tag = Key
	chk.Checked = DefaultValue
	sv.Panel.AddView(chk,0dip,Top,30dip,30dip)
	
	AddLabel(Title,13,Top+5dip,100%x-chk.Width - 7dip,chk.Height,chk)
	Top = Top + 40dip
	
	If manager.GetAll.ContainsKey(Key) Then chk.Checked = manager.GetBoolean(Key)
	
End Sub

Public Sub AddEditText(Key As String,Hint As String,InputType As Int,SingleLine As Boolean,Password As Boolean)
	
	Dim ed As EditText
	ed.Initialize("edittext")
	ed.Tag = Key
	ed.Hint = Hint
	ed.TextColor = Colors.White
	ed.HintColor = Colors.Gray
	ed.TextSize = 15
	ed.PasswordMode = Password
	ed.Typeface = Typeface.LoadFromAssets(sFontname)
	ed.InputType = InputType
	ed.SingleLine = SingleLine
	ed.Padding = Array As Int(10,10,10,10)
	
	If SingleLine = False Then
		sv.Panel.AddView(ed,7dip,Top,sv.Panel.Width - 14dip,100dip)
		ed.Gravity = Bit.Or(Gravity.TOP,Gravity.LEFT)
	Else
		sv.Panel.AddView(ed,7dip,Top,sv.Panel.Width - 14dip,50dip)
		ed.Gravity = Gravity.LEFT
	End If

	Top = Top + ed.Height + 4dip
	
	If manager.GetAll.ContainsKey(Key) Then ed.Text = manager.GetString(Key)
	
End Sub

Public Sub AddSeekbar(Key As String,Title As String,MaxValue As Int,Value As Int)
	
	AddBullet(Top + 10dip,mcl.md_amber_600)
	AddLabel(Title,15,Top+10dip,sv.Panel.Width - 17dip,20dip,Null)
	
	Top = Top + 30dip
	
	Dim ED As SeekBar
	ED.Initialize("seekbar")
	ED.Tag = Key
	ED.Max = MaxValue
	ED.Value = Value
	sv.Panel.AddView(ED,4dip,Top,sv.Panel.Width - 8dip,30dip)

	Top = Top + ED.Height + 4dip
		
	If manager.GetAll.ContainsKey(Key) Then ED.Value = manager.GetString(Key)
	
End Sub

Public Sub AddSpinner(Key As String,Prompt As String,ListData As List)
	
	AddBullet(Top + 10dip,mcl.md_amber_600)
	AddLabel(Prompt,15,Top+10dip,sv.Panel.Width - 17dip,20dip,Null)
	Top = Top + 45dip
	
	Dim ED As Spinner
	ED.Initialize("spinner")
	ED.Tag = Key
	ED.Prompt = Prompt
	ED.TextColor = Colors.ARGB(60,255,255,255)
	ED.TextSize = 13
	ED.DropdownBackgroundColor = Colors.RGB(225,225,225)
	ED.DropdownTextColor = Colors.Black
	ED.Padding = Array As Int(10,10,10,10)
	ED.AddAll(ListData)
	sv.Panel.AddView(ED,7dip,Top-5dip,sv.Panel.Width - 14dip,50dip)

	Top = Top + ED.Height +2dip
	
	If manager.GetAll.ContainsKey(Key) Then
		Dim su As String
		su = manager.GetString(Key)
		For i = 0 To ListData.Size - 1
			If su = ListData.Get(i) Then ED.SelectedIndex = i
		Next
	End If
	
End Sub

Public Sub AddListview(Key As String,Title As String,ListData As List)
	
	AddBullet(Top + 10dip,mcl.md_amber_600)
	AddLabel(Title,15,Top+10dip,sv.Panel.Width - 17dip,20dip,Null)
	
	Top = Top + 45dip
	
	Dim ED As ListView
	ED.Initialize("listview")
	ED.Tag = Key
	ED.FastScrollEnabled = True
	ED.SingleLineLayout.Label.Gravity = Gravity.LEFT
	ED.SingleLineLayout.Label.TextSize = 14
	ED.SingleLineLayout.Label.TextColor = Colors.ARGB(100,255,255,255)
	ED.SingleLineLayout.Label.Gravity = Bit.Or(Gravity.CENTER_VERTICAL,Gravity.LEFT)
	ED.SingleLineLayout.Label.Left = 0
	ED.SingleLineLayout.Label.Width = sv.Panel.Width-20dip
	ED.SingleLineLayout.ItemHeight=65dip
	Dim lblheight As Int
	lblheight = ED.SingleLineLayout.ItemHeight * ListData.Size
	sv.Panel.AddView(ED,7dip,Top,sv.Panel.Width - 14dip,lblheight)
	
	For i = 0 To ListData.Size - 1
		ED.AddSingleLine(ListData.Get(i))	
	Next
	
	Top = Top + ED.Height + 4dip
	
End Sub

Private Sub AddLabel(Title As String,FontSize As Int,sTop As Int,sWidth As Int,sHeight As Int,DepencyView As View)
	
	Dim lb As Label
	lb.Initialize("lblcheckbox")
	lb.TextColor = Colors.RGB(218,218,218)
	lb.TextSize = FontSize
	lb.Text = Title
	Try
		lb.Tag = DepencyView
	Catch
		
	End Try
	
	lb.Typeface = Typeface.LoadFromAssets(sFontname)
	lb.Gravity = Bit.Or(Gravity.CENTER_HORIZONTAL,Gravity.LEFT+10dip)
	sv.Panel.AddView(lb,30dip,sTop,sWidth-3dip,sHeight)
	
End Sub

Sub AddDivider
	
	Top = Top + 6dip
	Dim lb As Label
	lb.Initialize("")
	lb.Color = Colors.ARGB(60,255,255,255)'mcl.md_amber_400
	sv.Panel.AddView(lb,7dip,Top,sv.Panel.Width - 17dip,1)
	
	Top = Top + 10dip
	
End Sub

Sub AddBullet(sTop As Int,Color As Int)
	
	Dim lb As Label
	lb.Initialize("")
	sv.Panel.AddView(lb,10dip,sTop+6dip,10dip,10dip)
	Dim cd As ColorDrawable
	cd.Initialize(Color,lb.Width/2)
	lb.Background = cd
	
End Sub

Sub SetKeyBoolean(Key As String,Value As Boolean)
	manager.SetBoolean(Key,Value)
End Sub

Sub SetKeyString(Key As String,Value)
	manager.SetString(Key,Value)
End Sub

Sub GetKeyBoolean(Key As String) As Boolean
	Return manager.GetBoolean(Key)
End Sub

Sub GetKeyString(Key As String) As String
	Return manager.GetString(Key)
End Sub

Sub GetAllKey As Map
	Return manager.GetAll
End Sub

Sub ApplyHeightPanel
	sv.Panel.Height = Top	
End Sub

Private Sub lblcheckbox_Click
	
	Dim lb As Label
	lb = Sender
	
	Try
		Dim ch As CheckBox
		ch = lb.Tag
		ch.Checked = Not(ch.Checked)
	Catch
	End Try
	
End Sub

Private Sub checkbox_CheckedChange(Checked As Boolean)
	
	Dim ch As CheckBox
	ch = Sender
	manager.SetBoolean(ch.Tag,Checked)
	
End Sub

Private Sub edittext_TextChanged (Old As String, New As String)
	
	Dim ed As EditText
	ed = Sender
	manager.SetString(ed.Tag,ed.Text)
	
End Sub

Private Sub seekbar_ValueChanged (Value As Int, UserChanged As Boolean)
	
	Dim ed As SeekBar
	ed = Sender
	manager.SetString(ed.Tag,ed.Value)
	
End Sub

Private Sub spinner_ItemClick (Position As Int, Value As Object)
	
	Dim sp As Spinner
	sp = Sender
	manager.SetString(sp.Tag,Value)
	
End Sub

Private Sub listview_ItemClick (Position As Int, Value As Object)
	
	Dim ls As ListView
	ls = Sender
	manager.SetString(ls.Tag,Value)
	
End Sub
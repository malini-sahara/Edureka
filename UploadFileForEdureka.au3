$strPath = $CmdLine[1];
WinWait("[CLASS:#32770]","",3)
ControlSetText("Open","","Edit1",$strPath)
ControlClick("Open","&Open","Button1")
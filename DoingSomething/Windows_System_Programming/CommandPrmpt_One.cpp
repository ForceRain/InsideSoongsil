#include <stdio.h>
#include <stdlib.h>
#include <tchar.h>
#include <locale.h>
#include <windows.h>

#define	STR_LEN	256
#define 	CMD_TOKEN_NUM	10

TCHAR ERROR_CMD[]	=	_T("'%s'은(는) 실행할 수 있는 프로그램이 아닙니다.\n");
TCHAR cmdString[STR_LEN];
TCHAR originString[STR_LEN];
TCHAR cmdTokenList[CMD_TOKEN_NUM][STR_LEN];
TCHAR seps[]	=	_T(" ,\t\n");


int CmdProcessing(void);
TCHAR * StrLower(TCHAR *);

int _tmain(int argc, TCHAR * argv[])
{
	_tsetlocale(LC_ALL,_T("Korean"));

	if ( argc > 1 )
	{
		int tmp_len = 0;
		int i = 0;
		int checkExit = 0;
		for ( i = 1 ; i < argc ; i++ )
		{
			_tcscpy( cmdString+tmp_len, argv[i] );
			tmp_len += _tcslen( argv[i] );
			cmdString[tmp_len] = ' ';
			tmp_len ++;
		}
//		_tprintf( _T("Current command : %s"), cmdString );
		checkExit = CmdProcessing();
		if ( checkExit == TRUE )
		{
			_fputts(_T("명령어 처리를 종료합니다.\n"),stdout);
			return 0;
		}
	}

	DWORD isExit;
	while(1)
	{
		_fputts( _T("Best command prompt >> "), stdout );
		_getts( cmdString );
		isExit = CmdProcessing();
		if ( isExit == TRUE )
		{
			_fputts(_T("명령어 처리를 종료합니다.\n"),stdout);
			break;
		}
	}
	return 0;
}

int CmdProcessing()
{
	_tcscpy( originString, cmdString );

	TCHAR * token = _tcstok(cmdString, seps);
	int tokenNum = 0;
	while ( token != NULL )
	{
		_tcscpy( cmdTokenList[tokenNum++], StrLower(token) );
		token = _tcstok(NULL, seps);
	}

	if ( !_tcscmp( cmdTokenList[0], _T("exit") ) )
	{
		return TRUE;
	}
	else if ( !_tcscmp( cmdTokenList[0], _T("notepad") ) )
	{
		STARTUPINFO si = {0,};
		PROCESS_INFORMATION pi;
		si.cb = sizeof(si);
		TCHAR command[] = _T("C:\\WINDOWS\\system32\\notepad.exe");
		CreateProcess(
			command,NULL,NULL,NULL,
			TRUE,0,NULL,NULL,&si,&pi
		);
		CloseHandle(pi.hProcess);
		CloseHandle(pi.hThread);
	}
	else if ( !_tcscmp( cmdTokenList[0], _T("start") ) )
	{
		STARTUPINFO si = {0,};
		PROCESS_INFORMATION pi;
		si.cb = sizeof(si);
		si.dwFlags = STARTF_USEPOSITION | STARTF_USESIZE;
		si.dwX = 200;
		si.dwY = 200;
		si.dwXSize = 300;
		si.dwYSize = 300;

		TCHAR command[STR_LEN];
		_tcscpy(command,_T("CommandPrmpt_One.exe "));

		if ( tokenNum > 1 )
		{
			if (  _tcslen( originString ) - 5 > 100 )
			{
				_tprintf( ERROR_CMD, cmdTokenList[0] );
				return 0;
			}
			_tcscat( command , originString + 6 );
//			_tprintf( _T("in START command : %s\n") ,command );
			
			CreateProcess(
				NULL,command,NULL,NULL,
				TRUE,CREATE_NEW_CONSOLE,NULL,NULL,&si,&pi
			);
		}
		else
			CreateProcess(
				NULL,command,NULL,NULL,
				TRUE,CREATE_NEW_CONSOLE,NULL,NULL,&si,&pi
			);
		CloseHandle(pi.hProcess);
		CloseHandle(pi.hThread);
	}
	else if ( !_tcscmp( cmdTokenList[0], _T("echo") ) )
	{
		_tprintf( _T( originString + 5 ) );					// "echo " == length 5.
		_tprintf( _T("\n") );
	}
	else if ( !_tcscmp( cmdTokenList[0], _T("dir") ) )
	{
		system( _T( originString ) );
		_tprintf( _T( "\n" ) );
	}
	else
	{
		_tprintf( ERROR_CMD, cmdTokenList[0] );
	}
	return 0;
}

TCHAR * StrLower(TCHAR * pStr)
{
	TCHAR * ret = pStr;

	while ( *pStr )
	{
		if ( _istupper(*pStr) )
			*pStr = _totlower(*pStr);
		pStr++;
	}
	return ret;
}
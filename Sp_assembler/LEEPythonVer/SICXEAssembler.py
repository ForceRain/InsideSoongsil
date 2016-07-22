# -*- coding : utf-8 -*-
# 2015 system programming project1 mk3.

INST=0
FORMAT=1
OPCODE=2
OPER_NUM=3

register = { 'A' : 0 , 'X' : 1, 'L' : 2, 'B' : 3, 'S' : 4, 'T' : 5, 'F' : 6, 'PC' : 8, 'SW' : 9}    # enumeration 선언

input_data=[]                              # 들어오는 input내용을 저장할 변수
inst=[]                                    # OPTAB
token_table=[]                             # token table
token_line=0                               # 처리해야 할 token의 줄 개수를 담는 변수

symbol_table=[[]]                           # SYMTAB
label_num=[]                                # Symbol을 저장할 list
cur_line_num=0                              # token table의 위치를 가리키는 변수

literal_table=[[]]                          # LITTAB
has_literal=0                               # literal 개수를 담는 변수

EQU_check=0                                 # EQU인지 아닌지를 확인하기 위한 flag변수
location_counter=0                          # location counter
program_number=0                            # 프로그램 번호를 알려주는 변수
sub_program_start=0                         # 부 프로그램 동작을 나타내는 flag 변수
base_register=['',0]                    # 1번째 원소는 symbol name,2번째 원소는 address
program_length=[]                       # 각 프로그램 별 길이를 가지는 list
length_checked=0                        # 프로그램 마지막에 길이를 측정했는지 여부를 알리는 flag 변수
pc_or_base=1                            # pc-relative addressing, base-relative addressing을 나타내는 flag변수

xdef_table=[]                           # 외부 정의를 담는 table
xdef_num=[]                             # 각 프로그램 별로 외부 정의의 유무를 알리는 table
xref_table=[]                           # 외부 참조를 담는 table
xref_num=[]                             # 각 프로그램 별로 외부 참조의 유무를 알리는 table

modification_table=[[]]                 # 각 프로그램 별로 modification record가 필요한 symbol을 담는 table
modi_num=[]                             # 각 프로그램 별 modification record의 개수를 담는 table

class Token:
    def __init__(self,locCtr=0,instForm=-1,lb='',oper='',oprnd='',cimt=''):
        self.locationCounter=locCtr
        self.instructionFormat=instForm
        self.label=lb
        self.operator=oper
        self.operand=oprnd
        self.cmt=cimt

    def __str__(self):
        return '[ {0:04X}'.format((int(self.locationCounter)))+', '+str(self.label)+','+str(self.operator)+','+str(self.operand)+','+str(self.cmt)+']'
    pass

class Symbol :
    def __init__(self,sym='',addr=0):
        self.symbol=sym
        self.address=addr
    def __str__(self):
        return '['+str(self.symbol)+', '+str(self.address)+']'

class Literal :
    def __init__(self,lit='',addr=0):
        self.literal=lit
        self.address=addr
    def __str__(self):
        return '['+str(self.literal)+', '+str(self.address)+']'

class Modification :
    def __init__(self,mName='',addr=0,op=' ',isOp=0,cSize=0):
        self.modificationName=mName
        self.address=addr
        self.operator=op
        self.isOpcode=isOp
        self.changeSize=cSize
    pass

# OPTAB을 초기화 하는 함수.
# 정상종료시 0을 반환한다.

def init_inst_file():
    try :
        f=open("inst.data.txt","r")
    except IOError :
        return -1
    r=f.readlines()         # 파일 통째로 읽어온다.
    for list in r :
        a=list.split('\t')  # 탭 문자 기준으로 나눠서 inst에 list로 저장한다.
        inst.append(a)

    return 0
    pass

# input.txt 파일의 내용을 가져오는 함수
# 정상종료시 0을 반환한다.

def init_input_file():
    try :
        f=open("input.txt","r")
    except IOError :
        return -1

    r=f.readlines()             # 파일 통째로 읽어온다.
    for line in r :
        a=line.split('\n\t')        # 개행과 탭문자 기준으로 한 줄을 나눈다.
        a[0]=a[0].strip()           # 토큰에 공백이 있을 경우에 공백 제거.
        input_data.append(a[0].split('\t'))         # 탭 문자를 기준으로 나눈 list를 저장한다.

    return 0
    pass

# 프로그램 동작 초기에 실행하는 함수. OPTAB과 source file을 불러온다.
# 정상 종료시 0을 반환한다.

def init_assembler():
    if init_inst_file()<0 :
        return -1
    if init_input_file()<0 :
        return -1
    return 0
    pass

# assembler pass1을 진행하는 함수.
# 정상종료시 0을 반환한다.

def assem_pass1():
    global program_number, cur_line_num,token_line              # 전역으로 사용하는 변수를 여기서도 사용함.

    first=input_data[0]                                            # 첫 줄에는 프로그램 이름, 시작 주소가 있다고 가정.
    symbol_table[program_number].append(Symbol(first[0],int(first[2],16)))

    location_counter=0
    cur_line_num=1
    has_literal=0
    program_number=0
    sub_program_start=0
    xdef_num.append(0)
    xref_num.append(0)
    modi_num.append(0)                                            # assembler 진행중에 쓸 변수를 추가 및 초기화.

    token_table.append(Token((int(first[2])),-1,first[0],first[1],first[2]))

    label_num.append(1)
    token_parsing()
    token_line=cur_line_num

    return 0
    pass

# input_data에 있는 source code 한줄 한줄에 대하여 진행하는 함수이다.
# 정상 종료시 0을 반환한다.

def token_parsing() :
    input_data.__delitem__(0)                                               # for문 사용을 위해서 첫 줄 읽었으니 삭제.
    global sub_program_start, length_checked, program_number,has_literal, location_counter,EQU_check, cur_line_num

    for line in input_data :                                                # source code 한줄에 대해서
        out=search_opcode(line[0])
        if out>0 :
            if parse_opcode(line[0],line)<0 :
                return -1

            if sub_program_start==1 :                                       # 부 프로그램 시작시.
                token_table[cur_line_num].label=symbol_table[program_number][0].symbol;
                sub_program_start=0
            else :
                pass
        elif line[0]=='.' :                                             # source code의 '.'은 주석이라고 가정한다.
            token_table.append(Token(lb=line[0],cimt=''))
        elif line[0]=='END' :                                           # 'END' directive였을 경우.
            token_table.append(Token(oper=line[0],oprnd=(line[1].split(','))))
            while has_literal>=0 :                                      # literal을 token table에 추가
                literal_organization()
            if length_checked==0 :                                      # 프로그램 길이 확인
                program_length.append(location_counter)
                length_checked=1
            return 0
        elif line[0]=='LTORG' :                                         # 'LTORG' directive 였을 경우.
            token_table.append(Token(oper=line[0]))
            while has_literal>=0 :
                literal_organization()
        elif line[0]=='EXTDEF' :                                        # 외부 정의 였을 경우
            token_table.append(Token(oper=line[0],oprnd=(line[1].split(','))))
            xdef_table.append(line[1].split(','))
            xdef_num[program_number]=1                                  # 있다는 것을 알리기 위함.
        elif line[0]=='EXTREF' :                                        # 외부 참조 였을 경우
            token_table.append(Token(oper=line[0],oprnd=(line[1].split(','))))
            xref_table.append(line[1].split(','))
            xref_num[program_number]=1                                  # 있다는 것을 알리기 위함.
        elif line[0]=='BASE' :                                          # base 설정할 경우
            token_table.append(Token(oper=line[0],oprnd=(line[1])))
            base_register[0]=line[1]                                      # base register에 symbol 지정
        else :                                                      # 그 외의 경우는 label이라고 가정한다.
            temp=location_counter                                       # 잠시 임시변수에 담아둔다.
            if line[1]=='CSECT' :                                       # control section이 일어날 경우
                token_table.append(Token(oper=line[1],locCtr=0,lb=line[0],instForm=0))

                if length_checked==0 :                                  # 길이 체크를 안한 경우.
                    program_length.append(location_counter)
                    length_checked=1

                location_counter=0                                      # location counter를 0으로 설정.
                program_number+=1
                label_num.append(1)
                literal_table.append([])
                has_literal=0
                length_checked=0
                xref_num.append(0)
                xdef_num.append(0)
                modi_num.append(0)                                          # 새 프로그램을 시작하기 위한 초기화

                modification_table.append([])
                symbol_table.append([])                                  # 각 프로그램 별 list 추가.
                symbol_table[program_number].append(Symbol(line[0],0 ) )
                sub_program_start=1
            else :
                i=parse_opcode(line[1],line)                                # token table에 저장.
                if i<0 :                                                    # 명령어가 아니면 프로그램 종료.
                    return -1

                symbol_table[program_number].append(Symbol())
                symbol_table[program_number][label_num[program_number]].symbol=line[0]
                if EQU_check==1 :                                           # 'EQU'였다면 symbol의 address값을 할당하지 않고,
                    EQU_check=0
                else :                                                      # 아니었다면 address값에 잠시 저장해둔 전의 location counter값을 배정한다.
                    symbol_table[program_number][label_num[program_number]].address=temp

                label_num[program_number]+=1
                token_table[cur_line_num].label=line[0]
        cur_line_num+=1

    return 0
    pass

# pass1에서 literal을 만났을 경우, location counter에 반영하기 위한 함수.

def literal_organization() :
    global cur_line_num, has_literal,location_counter
    cur_line_num+=1                                                                 # 한 줄 늘려주고,
    literal_table[program_number][has_literal].address=location_counter

    tmp=literal_increase(literal_table[program_number][has_literal].literal)        # literal의 크기를 정한다음,
    if tmp<0 : return -1

    token_table.append(Token(locCtr=location_counter,instForm=tmp,lb='*',           # literal표시를 해주고
        oper=literal_table[program_number][has_literal].literal))
    location_counter+=tmp                                                           # location counter값을 증가시킨다.
    has_literal-=1
    pass

# location counter를 얼마나 증가 시킬지를 계산하는 함수.
# location counter의 증가 값을 반환한다.

def literal_increase(string) :
    start=2
    end=len(string)-1

    if string[1]=='C' : return (end-start-1)                                        # character 문자에 대해서,
    elif string[1]=='X' : return (end-start-1)/2                                    # 16진수 표현에 대해서
    else : return -1                                                            # 그 외에는 지원하지 않음.

    pass

# opcode와 parsing할 line을 매개로 받아서, token table에 저장하는 함수이다.
# 정상종료시 0을 반환한다.

def parse_opcode(opcode,line) :
    global program_number, length_checked,EQU_check,cur_line_num,location_counter
    is_four_format=0
    is_two_format=0
    is_one_format=0
    check_op=0

    if opcode[0]=='+' :                                                             # 4형식 이라면
        is_four_format=1
    check_op=search_opcode(opcode)                                                     # OPTAB에서 opcode 탐색

    if check_op<0 :                                                                     # directive 라면
        if (opcode=='WORD') or (opcode=='RESW') or (opcode=='RESB') or (opcode=='BYTE') :
            i=line.index(opcode)
            token_table.append(Token(instForm=0,locCtr=location_counter,oper=opcode,oprnd=(line[i+1].split(','))))

            if opcode=='WORD':
                location_counter+=3
            elif opcode=='RESW' :
                location_counter+=3*int(line[i+1])
            elif opcode=='RESB' :
                location_counter+=int(line[i+1])
            elif opcode=='BYTE' :
                location_counter+=1                                                     # operand의 값에 따라 적절하게 location counter를 증가.
            return 0
        elif opcode=='EQU' :                                                            # EQU directive 라면
            i=line.index(opcode)

            symbol_table[program_number].append(Symbol())
            if line[i+1]=='*' :                                                         # '*' 이라면 현재 location counter의 값을 symbol의 값으로 하겠다는 뜻.
                token_table.append(Token(oper=opcode,oprnd=(line[i+1].split(',')),locCtr=location_counter,instForm=0))
                symbol_table[program_number][label_num[program_number]].address=location_counter
            else :                                                                      # 그렇지 않으면 아직 symbol의 값을 배정할 수 없음. 0으로 지정.
                token_table.append(Token(oper=opcode,oprnd=(line[i+1].split(',')),locCtr=0,instForm=0))
                symbol_table[program_number][label_num[program_number]].address=0

            if length_checked==0 :                                                      # 프로그램 길이 체크.
                program_length.append(location_counter)
                length_checked=1
            EQU_check=1
        else :
            return -1
    else :
        if inst[check_op][FORMAT]=='2' :
            is_two_format=1
        elif inst[check_op][FORMAT]=='1' :
            is_one_format=1
        i=line.index(opcode)                                                                    # line의 list안에서 opcode가 위치한 곳의 인덱스를 반환.

        if line[i+1]!='' :
            if line[i+1][0]=='=' :
                if line[i+1] not in literal_table[program_number] :                                     # 추가하려는 literal이 literal table에 존재하지 않으면,
                    literal_table[program_number].append(Literal(lit=line[i+1]))                            # 추가한다.

                token_table.append(Token(locCtr=location_counter,oper=opcode,oprnd=(line[i+1].split(','))))

            else :
                token_table.append(Token(locCtr=location_counter,oper=opcode,oprnd=line[i+1].split(',')))
        else :
            token_table.append(Token(locCtr=location_counter,oper=opcode))

        if is_four_format==1 :                                                      # 4형식이라면, modification table에도 추가해준다.
            location_counter+=4
            token_table[cur_line_num].instructionFormat=4
            modification_table[program_number].append(Modification(mName=token_table[cur_line_num].operand[0],
                                                                   addr=token_table[cur_line_num].locationCounter,op='+',isOp=1,cSize=5))
            modi_num[program_number]+=1                                             # modification table에 추가.
        elif is_two_format==1 :
            location_counter+=2
            token_table[cur_line_num].instructionFormat=2
        elif is_one_format==1 :
            location_counter+=1
            token_table[cur_line_num].instructionFormat=1
        else :
            location_counter+=3
            token_table[cur_line_num].instructionFormat=3                           # 각 형식에 따라서 location counter를 증가.

    return 0
    pass

# mnemonic을 매개로 받아서, 그것이 OPTAB의 어느 위치에 있는지를 반환하는 함수이다.
# mnemonic이 OPTAB에 있으면 그 위치의 index를 반환, 찾지 못했다면 -1 반환.

def search_opcode(mnemonic) :
    if mnemonic=='' :
        return -2
    if mnemonic[0]=='+' :                                                       # 4형식을 뜻하는 '+'가 있다면,
        mnemonic=mnemonic[1:]                                                   # substring을 구함.
    i=0
    for name in inst :
        if name[0]==mnemonic :
            return i                                                            # OPTAB에서 위치를 반환.
        i+=1

    return -1                                                                   # 발견 못했으면 -1반환.
    pass

# assembler의 pass2를 진행하는 함수이다.
# 정상종료시 0을 반환.

def assem_pass2():
    global program_number,pc_or_base,token_table
    pc_or_base=1                                                # pc-relative, base-relative addressing을 나타내는 flag
    pass_or_not=0
    op_number=0
    is_looping=0
    op_cnt=0
    program_number=0                                            # 프로그램 번호.
    i=0
    base_register_init()                    # base register 초기화.

    for line in token_table :                                                       # token table의 각 줄에 대해서 진행.
        index=search_opcode(line.operator)
        is_looping=0
        op_cnt=0
        pc_or_base=1
        pass_or_not=0
        op_number=int(0)

        if index>=0 :                                                       # OPTAB에 존재하는 명령어라면,
            op_number=int(inst[index][OPCODE],16)                           # OPTAB에서 16진수로 읽어온다.
            op_number=op_number+int(indirect_immediate(line,index))
        elif index==-1 :
            if line.operator[0]=='=' :
                value_packing(3,line,i,line.operator)
            elif line.operator=='BYTE' or line.operator=='WORD' :
                value_packing(2,line,i,line.operand[0])
            elif line.operator=='EQU' :
                equate_refine(line)
            elif line.operator=='CSECT' :
                program_number+=1
            pass_or_not=1
        else :
            pass_or_not=1

        if pass_or_not==0 :                                                     # 명령어인 경우
            if line.instructionFormat==1 :                                      # 1형식 명령어라면,
                line.cmt='{0:02X}'.format(op_number)

            elif line.instructionFormat==2 :                                    # 2형식 명령어라면,
                for oprnd in line.operand :
                    op_number=op_number<<4
                    if oprnd=='A' : op_number=op_number+register['A']           # operand에 해당하는 enumeration값을 더해준다.
                    if oprnd=='X' : op_number=op_number+register['X']
                    if oprnd=='L' : op_number=op_number+register['L']
                    if oprnd=='B' : op_number=op_number+register['B']
                    if oprnd=='S' : op_number=op_number+register['S']
                    if oprnd=='T' : op_number=op_number+register['T']
                    if oprnd=='F' : op_number=op_number+register['F']
                    if oprnd=='PC' : op_number=op_number+register['PC']
                    if oprnd=='SW' : op_number=op_number+register['SW']
                    op_cnt+=1

                if op_cnt==1 :
                    op_number=op_number<<4                                      # operand가 하나짜리인 2형식이었다면,
                line.cmt='{0:04X}'.format(op_number)

            elif line.instructionFormat==3 or line.instructionFormat==4 :                           # 3형식이나 4형식 명령어라면,
                for oprnd in line.operand :                                             # operand중에 X가 있다면 looping 지원.
                    if oprnd=='X' :
                        is_looping=1
                op_number=op_number<<4

                if is_looping==1 : op_number+=8                                 # looping을 지원하면,
                if line.instructionFormat==4 : op_number+=1                     # 4형식이라면,

                if line.operand!='' :                                           # operand가 존재한다면,
                    if line.operand[0][0]=='#' :                                # immediate addressing이라면,
                        if str(line.operand[0][1]).isdigit() :                  # 숫자로 바로 immediate 값을 주는 경우.
                            if line.instructionFormat==4 :
                                op_number=op_number<<20
                            else :
                                op_number=op_number<<12                         # 3형식인지 4형식인지에 따라 알맞게 shift해준다.

                            temp=int(line.operand[0][1:])
                            op_number=op_number|temp                                  # bitwise-or 연산을 통해서 값을 반영한다.

                            if line.instructionFormat==4 :
                                line.cmt='{0:08X}'.format(op_number)
                            else :
                                line.cmt='{0:06X}'.format(op_number)
                        else :                                                  # symbol의 주소 값으로 immediate 값을 주는경우.
                            temp=relative_offset(line,i+1,line.operand[0][1:])

                            if line.instructionFormat!=4 :
                                if pc_or_base==1 :
                                    op_number+=2                                # pc-relative
                                elif pc_or_base==0 :
                                    op_number+=4                                # base-relative
                                else :
                                    return -1
                            if line.instructionFormat==4 :
                                op_number=op_number<<20
                            else :
                                op_number=op_number<<12                # 3형식인지 4형식인지에 따라 알맞게 shift해준다.

                            op_number=op_number|temp                                  # bitwise-or 연산을 통해서 값을 반영한다.
                            if line.instructionFormat==4 :
                                line.cmt='{0:08X}'.format(op_number)
                            else :
                                line.cmt='{0:06X}'.format(op_number)
                    elif line.operand[0][0]=='@' :                              # indirect addressing이라면,
                        temp=relative_offset(line,i+1,line.operand[0][1:])

                        if line.instructionFormat!=4 :
                            if pc_or_base==1 :
                                op_number+=2                             # pc-relative
                            elif pc_or_base==0 :
                                op_number+=4                               # base-relative
                            else :
                                return -1
                        if line.instructionFormat==4 :
                            op_number=op_number<<20
                        else :
                            op_number=op_number<<12                # 3형식인지 4형식인지에 따라 알맞게 shift해준다.

                        op_number=op_number|temp                                   # bitwise-or 연산을 통해서 값을 반영한다.
                        if line.instructionFormat==4 :
                            line.cmt='{0:08X}'.format(op_number)
                        else :
                            line.cmt='{0:06X}'.format(op_number)
                    else :                                                      # 그 외의 경우에는
                        temp=relative_offset(line,i+1,line.operand[0])                  # 주소값을 구한다.

                        if line.instructionFormat!=4 :
                            if pc_or_base==1 :
                                op_number+=2                                # pc-relative
                            elif pc_or_base==0 :
                                op_number+=4                               # base-relative
                            else :
                                return -1
                        if line.instructionFormat==4 :
                            op_number=op_number<<20
                        else :
                            op_number=op_number<<12                # 3형식인지 4형식인지에 따라 알맞게 shift해준다.

                        op_number=op_number|temp                                   # bitwise-or 연산을 통해서 값을 반영한다.
                        if line.instructionFormat==4 :
                            line.cmt='{0:08X}'.format(op_number)
                        else :
                            line.cmt='{0:06X}'.format(op_number)
                else :                                                          # operand가 없는 명령어의 경우에는,
                    if line.instructionFormat==4 :
                        op_number=op_number<<20
                    else :
                        op_number=op_number<<12                # 3형식인지 4형식인지에 따라 알맞게 shift해준다.

                    if line.instructionFormat==4 :
                        line.cmt='{0:08X}'.format(op_number)
                    else :
                        line.cmt='{0:06X}'.format(op_number)
        i+=1

    return 0
    pass

# literal이나, 변수들에 값을 할당해주는 함수이다.
# 정상종료시 0을 반환한다.

def value_packing(starting_ptr,line,line_number,symbol) :
    global program_number, token_table
    this_literal=0
    str_op1=''
    str_op2=''
    x_c_multiplication=1                                            # 16진수 표현이냐, character 표현이냐에 따라서 길이를 지정하기 위함.
    temp_val=0
    operator_ptr=0
    result=0
    temp=''
    val=0
    op1=0
    op2=0
    end=len(symbol)-1

    if symbol[0]=='=' : this_literal=1
    else : this_literal=2

    if symbol[starting_ptr-2]=='X' :                                # 16진수 표현이라면,
        x_c_multiplication=2
        temp=symbol[end]
        val=int(symbol[starting_ptr:end],16)
        temp_val=val
    elif symbol[starting_ptr-2]=='C' :                              # character 표현이라면,
        for i in range(starting_ptr,end) :
            if i!=starting_ptr :
                result=result<<4*2
            result=result|ord(symbol[i])
        temp_val=result
    else :                                                           # 그 외의 경우.
        this_literal=0
        while str(symbol[operator_ptr]).isalpha() and operator_ptr<=end :           # 이항 연산만 지원한다.
            operator_ptr+=1

        temp=symbol[operator_ptr]
        str_op1=symbol[0:operator_ptr]
        str_op2=symbol[operator_ptr+1:]
        op1=get_symbol_value(program_number,str_op1)
        op2=get_symbol_value(program_number,str_op2)

        if temp=='-' : temp_val=op1-op2
        elif temp=='+' : temp_val=op1+op2
        elif temp=='*' : temp_val=op1*op2
        elif temp=='/' : temp_val=op1/op2                           # 연산자에 알맞는 연산을 취한다.

    if this_literal==1 : line.instructionFormat=(end-3)/x_c_multiplication
    elif this_literal==2 : line.instructionFormat=(end-2)/x_c_multiplication
    else : line.instructionFormat=3                                   # this_literal에 의해서 명령어 길이를 갖게 한다.

    if line.instructionFormat==1 : token_table[line_number].cmt='{0:02X}'.format(temp_val)
    if line.instructionFormat==2 : token_table[line_number].cmt='{0:04X}'.format(temp_val)
    if line.instructionFormat==3 : token_table[line_number].cmt='{0:06X}'.format(temp_val)
    if line.instructionFormat==4 : token_table[line_number].cmt='{0:08X}'.format(temp_val)

    for xref in xref_table[program_number] :                            # 혹시 외부 참조를 하는 operand가 있다면, 첫번쨰 피 연산자에 대해서 진행
        if line.operator!='' :
            if xref==str_op1 :
                if line.operator=='WORD' :
                    modification_table[program_number].append(Modification(mName=str_op1,addr=line.locationCounter,op='+',isOp=0,cSize=6))
                    modi_num[program_number]+=1
                if line.operator=='BYTE' :
                    modification_table[program_number].append(Modification(mName=str_op1,addr=line.locationCounter,op='+',isOp=0,cSize=2))
                    modi_num[program_number]+=1                             # 첫번째 피 연산자의 부호는 '+'라고 가정한다.

    for xref in xref_table[program_number] :                            # 혹시 외부 참조를 하는 operand가 있다면, 두번째 피 연산자에 대해서 진행
        if line.operator!='' :
            if xref==str_op2 :
                if line.operator=='WORD' :
                    modification_table[program_number].append(Modification(mName=str_op2,addr=line.locationCounter,op=temp,isOp=0,cSize=6))
                    modi_num[program_number]+=1
                if line.operator=='BYTE' :
                    modification_table[program_number].append(Modification(mName=str_op2,addr=line.locationCounter,op=temp,isOp=0,cSize=2))
                    modi_num[program_number]+=1

    return 0
    pass

# pc-relative addressing을 할지, base-relative addressing을 할지 정하고, 주소값을 계산해서 반환하는 함수이다.
# 주소 배정이 가능하다면, 그에 해당하는 flag에 값을 넣고, 주소 배정의 값을 반환한다. 그렇지 못하면 -1을 반환한다.

def relative_offset(line,next_line_index,symbol) :
    global pc_or_base,literal_table
    next_locctr=token_table[next_line_index].locationCounter
    go_addr=get_symbol_value(program_number,symbol)
    masking=1                                           # 형식에 따라 알맞게 길이를 늘린다.
    if line.instructionFormat==3 :                      # 3형식이라면
        for i in range(0,11) :
            masking=masking<<1
            masking=masking|1
    else :                                              # 4형식이라면
        for i in range(0,19) :
            masking=masking<<1
            masking=masking|1

    if go_addr==-1 :                                    # 찾으려는 symbol이 존재하지 않는경우.
        for find_lit in literal_table[program_number] :                         # literal table에서 찾는다.
            if find_lit.literal==symbol :
                if ((-2048<=find_lit.address-next_locctr) and (find_lit.address-next_locctr<=2048)) :                # 이 범위 안에 들어간다면 pc-relative addressing가능.
                    pc_or_base=1
                    return (find_lit.address-next_locctr)&masking
                elif ((0<=find_lit.address-next_locctr) and (find_lit.address-next_locctr<=4096)) :                 # 이 범위 안에 들어간다면 base-relative addressing 가능.
                    pc_or_base=0
                    return (find_lit.address-next_locctr)&masking
                else :
                    return -1
        return 0
    elif go_addr==0 :                                   # 외부 참조에서 발견했다면, 0을 반환
        return 0

    if ((-2048<=go_addr-next_locctr) and (go_addr-next_locctr<=2048)) :               # 이 범위 안에 들어간다면 pc-relative addressing가능.
        pc_or_base=1
        return (go_addr-next_locctr)&masking
    elif ((0<=go_addr-next_locctr) and (go_addr-next_locctr<=4096)) :                # 이 범위 안에 들어간다면 base-relative addressing 가능.
        pc_or_base=0
        return (go_addr-next_locctr)&masking
    else :
        return -1

# 'EQU' directive에 대해 값을 배정하는 함수이다.

def equate_refine(line) :
    global program_number
    str_l=len(line.operand[0])
    i=0
    operator_ptr=0
    op1=0
    op2=0

    if line.operand[0]=='*' : return 0                                              # '*'는 현재 location counter의 값을 배정해 주는 것으로, 이미 pass1에서 진행했다.
    else :
        while line.label!=symbol_table[program_number][i].symbol : i+=1             # SYMTAB에서 EQU 값을 넣을 symbol을 찾는다.

        while str(line.operand[0][operator_ptr]).isalpha() : operator_ptr+=1        # 이항 연산만 가능하다.

        temp=line.operand[0][operator_ptr]

        op1=get_symbol_value(program_number,line.operand[0][0:operator_ptr])        # 첫 번째 피연산자.
        op2=get_symbol_value(program_number,line.operand[0][operator_ptr+1:])       # 두 번째 피연산자

        if temp=='-' :
            symbol_table[program_number][i].address=op1-op2
        elif temp=='+' :
            symbol_table[program_number][i].address=op1+op2
        elif temp=='*' :
            symbol_table[program_number][i].address=op1*op2
        elif temp=='/' :
            symbol_table[program_number][i].address=op1/op2                         # 연산자에 알맞는 연산을 해서 symbol에 값을 지정한다.
        line.locationCounter=symbol_table[program_number][i].address

# 간접 주소 방식, 즉시 주소 방식인지를 확인하여 그에 알맞는 값을 반환하는 함수이다.
# 주소에 따른 값을 반환한다.

def indirect_immediate(line,index) :
    if inst[index][OPER_NUM]!='0' :                 # operand 존재하는 경우.
        if line.instructionFormat==3 or line.instructionFormat==4 :
            if line.operand[0][0]=='#' : return int(1)          # immediate 주소 방식
            elif line.operand[0][0]=='@' : return int(2)        # indirect 주소 방식
            else : return int(3)                                # direct 주소 방식
        else :
            return int(0)
    else :                                          # operand 존재하지 않는경우
        if line.instructionFormat==3 or line.instructionFormat==4 :         # 3형식이나 4형식이라면
            return int(3)
        else :                                                     # 1,2형식이라면
            return int(0)
    pass

# base-relative addressing을 하기 위해서 base를 설정하는 함수이다.
# 정상종료시 0을 반환한다.

def base_register_init() :
    global program_number, base_register
    if base_register[0]=='' : return -1                     # pass1에서 'BASE'라는 directive가 없었다면 종료.
    else : base_register[1]=get_symbol_value(program_number,base_register.symbol)       # 있었다면 그 symbol의 address를 base로 설정.
    return 0

# 각 프로그램 별로 symbol table을 탐색해서 원하는 symbol이 있는지 확인하는 함수.
# 찾았으면 그 symbol에 해당하는 address를 반환한다. 외부 참조에 존재하면 0을 반환, 존재하지 않으면 -1을 반환한다.

def get_symbol_value(pr_num,symbol) :
    for find in symbol_table[pr_num] :              # 프로그램 번호에 따른 symbol table에서 탐색.
        if find.symbol==symbol :
            return find.address

    for ref_find in xref_table[pr_num] :            # 외부 참조에 대해서 탐색.
        if ref_find[0]==symbol :
            return 0
    return -1                                       # 존재하지 않으면 -1 반환.

# intermediate file을 화면에 출력하는 함수이다.
# filename이 주어진다면, 그 filename에 해당하는 파일을 make_objectcode()에서 생성한다.

def make_output(filename) :
    global cur_line_num, token_table
    for i in range(0,cur_line_num+1) :                                          # token table의 끝까지 진행.
        printed=0                                                               # operand 출력시, ','출력을 위한 flag 변수
        if token_table[i].instructionFormat!=-1 or i==0 :
            print ('{0:04X}'.format(token_table[i].locationCounter),end='    ')     # location counter 출력.

        if token_table[i].label!='' :
            print('{0:6s}'.format(token_table[i].label),end='    ')                # label 출력.
        else :
            print(end='    ')

        if token_table[i].operator!='' :
            print((token_table[i].operator),end='    ')                                # operator, 명령어 출력.
        else :
            print(end='    ')

        if token_table[i].operand!='' :
            for oprnd in token_table[i].operand :                                   # 존재하는 모든 operand에 대해서 출력.
                if printed==0 :
                    print(oprnd,end='   ')
                    printed=1
                else :
                    print(','+oprnd,end='   ')
        else :
            print(end=' ')

        if token_table[i].cmt!='' :                                                 # comment가 존재하면 comment 출력
            print(token_table[i].cmt)                                               # pass 2에선 comment위치에 object code를 넣었음.
        else :
            print()

    if filename!='' :                                                           # filename이 내용이 있다면,
        make_objectcode(filename)                                               # filename의 이름을 가진 object program을 생성.
    pass

# filename에 해당하는 object program을 생성하는 함수.

def make_objectcode(filename) :
    global symbol_table, token_table,xref_table,xdef_table,program_number,token_line
    file=open(filename,'w')                         # 쓰기 전용으로 파일 오픈.
    line_max=0                                      # 한 줄에 쓸 수 있는 최대 바이트 수를 담을 변수
    object_code_sum=0                               # 최종적으로 바이트 수 만큼 쓸 변수
    reserved=-1                                     # 'RESW', 'RESB'등의 directive가 나오면 줄 바꿈을 위한 변수.
    out_ptr=0                                       # whlie 무한 루프 반복문을 빠져 나갈 flag
    bef_ptr=0                                       # 최대 바이트 수를 넘겨서 반복문을 빠져 나왔을 때, 넘기게 된 원인의 명령어의 바이트 수를 담고 있는 변수
    line_start_ptr=0                                # token table에서 쓰기 시작할 위치를 가리키는 변수
    line_num_ptr=0

    for i in range (0,program_number+1) :           # 모든 프로그램에 대해서 진행.
        line_max=0
        out_ptr=0
        reserved=0

        file.write('H{0:6s}'.format(symbol_table[i][0].symbol))              # header record
        file.write('{0:06X}'.format(int(symbol_table[i][0].address)))        # 시작 주소
        file.write('{0:06X}\n'.format(int(program_length[i])))                  # 프로그램 길이

        if xdef_num[i]!=0 :                                             # 외부 정의
            file.write('D')
            for xdef in xdef_table[i] :
                file.write('{0:6s}'.format(xdef))
                file.write('{0:06X}'.format(get_symbol_value(i,xdef)))
            file.write('\n')

        if xref_num[i]!=0 :                                             # 외부 참조
            file.write('R')
            for xref in xref_table[i] :
                file.write('{0:6s}'.format(xref))
            file.write('\n')

        while 1 :                                                       # text record를 쓰는 routine
            object_code_sum=0
            bef_ptr=0

            line_max=0
            while line_max<=30 and line_num_ptr<=token_line :                       # 최대 바이트수인 30을 넘지 않고, token table의 끝까지 진행.
                if token_table[line_num_ptr].operator!='' :
                    if token_table[line_num_ptr].operator=='CSECT' :                # CSECT를 만나면 text record 쓰는것을 종료.
                        out_ptr=1
                        break
                    if token_table[line_num_ptr].operator=='RESW' or token_table[line_num_ptr].operator=='RESB' :       # 'RESB', 'RESW'를 만나면 줄 바꿈 시행.
                        reserved=1
                        break
                if token_table[line_num_ptr].cmt!='' :                                  # object code가 있는 경우, 명령어 형식을 통해서 쓸 바이트 수를 늘려나간다.
                    line_max+=token_table[line_num_ptr].instructionFormat
                    bef_ptr=token_table[line_num_ptr].instructionFormat
                line_num_ptr+=1

            if line_max>=30 :                                                       # 반복문이 최대 바이트수를 넘겨서 끝이 났다면,
                line_num_ptr-=1                                                     # 한 줄 되돌리고,
                object_code_sum=(line_max-bef_ptr)                                  # 그 전에 명령어 크기만큼 빼준다.
            else :
                object_code_sum=line_max                                            # 프로그램 끝까지 도달한 경우,

            if line_num_ptr>=token_line :
                out_ptr=1

            file.write('T{0:06X}'.format(token_table[line_start_ptr].locationCounter))          # text record 시작 부분.
            file.write('{0:02X}'.format(int(object_code_sum)))                         # 쓸 바이트 수.

            j=line_start_ptr
            while j<line_num_ptr :                                                  # text record에 해당하는 object code를 씀.
                if token_table[j].cmt!='' :
                    if token_table[j].instructionFormat==1 :
                        file.write('{0:02X}'.format(int(token_table[j].cmt,16)))
                    if token_table[j].instructionFormat==2 :
                        file.write('{0:04X}'.format(int(token_table[j].cmt,16)))
                    if token_table[j].instructionFormat==3 :
                        file.write('{0:06X}'.format(int(token_table[j].cmt,16)))
                    if token_table[j].instructionFormat==4 :
                        file.write('{0:08X}'.format(int(token_table[j].cmt,16)))
                j+=1
            file.write('\n')                                                        # 개행 출력.

            if reserved==1 :                                                        # 'RESW', 'RESB'가 있다면,
                while line_num_ptr<token_line :
                    if token_table[line_num_ptr].operator!='' :
                        if token_table[line_num_ptr].operator=='CSECT' :            # 'CSECT'가 나오면 이 프로그램에 대한 object program쓰는 것을 끝낸다.
                            out_ptr=1
                            break
                    if token_table[line_num_ptr].cmt!='' :                          # 다음 object code가 나올때까지 반복문 시행.
                        break
                    line_num_ptr+=1

            line_start_ptr=line_num_ptr
            if out_ptr==1 :
                break

        if line_num_ptr<token_line :
            while token_table[line_num_ptr].cmt=='' :                            # 다음 object code가 나올때까지 반복문 시행.
                line_num_ptr+=1
        line_start_ptr=line_num_ptr

        j=0
        while j<modi_num[i] :                                                       # modification record를 쓰는 부분.
            file.write('M')
            if modification_table[i][j].isOpcode==1 :                                       # 명령어에 대한 modification record라면,
                file.write('{0:06X}'.format(modification_table[i][j].address+1))
            else :
                file.write('{0:06X}'.format(modification_table[i][j].address))         # 변수 값을 지정하는것에 대한 modification record라면,

            file.write('{0:02X}'.format(modification_table[i][j].changeSize))
            file.write('{0:s}'.format(modification_table[i][j].operator))
            file.write('{0:s}'.format(modification_table[i][j].modificationName))
            file.write('\n')
            j+=1

        if i==0 :
            file.write('E{0:06X}\n'.format(symbol_table[i][0].address))             # end record
        else :
            file.write('E\n')
        file.write('\n')
    file.close()
    pass

# assembler기능을 수행하기 위한 main함수이다.

def __main__():
    if init_assembler()<0 :                                                 # assembler 초기화.
        print("init_assembler : initialization failed.")
        return
    if assem_pass1()<0 :                                                    # pass1 진행
        print("assem_pass1 : initialization failed.")
        return
    if assem_pass2()<0 :                                                    # pass2 진행
        print("assem_pass2 : initialization failed.")
        return

    make_output('output.txt')                                           # intermediate file과 object code 생성.

__main__()                  # main 함수 실행.
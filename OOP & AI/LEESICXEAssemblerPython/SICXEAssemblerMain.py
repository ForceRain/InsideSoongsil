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
sub_program_start=0
base_register=['',0]                    # 1번째 원소는 symbol name,2번째 원소는 address
program_length=[]
length_checked=0
pc_or_base=1

xdef_table=[]
xdef_num=[]
xref_table=[]
xref_num=[]

modification_table=[[]]
modi_num=[]

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

class Instruction:
    def __init__(self,iN='',iF='',op='',oN='',co=''):
        self.instructionName=iN
        self.instructionFormat=iF
        self.opcode=op
        self.operNum=oN
        self.cmt=co

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

def init_inst_file():
    try :
        f=open("inst.data.txt","r")
    except IOError :
        return -1
    r=f.readlines()
    for list in r :
        a=list.split('\t')
        inst.append(a)

    return 0
    pass

def init_input_file():
    try :
        f=open("input.txt","r")
    except IOError :
        return -1

    r=f.readlines()
    for line in r :
        a=line.split('\n\t')
        a[0]=a[0].strip()
        input_data.append(a[0].split('\t'))

    return 0
    pass

def init_assembler():
    if init_inst_file()<0 :
        return -1
    if init_input_file()<0 :
        return -1
    return 0
    pass

def assem_pass1():
    global program_number, cur_line_num,token_line

    first=input_data[0]
    symbol_table[program_number].append(Symbol(first[0],int(first[2],16)))

    location_counter=0
    cur_line_num=1
    has_literal=0
    program_number=0
    sub_program_start=0
    xdef_num.append(0)
    xref_num.append(0)
    modi_num.append(0)

    token_table.append(Token((int(first[2])),-1,first[0],first[1],first[2]))

    label_num.append(1)
    token_parsing()
    token_line=cur_line_num

    return 0
    pass

def token_parsing() :
    input_data.__delitem__(0)
    global sub_program_start, length_checked, program_number,has_literal, location_counter,EQU_check, cur_line_num

    for line in input_data :
        out=search_opcode(line[0])
        if out>0 :
            if parse_opcode(line[0],line)<0 :
                return -1

            if sub_program_start==1 :
                token_table[cur_line_num].label=symbol_table[program_number][0].symbol;
                sub_program_start=0
            else :
                pass
        elif line[0]=='.' :
            token_table.append(Token(lb=line[0],cimt=''))
        elif line[0]=='END' :
            token_table.append(Token(oper=line[0],oprnd=(line[1].split(','))))
            while has_literal>=0 :
                literal_organization()
            if length_checked==0 :
                program_length.append(location_counter)
                length_checked=1
            return 0
        elif line[0]=='LTORG' :
            token_table.append(Token(oper=line[0]))
            while has_literal>=0 :
                literal_organization()
        elif line[0]=='EXTDEF' :
            token_table.append(Token(oper=line[0],oprnd=(line[1].split(','))))
            xdef_table.append(line[1].split(','))
            xdef_num[program_number]=1
        elif line[0]=='EXTREF' :
            token_table.append(Token(oper=line[0],oprnd=(line[1].split(','))))
            xref_table.append(line[1].split(','))
            xref_num[program_number]=1
        elif line[0]=='BASE' :
            token_table.append(Token(oper=line[0],oprnd=(line[1])))
            base_register[0]=line[1]
        else :
            temp=location_counter
            if line[1]=='CSECT' :
                token_table.append(Token(oper=line[1],locCtr=0,lb=line[0],instForm=0))

                if length_checked==0 :
                    program_length.append(location_counter)
                    length_checked=1

                location_counter=0
                program_number+=1
                label_num.append(1)
                literal_table.append([])
                has_literal=0
                length_checked=0
                xref_num.append(0)
                xdef_num.append(0)
                modi_num.append(0)

                modification_table.append([])
                symbol_table.append([])
                symbol_table[program_number].append(Symbol(line[0],0 ) )
                sub_program_start=1
            else :
                i=parse_opcode(line[1],line)
                if i<0 :
                    return -1

                symbol_table[program_number].append(Symbol())

                symbol_table[program_number][label_num[program_number]].symbol=line[0]
                if EQU_check==1 :
                    EQU_check=0
                else :
                    symbol_table[program_number][label_num[program_number]].address=temp

                label_num[program_number]+=1
                token_table[cur_line_num].label=line[0]
        cur_line_num+=1

    return 0
    pass

def literal_organization() :
    global cur_line_num, has_literal,location_counter
    cur_line_num+=1

    literal_table[program_number][has_literal].address=location_counter

    tmp=literal_increase(literal_table[program_number][has_literal].literal)
    if tmp<0 : return -1

    token_table.append(Token(locCtr=location_counter,instForm=tmp,lb='*',
        oper=literal_table[program_number][has_literal].literal))
    location_counter+=tmp
    has_literal-=1

    pass

def literal_increase(string) :
    start=2
    end=len(string)-1

    if string[1]=='C' : return (end-start-1)
    elif string[1]=='X' : return (end-start-1)/2
    else : return -1

    pass

def parse_opcode(opcode,line) :
    global program_number, length_checked,EQU_check,cur_line_num,location_counter
    is_four_format=0
    is_two_format=0
    is_one_format=0
    check_op=0

    if opcode[0]=='+' :
        is_four_format=1

    check_op=search_opcode(opcode)

    if check_op<0 :
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
                location_counter+=1
            return 0
        elif opcode=='EQU' :
            i=line.index(opcode)

            symbol_table[program_number].append(Symbol())
            if line[i+1]=='*' :
                token_table.append(Token(oper=opcode,oprnd=(line[i+1].split(',')),locCtr=location_counter,instForm=0))
                symbol_table[program_number][label_num[program_number]].address=location_counter
            else :
                token_table.append(Token(oper=opcode,oprnd=(line[i+1].split(',')),locCtr=0,instForm=0))
                symbol_table[program_number][label_num[program_number]].address=0

            if length_checked==0 :
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

        i=line.index(opcode)

        if line[i+1]!='' :
            if line[i+1][0]=='=' :
                if line[i+1] not in literal_table[program_number] :
                    literal_table[program_number].append(Literal(lit=line[i+1]))

                token_table.append(Token(locCtr=location_counter,oper=opcode,oprnd=(line[i+1].split(','))))

            else :
                token_table.append(Token(locCtr=location_counter,oper=opcode,oprnd=line[i+1].split(',')))
        else :
            token_table.append(Token(locCtr=location_counter,oper=opcode))

        if is_four_format==1 :
            location_counter+=4
            token_table[cur_line_num].instructionFormat=4
            modification_table[program_number].append(Modification(mName=token_table[cur_line_num].operand[0],addr=token_table[cur_line_num].locationCounter,op='+',isOp=1,cSize=5))
            modi_num[program_number]+=1
        elif is_two_format==1 :
            location_counter+=2
            token_table[cur_line_num].instructionFormat=2
        elif is_one_format==1 :
            location_counter+=1
            token_table[cur_line_num].instructionFormat=1
        else :
            location_counter+=3
            token_table[cur_line_num].instructionFormat=3

    return 0
    pass

def search_opcode(mnemonic) :
    if mnemonic=='' :
        return -2
    if mnemonic[0]=='+' :
        mnemonic=mnemonic[1:]
    i=0
    for name in inst :
        if name[0]==mnemonic :
            return i
        i+=1

    return -1
    pass

def assem_pass2():
    global program_number,pc_or_base,token_table
    pc_or_base=1
    pass_or_not=0
    op_number=0
    is_looping=0
    op_cnt=0
    program_number=0
    i=0
    base_register_init()

    for line in token_table :
        index=search_opcode(line.operator)
        is_looping=0
        op_cnt=0
        pc_or_base=1
        pass_or_not=0
        op_number=int(0)

        if index>=0 :
            op_number=int(inst[index][OPCODE],16)
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

        if pass_or_not==0 :
            if line.instructionFormat==1 :
                line.cmt='{0:02X}'.format(op_number)

            elif line.instructionFormat==2 :
                for oprnd in line.operand :
                    op_number=op_number<<4
                    if oprnd=='A' : op_number=op_number+register['A']
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
                    op_number=op_number<<4

                line.cmt='{0:04X}'.format(op_number)

            elif line.instructionFormat==3 or line.instructionFormat==4 :
                for oprnd in line.operand :
                    if oprnd=='X' :
                        is_looping=1
                op_number=op_number<<4

                if is_looping==1 : op_number+=8
                if line.instructionFormat==4 : op_number+=1

                if line.operand!='' :
                    if line.operand[0][0]=='#' :
                        if str(line.operand[0][1]).isdigit() :
                            if line.instructionFormat==4 :
                                op_number=op_number<<20
                            else :
                                op_number=op_number<<12

                            temp=int(line.operand[0][1:])
                            op_number=op_number|temp;

                            if line.instructionFormat==4 :
                                line.cmt='{0:08X}'.format(op_number)
                            else :
                                line.cmt='{0:06X}'.format(op_number)
                        else :
                            temp=relative_offset(line,i+1,line.operand[0][1:])

                            if line.instructionFormat!=4 :
                                if pc_or_base==1 :
                                    op_number+=2
                                elif pc_or_base==0 :
                                    op_number+=4
                                else :
                                    return -1
                            if line.instructionFormat==4 :
                                op_number=op_number<<20
                            else :
                                op_number=op_number<<12

                            op_number=op_number|temp
                            if line.instructionFormat==4 :
                                line.cmt='{0:08X}'.format(op_number)
                            else :
                                line.cmt='{0:06X}'.format(op_number)
                    elif line.operand[0][0]=='@' :
                        temp=relative_offset(line,i+1,line.operand[0][1:])

                        if line.instructionFormat!=4 :
                            if pc_or_base==1 :
                                op_number+=2
                            elif pc_or_base==0 :
                                op_number+=4
                            else :
                                return -1
                        if line.instructionFormat==4 :
                            op_number=op_number<<20
                        else :
                            op_number=op_number<<12

                        op_number=op_number|temp
                        if line.instructionFormat==4 :
                            line.cmt='{0:08X}'.format(op_number)
                        else :
                            line.cmt='{0:06X}'.format(op_number)
                    else :
                        temp=relative_offset(line,i+1,line.operand[0])

                        if line.instructionFormat!=4 :
                            if pc_or_base==1 :
                                op_number+=2
                            elif pc_or_base==0 :
                                op_number+=4
                            else :
                                return -1
                        if line.instructionFormat==4 :
                            op_number=op_number<<20
                        else :
                            op_number=op_number<<12

                        op_number=op_number|temp
                        if line.instructionFormat==4 :
                            line.cmt='{0:08X}'.format(op_number)
                        else :
                            line.cmt='{0:06X}'.format(op_number)
                else :
                    if line.instructionFormat==4 :
                        op_number=op_number<<20
                    else :
                        op_number=op_number<<12

                    if line.instructionFormat==4 :
                        line.cmt='{0:08X}'.format(op_number)
                    else :
                        line.cmt='{0:06X}'.format(op_number)
        i+=1

    return 0
    pass

def value_packing(starting_ptr,line,line_number,symbol) :
    global program_number, token_table
    this_literal=0
    str_op1=''
    str_op2=''
    x_c_multiplication=1
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

    if symbol[starting_ptr-2]=='X' :
        x_c_multiplication=2
        temp=symbol[end]
        val=int(symbol[starting_ptr:end],16)
        temp_val=val
    elif symbol[starting_ptr-2]=='C' :
        for i in range(starting_ptr,end) :
            if i!=starting_ptr :
                result=result<<4*2
            result=result|ord(symbol[i])
        temp_val=result
    else :
        this_literal=0
        while str(symbol[operator_ptr]).isalpha() and operator_ptr<=end :
            operator_ptr+=1

        temp=symbol[operator_ptr]
        str_op1=symbol[0:operator_ptr]
        str_op2=symbol[operator_ptr+1:]
        op1=get_symbol_value(program_number,str_op1)
        op2=get_symbol_value(program_number,str_op2)

        if temp=='-' : temp_val=op1-op2
        elif temp=='+' : temp_val=op1+op2
        elif temp=='*' : temp_val=op1*op2
        elif temp=='/' : temp_val=op1/op2

    if this_literal==1 : line.instructionFormat=(end-3)/x_c_multiplication
    elif this_literal==2 : line.instructionFormat=(end-2)/x_c_multiplication
    else : line.instructionFormat=3

    if line.instructionFormat==1 : token_table[line_number].cmt='{0:02X}'.format(temp_val)
    if line.instructionFormat==2 : token_table[line_number].cmt='{0:04X}'.format(temp_val)
    if line.instructionFormat==3 : token_table[line_number].cmt='{0:06X}'.format(temp_val)
    if line.instructionFormat==4 : token_table[line_number].cmt='{0:08X}'.format(temp_val)

    for xref in xref_table[program_number] :
        if line.operator!='' :
            if xref==str_op1 :
                if line.operator=='WORD' :
                    modification_table[program_number].append(Modification(mName=str_op1,addr=line.locationCounter,op='+',isOp=0,cSize=6))
                    modi_num[program_number]+=1
                if line.operator=='BYTE' :
                    modification_table[program_number].append(Modification(mName=str_op1,addr=line.locationCounter,op='+',isOp=0,cSize=2))
                    modi_num[program_number]+=1

    for xref in xref_table[program_number] :
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

def relative_offset(line,next_line_index,symbol) :
    global pc_or_base,literal_table
    next_locctr=token_table[next_line_index].locationCounter
    go_addr=get_symbol_value(program_number,symbol)
    masking=1
    if line.instructionFormat==3 :
        for i in range(0,11) :
            masking=masking<<1
            masking=masking|1
    else :
        for i in range(0,19) :
            masking=masking<<1
            masking=masking|1

    if go_addr==-1 :
        for find_lit in literal_table[program_number] :
            if find_lit.literal==symbol :
                if ((-2048<=find_lit.address-next_locctr) and (find_lit.address-next_locctr<=2048)) :
                    pc_or_base=1
                    return (find_lit.address-next_locctr)&masking
                elif ((0<=find_lit.address-next_locctr) and (find_lit.address-next_locctr<=4096)) :
                    pc_or_base=0
                    return (find_lit.address-next_locctr)&masking
                else :
                    return -1
        return 0
    elif go_addr==0 :
        return 0

    if ((-2048<=go_addr-next_locctr) and (go_addr-next_locctr<=2048)) :
        pc_or_base=1
        return (go_addr-next_locctr)&masking
    elif ((0<=go_addr-next_locctr) and (go_addr-next_locctr<=4096)) :
        pc_or_base=0
        return (go_addr-next_locctr)&masking
    else :
        return -1

def equate_refine(line) :
    global program_number
    str_l=len(line.operand[0])
    i=0
    operator_ptr=0
    op1=0
    op2=0

    if line.operand[0]=='*' : return 0
    else :
        while line.label!=symbol_table[program_number][i].symbol : i+=1

        while str(line.operand[0][operator_ptr]).isalpha() : operator_ptr+=1

        temp=line.operand[0][operator_ptr]

        op1=get_symbol_value(program_number,line.operand[0][0:operator_ptr])
        op2=get_symbol_value(program_number,line.operand[0][operator_ptr+1:])

        if temp=='-' :
            symbol_table[program_number][i].address=op1-op2
        elif temp=='+' :
            symbol_table[program_number][i].address=op1+op2
        elif temp=='*' :
            symbol_table[program_number][i].address=op1*op2
        elif temp=='/' :
            symbol_table[program_number][i].address=op1/op2
        line.locationCounter=symbol_table[program_number][i].address

def indirect_immediate(line,index) :
    if inst[index][OPER_NUM]!='0' :
        if line.instructionFormat==3 or line.instructionFormat==4 :
            if line.operand[0][0]=='#' : return int(1)
            elif line.operand[0][0]=='@' : return int(2)
            else : return int(3)
        else :
            return int(0)
    else :
        if line.instructionFormat==3 or line.instructionFormat==4 :
            return int(3)
        else :
            return int(0)
    pass

def base_register_init() :
    global program_number, base_register
    if base_register[0]=='' : return -1
    else : base_register[1]=get_symbol_value(program_number,base_register.symbol)
    return 0

def get_symbol_value(pr_num,symbol) :
    for find in symbol_table[pr_num] :
        if find.symbol==symbol :
            return find.address

    for ref_find in xref_table[pr_num] :
        if ref_find[0]==symbol :
            return 0

    return -1

def make_output(filename) :
    global cur_line_num, token_table
    for i in range(0,cur_line_num+1) :
        printed=0
        if token_table[i].instructionFormat!=-1 or i==0 :
            print ('{0:04X}'.format(token_table[i].locationCounter),end='    ')

        if token_table[i].label!='' :
            print('{0:6s}'.format(token_table[i].label),end='    ')
        else :
            print(end='    ')

        if token_table[i].operator!='' :
            print((token_table[i].operator),end='    ')
        else :
            print(end='    ')

        if token_table[i].operand!='' :
            for oprnd in token_table[i].operand :
                if printed==0 :
                    print(oprnd,end='   ')
                    printed=1
                else :
                    print(','+oprnd,end='   ')
        else :
            print(end=' ')

        if token_table[i].cmt!='' :
            print(token_table[i].cmt)
        else :
            print()

    if filename!='' :
        make_objectcode(filename)

    pass

def make_objectcode(filename) :
    global symbol_table, token_table,xref_table,xdef_table,program_number,token_line
    file=open(filename,'w')
    line_max=0
    object_code_sum=0
    reserved=-1
    out_ptr=0
    bef_ptr=0
    line_start_ptr=0
    line_num_ptr=0

    for i in range (0,program_number+1) :
        line_max=0
        out_ptr=0
        reserved=0

        file.write('H{0:6s}'.format(symbol_table[i][0].symbol))
        file.write('{0:06X}'.format(int(symbol_table[i][0].address)))
        file.write('{0:06X}\n'.format(int(program_length[i])))

        if xdef_num[i]!=0 :
            file.write('D')
            for xdef in xdef_table[i] :
                file.write('{0:6s}'.format(xdef))
                file.write('{0:06X}'.format(get_symbol_value(i,xdef)))
            file.write('\n')

        if xref_num[i]!=0 :
            file.write('R')
            for xref in xref_table[i] :
                file.write('{0:6s}'.format(xref))
            file.write('\n')

        while 1 :
            object_code_sum=0
            bef_ptr=0

            line_max=0
            while line_max<=30 and line_num_ptr<=token_line :
                if token_table[line_num_ptr].operator!='' :
                    if token_table[line_num_ptr].operator=='CSECT' :
                        out_ptr=1
                        break
                    if token_table[line_num_ptr].operator=='RESW' or token_table[line_num_ptr].operator=='RESB' :
                        reserved=1
                        break
                if token_table[line_num_ptr].cmt!='' :
                    line_max+=token_table[line_num_ptr].instructionFormat
                    bef_ptr=token_table[line_num_ptr].instructionFormat
                line_num_ptr+=1

            if line_max>=30 :
                line_num_ptr-=1
                object_code_sum=(line_max-bef_ptr)
            else :
                object_code_sum=line_max

            if line_num_ptr>=token_line :
                out_ptr=1

            file.write('T{0:06X}'.format(token_table[line_start_ptr].locationCounter))
            file.write('{0:02X}'.format(int(object_code_sum)))

            j=line_start_ptr
            while j<line_num_ptr :
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
            file.write('\n')

            if reserved==1 :
                while line_num_ptr<token_line :
                    if token_table[line_num_ptr].operator!='' :
                        if token_table[line_num_ptr].operator=='CSECT' :
                            out_ptr=1
                            break
                    if token_table[line_num_ptr].cmt!='' :
                        break

                    line_num_ptr+=1

            line_start_ptr=line_num_ptr
            if out_ptr==1 :
                break

        if line_num_ptr<token_line :
            while token_table[line_num_ptr].cmt=='' :
                line_num_ptr+=1
        line_start_ptr=line_num_ptr

        j=0
        while j<modi_num[i] :
            file.write('M')
            if modification_table[i][j].isOpcode==1 :
                file.write('{0:06X}'.format(modification_table[i][j].address+1))
            else :
                file.write('{0:06X}'.format(modification_table[i][j].address))

            file.write('{0:02X}'.format(modification_table[i][j].changeSize))
            file.write('{0:s}'.format(modification_table[i][j].operator))
            file.write('{0:s}'.format(modification_table[i][j].modificationName))
            file.write('\n')
            j+=1

        if i==0 :
            file.write('E{0:06X}\n'.format(symbol_table[i][0].address))
        else :
            file.write('E\n')
        file.write('\n')
    file.close()
    pass


def __main__():
    if init_assembler()<0 :
        print("init_assembler : initialization failed.")
        return
    if assem_pass1()<0 :
        print("assem_pass1 : initialization failed.")
        return
    if assem_pass2()<0 :
        print("assem_pass2 : initialization failed.")
        return

    make_output('output.txt')

__main__()
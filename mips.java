
public class projectOne {
	public static int opCode;
	public static int regS;
	public static int regT;
	public static int regD;
	public static int shamt;
	public static int function;
	public static short offset;
	public static int x;
	public static int count = 0x7A060;
	public static String opName;
	

	public static void getInstructions(int i) {
		//first get opcode, then decide between formats
		//Decide between R and I formats
		getOpcode(i);
		if (opCode == 0) {
			rFormat(i);
		}
		else iFormat(i);
	}
	
	public static void rFormat(int i){
		//Should Print opcode func rd,rs,rt 
		getFunction(i);
		int tempRegD = i >>>11;
		regD = tempRegD & 0x1F;
		System.out.print("$" +regD +",");
		//Get RegS
		int tempRegS = i >>>21;
		regS = tempRegS & 0x1F;
		System.out.print("$" +regS +",");
		//Get RegT
		int tempRegT = i >>> 16;
		regT = tempRegT & 0x1F;
		System.out.print("$" +regT);
	}
	
	public static void iFormat(int i) {

		getOpcode(i);
		if (opName.equals("lw ")) {
			System.out.print(opName);
			//Get RegT
			int tempRegT = i >>> 16;
			regT = tempRegT & 0x1F;
			System.out.print("$" +regT+",");
			//Get Offset
			offset = (short) (i&0xFFFF);
			System.out.print(offset +"(");
			//Get RegS
			int tempRegS = i >>>21;
			regS = tempRegS & 0x1F;
			System.out.print("$" +regS +") ");
		}
		else if (opName.equals("sw ")) {
			System.out.print(opName);
			//Get RegT
			int tempRegT = i >>> 16;
			regT = tempRegT & 0x1F;
			System.out.print("$" +regT+",");
			//Get Offset
			offset = (short) (i&0xFFFF);
			System.out.print(offset +"(");
			//Get RegS
			int tempRegS = i >>>21;
			regS = tempRegS & 0x1F;
			System.out.print("$" +regS +") ");
		}
		//branch
		else {
			System.out.print(opName);
			//Get RegT
			int tempRegT = i >>> 16;
			regT = tempRegT & 0x1F;
			System.out.print("$" +regT+",");
			//Get RegS
			int tempRegS = i >>>21;
			regS = tempRegS & 0x1F;
			System.out.print("$" +regS +" ");
			//Get Offset
			offset = (short) (i&0xFFFF);
			int branchOffset = offset << 2;
			System.out.print(" address ");
			int tempCount = count;
			int targetAddress = tempCount+=branchOffset;
			System.out.printf("%X", targetAddress);
			
		}		
	}
	
	public static void getOpcode(int i){
		int tempOp = i >>>26;
		opCode = tempOp & 0x3F;
		if (opCode!= 0) {

			switch (opCode) {
			case 4:
				opName ="beq ";
				break;
			case 5:
				opName="bne ";
				break;
			case 35: 
				opName = "lw ";
				break;
			case 43:
				opName = "sw ";
				break;
			}
		}
	}
	
	public static void getFunction(int i) {
		function = i & 0x3F;
		String funcName;
		switch (function) {
		case 26:
			funcName = "div";
			System.out.print(funcName +" ");
			break;
		case 32:
			funcName = "add";
			System.out.print(funcName +" ");
			break;
		case 34:
			funcName = "sub";
			System.out.print(funcName +" ");
			break;
		case 36:
			funcName = "and";
			System.out.print(funcName +" ");
			break;
		case 37:
			funcName = "or";
			System.out.print(funcName +" ");
			break;
		case 43:
			funcName = "slt";
			System.out.print(funcName +" ");
			break;
		case 54:
			funcName = "tne";
			System.out.print(funcName +" ");
			break;
		}
	}
	
	public static void getFunc(int i) {
		System.out.println(i);
	}
	
	public static void main(String[] args) {
		int[] instructionSet = new int[11];
		instructionSet[0] = 0x022DA822;
		instructionSet[1] = 0x12A70003;
		instructionSet[2] = 0x8D930018;
		instructionSet[3] = 0x02689820;
		instructionSet[4] = 0xAD930018;
		instructionSet[5] = 0x02697824;
		instructionSet[6] = 0xAD8FFFF4;
		instructionSet[7] = 0x018C6020;
		instructionSet[8] = 0x02A4A825;
		instructionSet[9] = 0x158FFFF6;
		instructionSet[10] = 0x8E59FFF0;
		
		for (int i = 0; i < instructionSet.length-1; i++) {

			System.out.printf("%X", count);
			System.out.print(" ");
			getInstructions(instructionSet[i]);
			count+=4;
			System.out.println();
		}
	}
}

package bayeos.logger;

public final class LoggerConstants {
	
	public static final byte SetChannelAddress =  0x1;
	public static final byte GetChannelAddress = 0x2;
	public static final byte SetAutoSearch = 0x3;
	public static final byte GetAutoSearch = 0x4;
	public static final byte SetPin = 0x5;
	public static final byte GetPin = 0x6;
	public static final byte GetTime = 0x7;
	public static final byte SetTime = 0x8;
	public static final byte GetName = 0x9;
	public static final byte SetName = 0xa;
	public static final byte StartData = 0xb;
	public static final byte StopData = 0xc;
	public static final byte GetVersion = 0xd;
	public static final byte GetSamplingInt = 0xe;
	public static final byte SetSamplingInt = 0xf;
	public static final byte GetTimeOfNextFrame = 0x10;
	public static final byte StartLiveData = 0x11;
	public static final byte GetBatteryStatus = 0x16;
	
		
	/* 
	 Deprecated since 1.1  
	 Params:
	 1 = reset buffer, 
	 2 = reset read pointer 
	 3 = set read pointer to write pointer,	   
	*/
	public static final byte StopLiveData = 0xc;	
	public static final byte ModeStop = 0x12;
	public static final byte Seek = 0x13;
	
	/* 
	 Params:
	 [unsigned long start_pos - optional]
	 [unsigned long end_pos - optional] 	 
	 */
	public static final byte StartBulkData = 0x14;
	
	/* 
	  Param manadatory: 
	  0: save current read pointer to EEPROM, 
	  1: erase 
	  2: set read pointer to last EEPROM pos
	  3: set read pointer to write pointer
	  4: set to time of last binary dump
	  5: get read pos 
	 */ 
	public static final byte BufferCommand = 0x15;	
	
	
	// BufferCommands
	public static final byte BC_SAVE_READ_TO_EPROM = 0x0;
	public static final byte BC_ERASE = 0x1;
	public static final byte BC_SET_READ_TO_LAST_EPROM_POS = 0x2;
	public static final byte BC_SET_READ_TO_WRITE_POINTER = 0x3;
	public static final byte BC_SET_READ_TO_LAST_OF_BINARY_END_POS = 0x4;
	public static final byte BC_GET_READ_POS = 0x5;
			
	// DataMode 
	public static final byte DM_NEW = 0x0;
	public static final byte DM_FULL = 0x1;
		
	// StopMode 
	public static final byte SM_STOP = 0x0;
	public static final byte SM_RESET = 0x1;
	public static final byte SM_CANCEL = 0x2;
	
	
	private LoggerConstants(){
		
	}
}


public enum AudioFormat {
	MP3,
	WMA,
	AAC;
	
	static AudioFormat getAudioF(String input) {
		if(input.equals("AAC")) {
			return AudioFormat.AAC;
		}else if(input.equals("MP3")) {
			return AudioFormat.MP3;
		}else if(input.equals("WMA")){
			return AudioFormat.WMA;
		}else{
			return null;
		}
	}
	
}

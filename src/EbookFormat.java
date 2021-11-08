
public enum EbookFormat {
	EPUB, 
	MOBI, 
	AZW3, 
	PDF;
	
	static EbookFormat getEbookF(String input) {
		if(input.equals("AZW3")) {
			return EbookFormat.AZW3;
		}else if(input.equals("EPUB")) {
			return EbookFormat.EPUB;
		}else if(input.equals("MOBI")) {
			return EbookFormat.MOBI;
		}else if(input.equals("PDF")) {
			return EbookFormat.PDF;
		}else {
			return null;
		}
	}
	
}

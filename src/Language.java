
public enum Language {
	ENGLISH {
        public String toString() {
            return "English";
        }
	},
	FRENCH {
        public String toString() {
            return "French";
        }
	};
	
	static Language getLang(String input) {
		input = input.toLowerCase();
		if(input.equals("english")) {
			return Language.ENGLISH;
		}else if(input.equals("french")){
			return Language.FRENCH;
		}else {
			return null;
		}
	}
	
}

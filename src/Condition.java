
public enum Condition {
	NEW {
        public String toString() {
            return "new";
        }
	},
	USED {
        public String toString() {
            return "used";
        }
	};
	
	static Condition getCon(String input) {
		input = input.toLowerCase();
		if(input.equals("new")) {
			return Condition.NEW;
		}else if(input.equals("used")){
			return Condition.USED;
		}else {
			return null;
		}
	}
	
}

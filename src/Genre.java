
public enum Genre {
	POLITICS {
        public String toString() {
            return "Politics";
        }
	},
	MEDICINE {
        public String toString() {
            return "Medicine";
        }
	},
	BUSINESS {
        public String toString() {
            return "Business";
        }
	},
	COMPUTERSCIENCE {
        public String toString() {
            return "Computer Science";
        }
	},
	BIOGRAPHY {
		public String toString() {
			return "Biography";
		}
	};
	
	static Genre getGen(String input) {
		input = input.toLowerCase();
		if(input.equals("politics")) {
			return Genre.POLITICS;
		}else if(input.equals("medicine")) {
			return Genre.MEDICINE;
		}else if(input.equals("business")) {
			return Genre.BUSINESS;
		}else if(input.equals("computer science")) {
			return Genre.COMPUTERSCIENCE;
		}else if(input.equals("biography")){
			return Genre.BIOGRAPHY;
		}else {
			return null;
		}
	}
	
}

package model;

    public class ApplicationException extends Exception {
        private String message;
        public ApplicationException(String messsage){
            this.message = messsage;
        }

        @Override
        public String toString() {
            return "One or more Error Occurred:\n" + message;
        }
    }


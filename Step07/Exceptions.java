class ItemNotFoundException extends Exception {
    ItemNotFoundException(String message) {
        super(message);
    }
}

class MemberNotFoundException extends Exception {
    MemberNotFoundException(String message) {
        super(message);
    }
}

class ItemNotAvailableException extends Exception {
    ItemNotAvailableException(String message) {
        super(message);
    }
}

class BorrowLimitExceededException extends Exception {
    BorrowLimitExceededException(String message) {
        super(message);
    }
}

class ItemNotOnLoanException extends Exception {
    ItemNotOnLoanException(String message) {
        super(message);
    }
}

class ReservationNotAllowedException extends Exception {
    ReservationNotAllowedException(String message) {
        super(message);
    }
}
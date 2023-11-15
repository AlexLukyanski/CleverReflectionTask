package by.clever.reflection.service.validation;


import by.clever.reflection.entity.constant.MusicGenre;

import java.time.LocalDate;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class MusicBandDTOValidation {

    private final boolean isIdValid;
    private final boolean isNameValid;
    private final boolean isGenreValid;
    private final boolean isCreationDateValid;
    private final boolean isWorkPhoneNumberValid;
    private final boolean isWorkEmailValid;

    public MusicBandDTOValidation(boolean isIdValid, boolean isNameValid, boolean isGenreValid, boolean isCreationDateValid, boolean isWorkPhoneNumberValid, boolean isWorkEmailValid) {
        this.isIdValid = isIdValid;
        this.isNameValid = isNameValid;
        this.isGenreValid = isGenreValid;
        this.isCreationDateValid = isCreationDateValid;
        this.isWorkPhoneNumberValid = isWorkPhoneNumberValid;
        this.isWorkEmailValid = isWorkEmailValid;
    }

    public boolean isMusicBandDTOValid() {
        return isIdValid && isNameValid && isGenreValid
                && isCreationDateValid && isWorkPhoneNumberValid && isWorkEmailValid;
    }

    public static MusicBandDTOValidationBuilder builder() {
        return new MusicBandDTOValidationBuilder();
    }

    public static class MusicBandDTOValidationBuilder {
        private boolean isIdValid;
        private boolean isNameValid;
        private boolean isGenreValid;
        private boolean isCreationDateValid;
        private boolean isWorkPhoneNumberValid;
        private boolean isWorkEmailValid;

        public MusicBandDTOValidationBuilder() {
        }

        public MusicBandDTOValidationBuilder isIdValid(UUID uuid) {
            if (uuid == null) {
                isIdValid = false;
            } else {
                isIdValid = true;
            }
            return this;
        }

        public MusicBandDTOValidationBuilder isNameValid(String name) {
            if (name == null) {
                isNameValid = false;
                return this;
            }

            String regex = "^.{1,30}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(name);

            isNameValid = matcher.matches();
            return this;
        }

        public MusicBandDTOValidationBuilder isGenreValid(MusicGenre genre) {
            if (genre == null) {
                isGenreValid = false;
                return this;
            }

            isGenreValid = false;
            MusicGenre[] genres = MusicGenre.values();

            for (MusicGenre genreName : genres) {
                if (genreName.equals(genre)) {
                    isGenreValid = true;
                    break;
                }
            }
            return this;
        }

        public MusicBandDTOValidationBuilder isCreationDateValid(LocalDate creationDate) {
            if (creationDate == null) {
                isCreationDateValid = false;
                return this;
            }

            LocalDate now = LocalDate.now();

            if (creationDate.isAfter(now)) {
                isCreationDateValid = false;
                return this;
            }

            isCreationDateValid = true;
            return this;
        }

        public MusicBandDTOValidationBuilder isWorkPhoneNumberValid(String workPhone) {
            if (workPhone == null) {
                isWorkPhoneNumberValid = false;
                return this;
            }

            String regex = "^(\\+)?(\\d{1,2})?[( .-]*(\\d{3})[) .-]*(\\d{3,4})[ .-]?(\\d{4})$\n";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(workPhone);

            isWorkPhoneNumberValid = matcher.matches();
            return this;
        }

        public MusicBandDTOValidationBuilder isWorkEmailValid(String email) {

            if (email == null) {
                isWorkEmailValid = false;
                return this;
            }

            String regex = "^(.+)@(\\S+)$";

            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(email);

            isWorkEmailValid = matcher.matches();
            return this;
        }

        public MusicBandDTOValidation build() {
            return new MusicBandDTOValidation(this.isIdValid, this.isNameValid, this.isGenreValid, this.isCreationDateValid, this.isWorkPhoneNumberValid, this.isWorkEmailValid);
        }
    }
}
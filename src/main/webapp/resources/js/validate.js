function validate_form() {
    valid = true;

    if ($('input[type=checkbox]:checked').length == 0) {
        alert("ERROR! Please select at least one checkbox");
        valid = false;
    }

    return valid;
}
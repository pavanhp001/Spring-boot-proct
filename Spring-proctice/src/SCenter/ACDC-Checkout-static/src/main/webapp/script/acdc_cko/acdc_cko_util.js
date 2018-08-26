
/**
 * This function is used for validate entered value is number or character
 * @param evt -- current event
 */
function isNumber(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57) && charCode != 46 && charCode != 39) {
        return false;
    }
    return true;
}


/**
 * This function is used for populate dashe(s) between phone number's
 * @param event -- current event
 * 
 */
function prepopulatePhoneDashes(event) {
    if (event.keyCode != 8 && event.keyCode != 46 ) {
        if (this.value.length == 3) {
            this.value = this.value + "-";
        } else if (this.value.length == 7) {
            this.value = this.value + "-";
        } else {
            this.value = this.value.replace(/(\d{3})\-?(\d{3})\-?(\d{4})/, '$1-$2-$3');
        }
    }
}
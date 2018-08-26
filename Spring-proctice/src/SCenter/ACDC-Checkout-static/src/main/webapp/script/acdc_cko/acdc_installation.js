/************************
 * 
 *  INSTALLATION PAGE 
 * 
 * ***********************/


var datepickerArr = [];
var holidays = ["11/11/2014", "11/27/2014", "12/25/2014",
    "01/01/2015", "01/19/2015", "02/16/2015", "05/25/2015", "07/03/2015",
    "09/07/2015", "10/12/2015", "11/11/2015", "11/26/2015", "12/25/2015",
    "01/01/2016", "01/18/2016", "02/15/2016", "05/30/2016", "07/04/2016",
    "09/05/2016", "10/10/2016", "11/11/2016", "11/24/2016", "12/26/2016",
    "01/01/2017", "01/16/2017", "02/20/2017", "05/29/2017", "07/04/2017",
    "09/04/2017", "10/09/2017", "11/10/2017", "11/23/2017", "12/25/2017",
    "01/01/2018", "01/15/2018", "02/19/2018", "05/28/2018", "07/04/2018",
    "09/03/2018", "10/08/2018", "11/12/2018", "11/22/2018", "12/25/2018",
    "01/01/2019", "01/21/2019", "02/18/2019", "05/27/2019", "07/04/2019",
    "09/02/2019", "10/14/2019", "11/11/2019", "11/28/2019", "12/25/2019"
];
var instalationFieldsArr = ['lineItem.schedulingInfo.desiredStartDate2.date', 'lineItem.schedulingInfo.desiredStartDate2.time', 'lineItem.schedulingInfo.desiredStartDate.time', 'lineItem.schedulingInfo.desiredStartDate.date']
var removeAsstrickExtList = ["consumer.previousAddress.dwelling.stateOrProvince",
    "consumer.previousAddress.dwelling.line2info", "consumer.previousAddress.dwelling.line2"
];
var enableBackBtnInfoArr = [];
var isBackButtonInfo = true;
var selectedSerivceType = "";
var enteredValueArr = [];
$(document).ready(function() {

    try {
        $("input[name='installtype']").on("change", function() {
            var installtype = this.id;
            if (installtype == "proinstall") {
                $("#pro_install").show();
            } else {
                $("#pro_install").hide();
            }
        });
        $("input[name='ccbillingadd']").on("change", function() {
            if (this.checked) {
                $("#ccbilling_address_sec").hide();
            } else {
                $("#ccbilling_address_sec").show();
            }
        });
        $("input[name='ccbillingadd']").trigger("change");
        buildPaymentAndInstallationContent();
        var validateForm;
        $("#bottom-footer .next_step span").click(submitInstallationInfo);
        $("#customizations .next_step span").click(submitInstallationInfo);
        var date = new Date();
        var currentMonth = date.getMonth() + 1;
        var currentDay = date.getDay();
        var currentYear = date.getFullYear();
        var twoMonths = currentMonth + 2;
        var endDateval = twoMonths + "/" + currentDay + "/" + currentYear;
        $('.firstinstall').on("change", function() {
            var arr = [];
            $('.firstinstall').find('input, select').each(function() {
                arr.push($(this).val());
            });
            var firstInstallDate = arr[0];
            var firstInstallTime = arr[1];
            var secondInstallDate = arr[2];
            var secondInstallTime = arr[3];
            $(".installErr").text("");
            if (firstInstallDate != undefined && firstInstallDate != '') {
                if (firstInstallDate === secondInstallDate && (firstInstallTime != '' && firstInstallTime != undefined)) {
                    $('[valuetarget="lineItem.schedulingInfo.desiredStartDate2.time"] option').each(function() { //loop through options
                        if (!($(this).val().indexOf(firstInstallTime) == -1)) {
                            $(this).hide();
                        } else {
                            $(this).show();
                        }
                    });
                    if (firstInstallTime === secondInstallTime) {
                        var installMsg = $("<span></span>").addClass("installErr").text("first and second installation date and time must not be same");
                        $(".secondRequest").after(installMsg);
                    }
                } else {
                    $('[valuetarget="lineItem.schedulingInfo.desiredStartDate2.time"] option').each(function() { //loop through options
                        $(this).show();
                    });
                }
            }
        });

        buildPreviousCheckedRadioContent();
        buildPreviousCheckedContent();
        buildPreviousSelectContent();
        appendEvents("input[type='radio']", buildCheckedContent, "click");
        displayCKOLoader(true);
        $('input').attr('autocomplete', 'off');
        $("#iData").click(changeOrientation)
        $(window).on("orientationchange", changeOrientation);
        appendEvents("select", dropDownChnageBuild, "change");
        appendEvents("input", setDynamicHeight, "click");
        appendEvents("select", setDynamicHeight, "change");
        appendEvents("input[validation*='Numeric']",isNumber,"keypress");
        appendPageEventMandatoryStar();
        var dispayPaymentInnerContent = $(".paymentInformation .dispayPaymentInnerContent").length
        if (dispayPaymentInnerContent == 0) {
            $(".paymentInformationCotent").hide();
        }
        if ($(".installTime .dialogueDropDown").length == 0) {
            $(".installInfoContent").hide();
        }
        if (dispayPaymentInnerContent == 0 && $(".installTime .dialogueDropDown").length == 0) {

            $("#customizations").after($("<span></span>").text("No Information"));
        }
    } catch (e) {
        console.log(e);
    }
});

function editDays(date) {
    for (var i = 0; i < holidays.length; i++) {
        if (new Date(holidays[i]).toString() == date.toString()) {
            return false;
        }
    }
    return true;
}

function calendarValidations(validationTypeValue, currentObj) {
    var validationType = "";
    if (currentObj.attr("validation") != undefined && currentObj.attr("validation") != "") {
        validationTypeValue = currentObj.attr("validation").split(":");
        validationType = validationTypeValue[0];
    }
    if (validationType != undefined && validationType.match(/^([5-7]+)CAL$/)) {
        if (validationType == "6CAL") {
            var dateRange;
            var date = new Date();
            var endDates = new Date();
            if (validationTypeValue[1] != undefined) {
                dateRange = validationTypeValue[1].split("-");

                if (dateRange[0] != undefined) {
                    var actualDate = parseInt(dateRange[0])
                    var x = 0;


                    while (x <= actualDate) {
                        date.setDate(date.getDate() + 1);
                        if (date.getDay() != 0) {
                            x++;
                        }
                    }
                }
                if (dateRange[1] != undefined) {
                    var y = 0;
                    var actualEndDate = parseInt(dateRange[1]);
                    while (y <= actualEndDate) {
                        endDates.setDate(endDates.getDate() + 1);
                        if (endDates.getDay() != 0) {
                            y++;
                        }
                    }
                }
            }
            currentObj.datepicker({
                format: 'mm/dd/yyyy',
                beforeShowDay: editDays,
                startDate: date,
                endDate: endDates,
                daysOfWeekDisabled: [0],
                autoclose: true
            });
        } else if (validationType != undefined && validationType == '5CAL') {
            var dateRange;
            var date = new Date();
            var endDates = new Date();
            if (validationTypeValue[1] != undefined) {
                dateRange = validationTypeValue[1].split("-");

                if (dateRange[0] != undefined) {
                    var actualDate = parseInt(dateRange[0])
                    var x = 0;


                    while (x <= actualDate) {
                        date.setDate(date.getDate() + 1);
                        if (date.getDay() != 0 && date.getDay() != 6) {
                            x++;
                        }
                    }
                }
                if (dateRange[1] != undefined) {
                    var y = 0;
                    var actualEndDate = parseInt(dateRange[1]);
                    while (y <= actualEndDate) {
                        endDates.setDate(endDates.getDate() + 1);
                        if (endDates.getDay() != 0 && endDates.getDay() != 6) {
                            y++;
                        }
                    }
                }
            }
            currentObj.datepicker({
                format: 'mm/dd/yyyy',
                startDate: date,
                beforeShowDay: editDays,
                endDate: endDates,
                daysOfWeekDisabled: [0, 6],
                autoclose: true
            });
        } else if (validationType != undefined && validationType == '7CAL') {
            currentObj.datepicker({
                format: 'mm/dd/yyyy',
                autoclose: true
            });
        }
    } else {
        currentObj.datepicker({
            format: 'mm/dd/yyyy',
            autoclose: true
        });
    };

}
/*************************************************************************
 * 
 * ****************** build installation content *************************
 * 
 * ************************************************************************/
function buildPaymentAndInstallationContent() {
    //Get dialogue Type List from installation json
    $.each(installationJson.dialogueTypeList, function(i, dialogueTypeList) {
        //Get data group list from dialogue type List
        $.each(dialogueTypeList.dataGroupList, function(j, dataGroupList) {
            var homeServicesBody = $("<div></div>").attr({
                "class": "home-services-body"
            });
            //Get data feild list from data group list
            $.each(dataGroupList.dataFeildList, function(k, dataFeildListObject) {
                if (dataFeildListObject.isEnabled == true) {
                    buildHtmlElements(dataFeildListObject, true);
                } else {
                    enableArr.push(dataFeildListObject);
                }
            });
        });
    });
}

/*************************************************************************
 * 
 * ****************** build installation content html elements ***********
 * 
 * ************************************************************************/
function buildHtmlElements(dataFeildListObject, isEnable) {

    if ((dataFeildListObject.valueTarget != undefined && dataFeildListObject.valueTarget != "" && $.inArray(dataFeildListObject.valueTarget, instalationFieldsArr) > -1)) {
        var dispayContent = $("<div></div>").attr({
            "class": "dispayContent "
        });
        var dispayRightContent = $("<div></div>").attr({
            "class": "dispayRightContent "
        });
        var dataFieldDisclosure = $("<div></div>").attr({
            "class": "alert alert-info"
        }).text(dataFeildListObject.dataFieldText);
        var dataFieldDisclosureInfo = $("<div></div>").attr({
            "class": "dataFieldDisclosureInfo"
        }).text("i");

        var displayHtmlTag;
        var dispalyInput = dataFeildListObject.dispalyInput.toLowerCase();
        //Check html elements types 
        if (dispalyInput == "dropdown" && (dataFeildListObject.valueTarget == "lineItem.schedulingInfo.desiredStartDate.time" || dataFeildListObject.valueTarget == "lineItem.schedulingInfo.desiredStartDate2.time")) {
            displayHtmlTag = $("<select></select>").attr({
                "id": dataFeildListObject.dataFieldExID,
                "displayType": dataFeildListObject.type,
                "class": "dialogueDropDown",
                "name": dataFeildListObject.dataFieldExID,
                "valueTarget": dataFeildListObject.valueTarget
            });
            var option = $('<option/>');
            option.attr({
                'value': ""
            }).text("Please Select");
            displayHtmlTag.append(option);
            //Build drop down options from data constraint value list.
            $.each(dataFeildListObject.dataConstraintValueList, function(l, dataConstraintValue) {
                option = $('<option/>');
                if (dataFeildListObject.enteredValue != undefined && dataFeildListObject.enteredValue != "") {
                    if (dataConstraintValue == dataFeildListObject.enteredValue) {
                        option.attr({
                            'value': dataConstraintValue,
                            "selected": "selected"
                        }).text(dataConstraintValue);
                    } else {
                        option.attr({
                            'value': dataConstraintValue
                        }).text(dataConstraintValue);
                    }
                } else {
                    option.attr({
                        'value': dataConstraintValue
                    }).text(dataConstraintValue);
                }
                displayHtmlTag.append(option);
                if (dataFeildListObject.valueTarget == "lineItem.schedulingInfo.desiredStartDate.time") {
                    $(".firstRequest").append(displayHtmlTag);

                } else if (dataFeildListObject.valueTarget == "lineItem.schedulingInfo.desiredStartDate2.time") {
                    $(".secondRequest").append(displayHtmlTag);
                }

            });
        } else if (dispalyInput == "text" && (dataFeildListObject.valueTarget == "lineItem.schedulingInfo.desiredStartDate.date" || dataFeildListObject.valueTarget == "lineItem.schedulingInfo.desiredStartDate2.date")) {

            if (dataFeildListObject.enteredValue != undefined && dataFeildListObject.enteredValue != "" && dataFeildListObject.valueTarget == "lineItem.schedulingInfo.desiredStartDate.date") {
                $("input[valuetarget='lineItem.schedulingInfo.desiredStartDate.date']").attr({
                    "id": dataFeildListObject.dataFieldExID,
                    "name": dataFeildListObject.dataFieldExID,
                    "value": dataFeildListObject.enteredValue,
                    "validation": dataFeildListObject.validation
                });
                calendarValidations(dataFeildListObject.validation, $("input[valuetarget='lineItem.schedulingInfo.desiredStartDate.date']"));
            } else if (dataFeildListObject.enteredValue != undefined && dataFeildListObject.enteredValue != "" && dataFeildListObject.valueTarget == "lineItem.schedulingInfo.desiredStartDate2.date") {
                $("input[valuetarget='lineItem.schedulingInfo.desiredStartDate2.date']").attr({
                    "id": dataFeildListObject.dataFieldExID,
                    "name": dataFeildListObject.dataFieldExID,
                    "value": dataFeildListObject.enteredValue,
                    "validation": dataFeildListObject.validation
                });
                calendarValidations(dataFeildListObject.validation, $("input[valuetarget='lineItem.schedulingInfo.desiredStartDate2.date']"));
            } else if (dataFeildListObject.valueTarget == "lineItem.schedulingInfo.desiredStartDate.date") {
                $("input[valuetarget='lineItem.schedulingInfo.desiredStartDate.date']").attr({
                    "id": dataFeildListObject.dataFieldExID,
                    "name": dataFeildListObject.dataFieldExID,
                    "validation": dataFeildListObject.validation
                });
                calendarValidations(dataFeildListObject.validation, $("input[valuetarget='lineItem.schedulingInfo.desiredStartDate.date']"));
            } else if (dataFeildListObject.valueTarget == "lineItem.schedulingInfo.desiredStartDate2.date") {
                $("input[valuetarget='lineItem.schedulingInfo.desiredStartDate2.date']").attr({
                    "id": dataFeildListObject.dataFieldExID,
                    "name": dataFeildListObject.dataFieldExID,
                    "validation": dataFeildListObject.validation
                });
                calendarValidations(dataFeildListObject.validation, $("input[valuetarget='lineItem.schedulingInfo.desiredStartDate2.date']"));
            }

        }
        if (dispalyInput != undefined && dispalyInput == "disclosure" || dispalyInput != undefined && (dispalyInput == "inforamtion" || dispalyInput == "informational")) {
            if (dispalyInput == "inforamtion") {

                dataFieldDisclosure.append(dataFieldDisclosureInfo);
                dispayContent.append(dataFieldDisclosure);
            } else {
                dispayContent.append(dataFieldDisclosure);
            }
            $(".disclosureContent").append(dispayContent);
        }
    } else {
        var mandatoryStar = $("<span></span>").text("*").attr({
            "class": "mandatory",
            "style": "float: left; margin: 4px; padding: 2px;"
        });
        var dispalyInput = dataFeildListObject.dispalyInput.toLowerCase();
        var dispayContent = $("<div></div>").attr({
            "class": "dispayPaymentInnerContent "
        });
        var dispayPaymentMainContent = $("<div></div>").attr({
            "class": "dispayPaymentMainContent "
        });
        var dispayMandatoryContent = $("<div></div>").attr({
            "class": "dispayMandatoryContent "
        });
        var dispayLeftContent = $("<div></div>").attr({
            "class": "dispayLeftContent "
        });
        var dispayRightContent = $("<div></div>").attr({
            "class": "dispayRightCont"
        });
        var dataFieldDisclosure = $("<div></div>").attr({
            "class": "alert alert-info"
        }).text(dataFeildListObject.dataFieldText);
        var dataFieldDisclosureInfo = $("<div></div>").attr({
            "class": "dataFieldDisclosureInfo"
        }).text("i");
        var htmlTag;

        if (isAppendAstreck(dataFeildListObject) && dispalyInput != undefined && dispalyInput != "disclosure" && dispalyInput != "informational" && dispalyInput != "informational" && dispalyInput != undefined && dispalyInput != "checkbox") {
            dispayMandatoryContent.append(mandatoryStar);
            dispayContent.append(dispayMandatoryContent);
        }

        if (dispalyInput != undefined && dispalyInput == "text") {
            htmlTag = $("<input type='text'/>")
            var validation = dataFeildListObject.validation;
            var maxLength = "";
            if (validation != undefined && validation.split(":")[1] == "Numeric") {
                maxLength = validation.split(":")[0];
            }
            if (dataFeildListObject.enteredValue != undefined || dataFeildListObject.enteredValue != undefined) {
                htmlTag.attr({
                    "id": dataFeildListObject.dataFieldExID,
                    "validation": dataFeildListObject.validation,
                    "name": dataFeildListObject.dataFieldExID,
                    "displayType": dataFeildListObject.type,
                    "maxlength": maxLength,
                    "valueTarget": dataFeildListObject.valueTarget,
                    "class": 'dialogueTextInput',
                    "value": dataFeildListObject.enteredValue
                })
            } else {
                htmlTag.attr({
                    "id": dataFeildListObject.dataFieldExID,
                    "validation": dataFeildListObject.validation,
                    "maxlength": maxLength,
                    "name": dataFeildListObject.dataFieldExID,
                    "displayType": dataFeildListObject.type,
                    "valueTarget": dataFeildListObject.valueTarget,
                    "class": 'dialogueTextInput',
                    "value": ""
                })
            }
            dispayLeftContent.text(dataFeildListObject.dataFieldText);
            dispayRightContent.append(htmlTag);

        } else if (dispalyInput != undefined && dispalyInput == "radio") {
            htmlTag = $("<input type='radio'/>");
            if (dataFeildListObject.enteredValue != undefined || dataFeildListObject.enteredValue != undefined) {
                htmlTag.attr({
                    "id": dataFeildListObject.dataFieldExID,
                    "validation": dataFeildListObject.validation,
                    "name": dataFeildListObject.dataFieldExID,
                    "displayType": dataFeildListObject.type,
                    "valueTarget": dataFeildListObject.valueTarget,
                    "class": 'dialogueTextInput',
                    "value": dataFeildListObject.enteredValue
                })
            } else {
                htmlTag.attr({
                    "id": dataFeildListObject.dataFieldExID,
                    "validation": dataFeildListObject.validation,
                    "name": dataFeildListObject.dataFieldExID,
                    "displayType": dataFeildListObject.type,
                    "valueTarget": dataFeildListObject.valueTarget,
                    "class": 'dialogueTextInput',
                    "value": ""
                })
            }

            dispayLeftContent.text(dataFeildListObject.dataFieldText);
            dispayRightContent.append(htmlTag);
        } else if (dispalyInput != undefined && dispalyInput == "checkbox") {
            htmlTag = $("<input type='checkbox'/>")
            if (dataFeildListObject.enteredValue != undefined || dataFeildListObject.enteredValue != undefined) {
                htmlTag.attr({
                    "id": dataFeildListObject.dataFieldExID,
                    "validation": dataFeildListObject.validation,
                    "name": dataFeildListObject.dataFieldExID,
                    "displayType": dataFeildListObject.type,
                    "valueTarget": dataFeildListObject.valueTarget,
                    "class": 'dialogueTextInput',
                    "value": dataFeildListObject.enteredValue
                })
            } else {
                htmlTag.attr({
                    "id": dataFeildListObject.dataFieldExID,
                    "validation": dataFeildListObject.validation,
                    "name": dataFeildListObject.dataFieldExID,
                    "displayType": dataFeildListObject.type,
                    "valueTarget": dataFeildListObject.valueTarget,
                    "class": 'dialogueTextInput',
                    "value": dataFeildListObject.dataFieldExID
                })
            }
            dispayLeftContent.text(dataFeildListObject.dataFieldText);
            dispayRightContent.append(htmlTag);
        } else if (dispalyInput != undefined && dispalyInput == "dropdown") {
            if (dataFeildListObject.valueTarget != undefined && dataFeildListObject.valueTarget == "consumerFinancialInfo.cardType") {
                htmlTag = $("<select></select>").attr({
                    "id": dataFeildListObject.dataFieldExID,
                    "class": "dialogueDropDown dialogueDropDown",
                    "name": dataFeildListObject.dataFieldExID,
                    "displayType": dataFeildListObject.type,
                    "valueTarget": dataFeildListObject.valueTarget
                });
                var option = $('<option/>');
                option.attr({
                    'value': ""
                }).text("Select");
                htmlTag.append(option);
                if (dataFeildListObject.dataConstraintValueList != undefined) {

                    $.each(dataFeildListObject.dataConstraintValueList, function(l, dataConstraintValue) {
                        option = $('<option/>');
                        option.attr({
                            'value': dataConstraintValue
                        }).text(dataConstraintValue);
                        if (dataFeildListObject.enteredValue != undefined || dataFeildListObject.enteredValue != "") {
                            if (dataFeildListObject.enteredValue == dataConstraintValue) {
                                option.attr("selected", "selected");
                            }
                        }
                        htmlTag.append(option);
                    });
                }
                dispayLeftContent.text(dataFeildListObject.dataFieldText);
                dispayRightContent.append(htmlTag);

            } else if (dataFeildListObject.dataFieldExID != undefined && dataFeildListObject.featureExID == "NEW_XFER_INSTALL") {
                var htmlTag = $("<div></div>").attr({
                    "id": dataFeildListObject.dataFieldExID,
                    "name": dataFeildListObject.dataFieldExID,
                    "displayType": dataFeildListObject.type,
                    "class": "serviceType",
                    "valueTarget": dataFeildListObject.valueTarget
                });
                var submitJson = {
                    'displaytype': dataFeildListObject.type,
                    'name': dataFeildListObject.dataFieldExID,
                    'inputType': "radio"
                };
                if (dataFeildListObject.dataConstraintValueList != undefined) {

                    $.each(dataFeildListObject.dataConstraintValueList, function(l, dataConstraintValue) {
                        var dropDownListContent = $("<div></div>").attr({
                            "class": "dropDownListContent "
                        });
                        option = $("<input type='radio'/>");
                        option.attr({
                            "class": "newOrTransferCustomer",
                            "id": dataFeildListObject.dataFieldExID,
                            'value': dataConstraintValue,
                            "previous": dataConstraintValue,
                            "name": dataFeildListObject.dataFieldExID,
                            "tabindex": "0",
                            "submitJson": JSON.stringify(submitJson)
                        }).text(dataConstraintValue);

                        if (dataFeildListObject.enteredValue != undefined && dataFeildListObject.enteredValue != "") {
                            if (dataFeildListObject.enteredValue == dataConstraintValue) {
                                option.attr({
                                    "checked": "checked"
                                });
                                //option.addClass("active");
                                isBackButtonInfo = false;
                            }
                        }
                        htmlTag.append(option);
                        dropDownListContent.append(dataConstraintValue)
                        htmlTag.append(dropDownListContent);
                    });
                }
                var requiredField = $("<span></span>").attr({
                    "style": "color:red"
                }).text("*")
                dispayLeftContent.append($("<span></span>").text(dataFeildListObject.dataFieldText));
                dispayRightContent.append(htmlTag);
            } else {
                htmlTag = $("<select></select>").attr({
                    "id": dataFeildListObject.dataFieldExID,
                    "class": "dialogueDropDown",
                    "name": dataFeildListObject.dataFieldExID,
                    "displayType": dataFeildListObject.type,
                    "valueTarget": dataFeildListObject.valueTarget
                });
                var option = $('<option/>');
                option.attr({
                    'value': ""
                }).text("Select");
                htmlTag.append(option);
                if (dataFeildListObject.dataConstraintValueList != undefined) {

                    $.each(dataFeildListObject.dataConstraintValueList, function(l, dataConstraintValue) {
                        option = $('<option/>');
                        option.attr({
                            'value': dataConstraintValue
                        }).text(dataConstraintValue);
                        if (dataFeildListObject.enteredValue != undefined || dataFeildListObject.enteredValue != "") {
                            if (dataFeildListObject.enteredValue == dataConstraintValue) {
                                option.attr("selected", "selected");
                            }
                        }
                        htmlTag.append(option);
                    });
                }
                dispayLeftContent.text(dataFeildListObject.dataFieldText);
                dispayRightContent.append(htmlTag);
            }

        } else if ((dispalyInput != undefined && dispalyInput == "disclosure") || (dispalyInput != undefined && dispalyInput == "information") || (dispalyInput != undefined && dispalyInput == "informational")) {
            if (dispalyInput == "information" || dispalyInput == "informational") {

                if (dataFeildListObject.enteredValue != undefined || dataFeildListObject.enteredValue != "") {
                    dispayContent.append(dataFieldDisclosure.text(dataFeildListObject.dataFieldText));
                } else {
                    dataFieldDisclosure.append(dataFieldinforamtion);
                    dispayContent.append(dataFieldDisclosure);
                }
            } else {
                if (dataFeildListObject.enteredValue != undefined || dataFeildListObject.enteredValue != "") {
                    dispayContent.append(dataFieldDisclosure.text(dataFeildListObject.dataFieldText));
                } else {
                    dispayContent.append(dataFieldDisclosure.text(dataFeildListObject.dataFieldText));
                }
            }
            if ($(".dispayPaymentInnerContent").length == 0) {
                $(".paymentInformation").append(dispayContent);
            } else {
                $(".paymentInformation:last").after(dispayContent);
            }
        }
        dispayContent.append(dispayLeftContent);
        dispayContent.append(dispayRightContent);
        dispayPaymentMainContent.append(dispayContent);
        $(".paymentInformation").append(dispayPaymentMainContent);

    }

}
/***********************************************************************************************
 * 
 * ****************** register and unregister events when adding a new elements ****************
 * 
 * **********************************************************************************************/
function appendEvents(tagName, functionName, eventName) {
    $(tagName).unbind(eventName, functionName);
    $(tagName).bind(eventName, functionName);
}

function required(requiredObj, validationMsg) {
    var error_tooltip = $("<div></div>").attr({
        "class": "error_tooltip"
    });
    var glyphicon_triangle_left = $("<span></span>").attr({
        "class": "glyphicon glyphicon-triangle-left"
    });
    var error_tooltip_lbl;
    if (jQuery.type(validationMsg) === "string") {
        error_tooltip_lbl = $("<span></span>").attr({
            "class": "error_tooltip_lbl"
        }).text(validationMsg);
    } else {
        error_tooltip_lbl = $("<span></span>").attr({
            "class": "error_tooltip_lbl"
        });
        error_tooltip_lbl.append(validationMsg);
    }
    error_tooltip.append(glyphicon_triangle_left);
    error_tooltip.append(error_tooltip_lbl);
    requiredObj.after(error_tooltip);
}

/************************************************************************************************
 * 
 * ***************** This function is used for empty validations and date picker validations ****
 * 
 * **********************************************************************************************/
var validatoinArr = [];
var inputArr = [];
var isValidate = true;
function validateInstallations() {
    $(".dialogueTextInput").css("width", " 140px !important;");
    validatoinArr = [];
    var isValid = true;
    $(".error_tooltip").remove();
    $(".home-services-body.error").removeClass("error");
    $(".installErr").text("");
    if ($("input,select").length > 0) {
        // $("input,select").each(function() {

        //Payment empty field validation
        $(".mandatory").parent().parent().find("input,select").each(function(index) {
            if ($(this).is(":visible")) {
                var fieldValue = $(this).val();
                var middleName = $(this).attr("id");
                var serviceType = $(this).attr("class");
                if (middleName == undefined) {
                    middleName = ""
                }
                var date = new Date();
                if (fieldValue != "" && fieldValue == date.getFullYear() && serviceType == "expirationDate") {
                    var currentMonth = date.getMonth() + 1;
                    var expMonth = $(".monthContent").find(".expirationDate").val();
                    $(".expirationDate").each(function() {
                        var expireMonthYear = $(this).val();
                        if (expireMonthYear < currentMonth && expireMonthYear.length == 2 && expireMonthYear == expMonth) {
                            $("#lname,#DLNumber").parent().css("display", "block");
                            $(this).closest(".home-services-body").addClass("error");
                            required($(".expirationDate:eq(1)"), "Date expired");
                            $(this).parent().find(".mandatory").addClass(".mandatoryMessage");
                            isValid = false;
                        }
                    });
                }
                if (fieldValue == "" && middleName != "mi") {
                    $(this).closest(".home-services-body").addClass("error");
                    required($(this), "required");
                    $(this).parent().find(".mandatory").addClass(".mandatoryMessage");
                    isValid = false;
                }else if($(this).attr("type") != undefined && isValidate && $(this).attr("type") == "radio"){
                	var isChecked = $(this).attr("name");
                	if(isChecked != undefined && $("input[name="+isChecked+"]:checked").length == 0){
                		 $(this).closest(".home-services-body").addClass("error");
                         required($(this).parent().parent().prev(), "required");
                         $(this).parent().find(".mandatory").addClass(".mandatoryMessage");
                         isValidate = false;
                         isValid = false;
                		
                	} 
                }
            }
        });
        $("input[validation*='Numeric']").each(function(){
        	var maxlength = $(this).attr("maxlength");
        	var maxVal = $(this).val();
        	if(maxVal != undefined && maxVal != "" ){
        		maxVal = maxVal.length;
        	}else{
        		maxVal = maxVal;
        	}
        	console.log("maxVal :: "+maxVal);
        	if(maxlength != undefined && maxlength != "" && maxVal != undefined && maxVal != "" && maxVal < maxlength ){
        		$(this).closest(".home-services-body").addClass("error");
        		var textContent = "Please enter "+maxlength+" digits";
                required($(this), $("<span></span>").addClass("invalidMsg").text(textContent));
                console.log("Max length is :: "+maxlength);
                $(this).parent().find(".error_tooltip").css("display","block");
                $(this).parent().find(".mandatory").addClass(".mandatoryMessage");
                isValid = false;
        	}
        	
        })
        if ($(".yearContent").find(".error_tooltip")) {
            $(".yearContent").find(".error_tooltip").css("margin", "7px 0 0");
        }
        var zip = $('input[name="DisabledPreviousZipCode"]').val();
        var cardlength = $("input[valuetarget='consumerFinancialInfo.creditCard.number']").val();
        if (zip != undefined && zip == '') {
            $('.zipCodeErro').remove();
        }
        if (zip != undefined && $('.zipCodeErro').length > 0) {
            isValid = false;
        }
        if ($('.validateCCClass').length > 0) {
            isValid = false;
        }
        if (cardlength != undefined && cardlength == '') {
            $('.validateCCClass').remove();
        }

        //install content empty field validation
        $(".installContent .firstinstall").find("input,select").each(function() {
            if ($(this).val() == "") {
                if (!($(this).attr("type") == "hidden")) {
                    $(this).closest(".home-services-body").addClass("error");
                    required($(this), "required");
                    validatoinArr.push(" " + $(this).parent().parent().find(".dispayLeftContent").text());
                    isValid = false;
                }
            }
        });
        console.log("validateStatus  validateInstallations ::::::::: " + isValid);
    }
    /******************** Strat PAGE_EVENT Validation***************************************************************************/
    $("input[type='checkbox']").each(function() {
        var isChekcd = $(this).is(":checked");
        var currObj = $(this);
        console.log(isChekcd);
        if (!isChekcd) {
            var dataFeildList = dataFieldMatrixMap[currObj.attr("name")];
            if (dataFeildList != undefined) {
                if (dataFeildList["N"] != undefined) {
                    $.each(dataFeildList["N"], function(k, dataFeildObj) {
                        if (dataFeildObj.externalId != undefined && dataFeildObj.externalId == "PAGE_EVENT:DECLINE_AUTHORIZATION") {
                            currObj.closest(".home-services-body").addClass("error");
                            required(currObj, "required");
                            isValid = false;
                        }
                    });
                }
            }
        }
    });
    /******************** End PAGE_EVENT Validation***************************************************************************/
    inputArr = [];
    $(".input_container input,.installTime select").each(function() {
        var installDate = $(this).val();
        //console.log("installDate : "+installDate)
        inputArr.push(installDate)
    });

    if (inputArr[0] != undefined && inputArr[0] == "") {
        var installMsg = $("<span></span>").addClass("installErr").text("Please Select First install date ");
        $(".firstRequest").after(installMsg);
    } else if (inputArr[1] != undefined && inputArr[1] == "") {
        var installMsg = $("<span></span>").addClass("installErr").text("Please Select First install Time");
        $(".firstRequest").after(installMsg);
    }
    if (inputArr[2] != undefined && inputArr[2] == "") {
        var installMsg = $("<span></span>").addClass("installErr").text("Please Select Second install date");
        $(".secondRequest").after(installMsg);
    } else if (inputArr[3] != undefined && inputArr[3] == "") {
        var installMsg = $("<span></span>").addClass("installErr").text("Please Select Second install Time ");
        $(".secondRequest").after(installMsg);
    }
    if (inputArr[0] != undefined && inputArr[0] == "" && inputArr[1] != undefined && inputArr[1] == "" && inputArr[2] != undefined && inputArr[2] == "" && inputArr[3] != undefined && inputArr[3] == "") {
        $(".installErr").text("");
    }
    if ((inputArr[0] != undefined && inputArr[2] != undefined && inputArr[0].length > 0 && inputArr[2].length > 0 && inputArr[0] == inputArr[2]) && (inputArr[1] != undefined && inputArr[3] != undefined && inputArr[1].length > 0 && inputArr[3].length > 0 && inputArr[1] != 'select' && inputArr[3] != 'select' && inputArr[1] == inputArr[3])) {
        $(".installErr").text("");
        $(".validCont").hide();
        var installMsg = $("<span></span>").addClass("installErr").text("First install and second install dates should  be different")
        $(".secondRequest").after(installMsg);
        isValid = false;
    } else {
        console.log("::::::::::::::::::first install and second installations dates should not be different :::::::::::::::::::::::::")
    }
    return isValid;
}
/***********************************************************************************************
 * 
 * ***************** This function is used for submit installation information to back end *****
 * 
 * **********************************************************************************************/
function submitInstallationInfo() {
    var validateStatus = validateInstallations();
    console.log("validateStatus   ::::::::: " + validateStatus);
    if (validateStatus) {
        var formData = $('#installationsAndPayments').serializeInstallationObject();
        //console.log("formData   ::::::::: "+JSON.stringify(formData));
        $("#installationJSON").val(JSON.stringify(formData));
        displayCKOLoader(false);
        if (formData != null) {
            var fd = formData;
            Array.prototype.removeByValue = function(val) {
                for (var i = 0; i < this.length; i++) {
                    if (this[i].name == val) {
                        this.splice(i, 1);
                        break;
                    }
                }
            };
            fd.removeByValue('iData');
            var installation = {
                "installation": JSON.stringify(fd)
            };
            setDataLayer(JSON.stringify(installation));
        }
        $("#installationsAndPayments").submit();
    } else {
        $(".validCont").show();
        setDynamicHeight();
        return false;
    }
    setDynamicHeight();
}

/*********************************************************************************************************
 * 
 * ***************** This function is used for fetch all form field information and convert to json ******
 * 
 * *******************************************************************************************************/
$.fn.serializeInstallationObject = function() {
    var jsonOBJ = [];
    var a = this.serializeArray();
    //console.log("aaaaaa ==== "+JSON.stringify(a));
    $.each(a, function(i, val) {
        if (val.value != "" && val.value != "select") {
            var jsonOBJ1 = {};
            // console.log(i+"aaaaaa ==== "+$('[name='+val.name+']').attr('types'));
            jsonOBJ1['name'] = val.name;
            jsonOBJ1['value'] = val.value;
            var validation = $('[name=' + val.name + ']').attr('validation');
            var valueTarget = $('[name=' + val.name + ']').attr('valueTarget');
            /********************* customize credit card expirationDate ****************************************/
            if ($('[name=' + val.name + ']').attr('valueTarget') == "consumerFinancialInfo.creditCard.expirationDate") {
                jsonOBJ1['value'] = $(".expirationDate:eq(0)").val() + "/" + $(".expirationDate:eq(1)").val();
            }
            /********************* Add masked values ****************************************/
            else if ((validation != undefined) && (validation == "credit" || validation == "debit")) {
                var maskedValues = $('[name=' + val.name + ']').attr("maskedValue")
                if (maskedValues != undefined && maskedValues != "") {
                    jsonOBJ1['value'] = appendDashSymbols(maskedValues);
                }
            } else {
                jsonOBJ1['value'] = val.value;
            }

            jsonOBJ1['displaytype'] = $('[name=' + val.name + ']').attr('displaytype');
            jsonOBJ1['inputype'] = $('[name=' + val.name + ']').attr('type');
            jsonOBJ1['valueTarget'] = $('[name=' + val.name + ']').attr('valueTarget');
            jsonOBJ.push(jsonOBJ1);
        }
    });

    if ($(".newOrTransferCustomer:checked").length > 0) {
        var submitJson = JSON.parse($(".newOrTransferCustomer:checked").attr("submitjson"));
        var selectVal = $(".newOrTransferCustomer:checked").attr("value");
        submitJson["value"] = selectVal;
        jsonOBJ.push(submitJson);

    }
    return jsonOBJ;
};


/**
 * This function is used for remove enable and disable content when we click on checke box 
 */
function removeDisableFieldsContent(contentKeyValue, dataFieldExtId) {
    var disableJson = dataFieldMatrixMap[dataFieldExtId];
    if (contentKeyValue != undefined && dataFieldExtId != undefined &&
        disableJson != undefined) {
        var serviceTypeList = "";
        if (disableJson[contentKeyValue]) {
            serviceTypeList = disableJson[contentKeyValue];
        }
        if (disableJson != undefined && serviceTypeList != undefined) {

            $.each(serviceTypeList, function(k, disableItems) {
                if (dataFieldMatrixMap[disableItems.externalId] != undefined) {
                    removeDisableChildsContent(dataFieldMatrixMap[disableItems.externalId]);
                }
                $("#" + disableItems.externalId).remove();
            });
        }
    }
}

/**
 * This function is used for remove child's  enable and disable content when we click on checke box 
 */
function removeDisableChildsContent(disableItems) {
    if (disableItems != undefined) {
        $.each(disableItems, function(k, disableChildItems) {
            $.each(disableChildItems, function(k, disableChildItems2) {
                $("#" + disableChildItems2.externalId).remove();
                if (dataFieldMatrixMap[disableChildItems2.externalId] != undefined) {
                    removeDisableChildsContent(dataFieldMatrixMap[disableChildItems2.externalId]);
                }

            });
        });
    }
}

/**
 * This function is used for enable and disable content when we click on checke box 
 */
//enable and disable logic
function buildCheckedContent() {
    var id = $(this).attr("id");
    var isChecked = $(this).is(':checked');
    var previousValue;
    if ($(this).attr("class") != undefined && $(this).attr("class") != "newOrTransferCustomer") {
        if (isChecked) {
            var serviceVal = "Y";
            var previous = $(this).data("previous");
        } else {
            var serviceVal = "N";
            var previous = $(this).data("previous");
        }
        $(this).data("previous", serviceVal);
        getEnableContent(serviceVal, previous, $(this));

    } else {
        var serviceVal = $(this).attr("value");
        var previous = $(this).data("previous");
        if (previous == undefined && serviceVal == "New Service") {
            previous = "Transfer Existing Service"
        } else if (previous == undefined && serviceVal == "Transfer Existing Service") {
            previous = "New Service"
        }
        getEnableContent(serviceVal, previous, $(this));
    }
}

//enable and disable logic     
function dropDownChnageBuild() {
    var serviceVal = $(this).val();
    var previous = $(this).data("previous");
    $(this).data("previous", serviceVal)
    getEnableContent(serviceVal, previous, $(this))
}

function buildPreviousCheckedRadioContent() {
    $("input[type='radio']:checked").each(function() {
        var serviceVal = $(this).attr("value");
        var previous = $(this).data("previous");
        if (previous == undefined && serviceVal == "New Service") {
            previous = "Transfer Existing Service"
        } else if (previous == undefined && serviceVal == "Transfer Existing Service") {
            previous = "New Service"
        }
        getEnableContent(serviceVal, previous, $(this));
    });
    appendEvents("select", dropDownChnageBuild, "change");
    appendEvents("input", setDynamicHeight, "click");
    appendEvents("select", setDynamicHeight, "change");
}

function buildPreviousCheckedContent() {
    $("input[type='checkbox']:checked").each(function() {
        var id = $(this).attr("id");
        var isChecked = $(this).is(':checked');
        if (isChecked) {
            var serviceVal = "Y";
            var previous = $(this).data("previous");
            $(".playbackReceiver").show();
        } else {
            $(".playBackRecievers").remove();
            var serviceVal = "N";
            var previous = $(this).data("previous");
        }
        $(this).data("previous", serviceVal);
        getEnableContent(serviceVal, previous, $(this));
    });
    appendEvents("select", dropDownChnageBuild, "change");
    appendEvents("input", setDynamicHeight, "click");
    appendEvents("select", setDynamicHeight, "change");
}
//enable and disable logic
function getEnableContent(serviceVal, previous, thisObj) {
    if (previous != undefined) {
        removeDisableFieldsContent(previous, thisObj.attr("name"));
    }
    var dataFeildList = dataFieldMatrixMap[thisObj.attr("name")];
    // console.log("dataFeildList getEnableContent :: "+JSON.stringify(dataFeildList));
    var enable = false;
    if (dataFeildList != undefined) {
        var dialogueServiceItem;
        if (dataFeildList[serviceVal] != undefined) {
            $.each(dataFeildList[serviceVal], function(k, enableItems) {
                $.each(enableArr, function(l, enableArrObj) {
                    if (enableArrObj.dataFieldExID == enableItems.externalId) {
                        //console.log("enableArrObj =============>>>>>>>>>>  "+enableArrObj.dataFieldExID)
                        dialogueServiceItem = buildEnableHtml(enableArrObj, enable);
                        if ($(".dialogueServiceItem").length > 0) {
                            $(".dialogueServiceItem:last").after(dialogueServiceItem);
                        } else {
                            thisObj.closest(".dispayPaymentMainContent ").after(dialogueServiceItem);
                        }
                        return false;
                    }
                });
            });
            $("input[valuetarget='consumerFinancialInfo.creditCard.number']").attr("maxlength", "16");
            appendEvents('input[valuetarget="consumerFinancialInfo.creditCard.number"]', isNumber, "keypress");
            appendEvents('input[valuetarget="consumerFinancialInfo.creditCard.number"]', validateCreditCardNumber, "blur");
        }
    }
    if ($('input[valuetarget="consumer.previousAddress.dwelling.postalCode"]').length > 0 &&
        $('input[valuetarget="consumer.previousAddress.dwelling.postalCode"]').attr("valuetarget") != undefined &&
        $('input[valuetarget="consumer.previousAddress.dwelling.postalCode"]').attr("valuetarget") != "") {
        appendEvents('input[valuetarget="consumer.previousAddress.dwelling.postalCode"]', zipOnKeyUp, "keyup");
        appendEvents('input[valuetarget="consumer.previousAddress.dwelling.postalCode"]', zipOnKeyDown, "keydown");
        appendEvents('input[valuetarget="consumer.previousAddress.dwelling.postalCode"]', prepopulateCityAndState, "blur");
    } else {
        appendEvents('input[name="DisabledPreviousZipCode"]', zipOnKeyUp, "keyup");
        appendEvents('input[name="DisabledPreviousZipCode"]', zipOnKeyDown, "keyup");
        appendEvents('input[name="DisabledPreviousZipCode"]', prepopulateCityAndState, "blur");
    }

    appendEvents("input[type='checkbox']", buildCheckedContent, "click");
    appendEvents("input[type='radio']", buildCheckedContent, "click");
    appendEvents("select", dropDownChnageBuild, "change");
    appendEvents("input", setDynamicHeight, "click");
    appendEvents("select", setDynamicHeight, "change");
    appendEvents("input[validation='credit']", maskPaymentInformation, "blur");
    appendEvents("input[validation='credit']", populateMaskedValues, "focus");
    appendEvents('input[validation="Phone"]', isNumber, "keypress");
    $('input[validation="Phone"]').attr("maxlength", "12");
    appendEvents('input[validation="Phone"]', prepopulatePhoneDashes, "keypress keydown keyup change blur");
    appendPageEventMandatoryStar();
    $('input[validation="Phone"]').each(prepopulatePhoneDashes);
    $("input[validation='Date']").each(function() {
        var dateField = $(this).attr("valueTarget");
        if (dateField != undefined && dateField != "consumer.dateOfBirth") {
            $(this).datepicker({
                format: 'mm/dd/yyyy',
                autoclose: true
            });
        }
    });

}

function buildPreviousSelectContent() {
    $("select option:selected").each(function() {
        var seletedValue = $(this).val();
        var seletedExtId = $(this).parent().attr("id");
        var previous = $(this).parent().data("previous");
        if (seletedExtId != undefined && seletedValue != undefined) {
            getEnableContent(seletedValue, previous, $("select#" + seletedExtId));
            $(this).parent().data("previous", seletedValue);

        }

    });

}

function zipOnKeyUp() {
    var zip = $(this).val().trim();
    var zipExp = /(^\d{5}$)|(^\d{5}-\d{4}$)/;
    if (!zipExp.test(zip)) {
        if (!(/^[0-9-]*$/.test(zip))) {
            $(this).val("");
        }
    }
    zip = zip.replace("-", "");
    var firstval = zip.substring(0, 5);
    var lastValue = zip.substring(5, 9);
    if ((/^[0-9-]*$/.test(zip))) {
        if (zip.length > 6) {
            $(this).val(firstval + "-" + lastValue);
        }
    }
}

function zipOnKeyDown(e) {
    var charCode = (e.which) ? e.which : e.keyCode;
    if (e.shiftKey && (e.which >= 48 && e.which <= 57)) {
        e.preventDefault();
        return false;
    }
    if ((charCode >= 48 && charCode <= 57) || (charCode >= 96 && charCode <= 105) || charCode == 8 || charCode == 9 || charCode == 46 || charCode == 37 || charCode == 39 || charCode == 13) {
        return true;
    } else {
        return false;
    }
}

/**
 * This function is used for masking sensitive information 
 * Like DOB,Credit card/Debit card and SSN 
 * 
 */
function maskPaymentInformation() {
    var validationType = $(this).attr("validation");
    var value = $(this).val();
    if ((validationType != undefined && validationType != "undefined" && value != "" && value != undefined) && (validationType == "SSN" || validationType == "credit" || validationType == "debit" || validationType == "Date")) {
        var roundVal = value.trim().length;
        var regExp = new RegExp("^.{" + roundVal + "}");
        var astrekStr = "";
        for (var int = 0; int < roundVal; int++) {
            astrekStr = astrekStr + "*";
        }
        if ($(this).attr("maskedValue") == undefined) {
            $(this).attr({
                "maskedValue": value
            });

        } else if ($(this).attr("maskedValue") != undefined) {
            $(this).attr("maskedValue", value);
        }
        $(this).val(value.replace(regExp, astrekStr))
    }
}

function validateCreditCardNumber() {
    $('.validateCCClass').remove();
    var ccMinLength = {
        "ccLength": [{
            "ccType": "MasterCard",
            "length": 16
        }, {
            "ccType": "Visa",
            "length": 16
        }, {
            "ccType": "AmericanExpress",
            "length": 15
        }, {
            "ccType": "Discover",
            "length": 16
        }]
    };
    var creditCardNumberStartAndEnd = {
        "creditCardBound": [{
            "ccTypes": "AmericanExpress1",
            "upperBound": "3499",
            "lowerBound": "3400"
        }, {
            "ccTypes": "AmericanExpress2",
            "upperBound": "3799",
            "lowerBound": "3700"
        }, {
            "ccTypes": "Visa",
            "upperBound": "4999",
            "lowerBound": "4000"
        }, {
            "ccTypes": "MasterCard",
            "upperBound": "5599",
            "lowerBound": "5100"
        }, {
            "ccTypes": "Discover",
            "upperBound": "6011",
            "lowerBound": ""
        }]
    };
    var creditCardList = ["Visa", "MasterCard", "Discover", "AmericanExpress"];

    var creditCardType = $("select[valuetarget='consumerFinancialInfo.cardType'] option:selected").val(); //$(".ccType").val();
    var id = $(this).attr('id');
    var selValue = $("input[valuetarget='consumerFinancialInfo.creditCard.number']").val();
    var bound = selValue.substring(0, 4);
    var ccLength = selValue.length;
    if (creditCardType != null && creditCardType.trim().length > 0) {
        for (var i = 0; i < creditCardList.length; i++) {
            if (creditCardList[i] == creditCardType) {
                var isCreditCardValid = false;
                $(ccMinLength.ccLength).each(function() {
                    if (this.ccType.indexOf(creditCardType) >= 0) {
                        if (this.length == ccLength) {
                            $('#' + id + '_validateCCClass').each(function() {
                                if ($(this).text() != null) {
                                    $(this).remove();
                                }
                            });
                            isCreditCardValid = true;
                        } else {
                            $("#" + id + "_validateCCClass").each(function() {
                                if ($(this).text() != null) {
                                    $(this).remove();
                                }
                            });
                            $('input[id=' + id + ']').after("<span id='" + id + "_validateCCClass' class='validateCCClass error' " +
                                "style='background-color:yellow;'><font color='red'>" + this.ccType.toUpperCase() + " card incorrect length </span>");
                            isCreditCardValid = false;
                        }
                    }
                });

                if (isCreditCardValid) {
                    $(creditCardNumberStartAndEnd.creditCardBound).each(function() {
                        if (this.ccTypes == creditCardType) {
                            var upperBound = this.upperBound;
                            var lowerBound = this.lowerBound;
                            if (bound >= lowerBound && bound <= upperBound) {

                                var data = {
                                    "ccNumber": selValue
                                };
                                $.ajax({
                                    type: 'POST',
                                    data: data,
                                    async: false,
                                    url: $('#contextPath').val() + "/static/updateDialogue/isValidCCNumber",
                                    success: function(data) {
                                        var responseJSON = JSON.parse(data);
                                        if (responseJSON.error != null && responseJSON.error != undefined) {
                                            $("#" + id + '_validateCCClass').each(function() {
                                                if ($(this).text() != null) {
                                                    $(this).remove();
                                                }
                                            });

                                            $('input[id=' + id + ']').after("<span id='" + id + "_validateCCClass' class='validateCCClass error' style='background-color:yellow;' ><font color='red'>" + responseJSON.error + "</font></span>");
                                            return false;
                                        } else {
                                            $('#' + id + '_validateCCClass').each(function() {
                                                if ($(this).text() != null) {
                                                    $(this).remove();
                                                }
                                            });
                                            return true;
                                        }
                                    },
                                    error: function(status) {}
                                });

                            } else {
                                $("#" + id + "_validateCCClass").each(function() {
                                    if ($(this).text() != null) {
                                        $(this).remove();
                                    }
                                });
                                $('input[id=' + id + ']').after("<span id='" + id + "_validateCCClass' class='validateCCClass error' " +
                                    "style='background-color:yellow;'><font color='red'>" + this.ccTypes.toUpperCase() + " card number is not valid</span>");
                                return false;
                            }
                        }
                    });
                } else {
                    return false;
                }
            }
        }
    } else {
        $('input[id=' + id + ']').after("<span id='" + id + "_validateCCClass' class='validateCCClass error' " +
            "style='background-color:yellow;'><font color='red'>select credit card type</span>");
        return false;
    }
}

/**
 * This function is used for automatically populate city and state when enter zip code
 * 
 */
function prepopulateCityAndState() {
    var zip = $('input[name="DisabledPreviousZipCode"]').val();
    var zipLength = zip.length;
    var path = $("#contextPath").val();
    var url = path + "/static/updateDialogue/populateCityAndState";
    var data = {};
    data["zipCode"] = zip;
    if (zipLength == 5 || zipLength == 10) {
        $('input[name="DisabledPreviousZipCode"]').after('<img id="loader" class="validateZipLoader" width="18" height="18" alt="Loading ..."' +
            'src="' + path + '/image/spinner1.gif" style="padding-left: 2px; vertical-align: top; padding-top: 1px;">');
        $('.zipCodeErro').remove();
        try {
            $.ajax({
                type: 'POST',
                url: url,
                data: data,
                async: false,
                success: function(data) {
                    data = JSON.parse(data);
                    if ($.isEmptyObject(data)) {
                        $('input[name="DisabledPreviousZipCode"]').after("<span class='zipCodeErro' style='background-color:yellow;' ><font color='red'>Invalid Zip</font></span>");
                        $('.validateZipLoader').remove();
                        $('input[name="DisabledPreviousCity"]').val("");
                        $('input[name="DisabledPreviousState"]').val("");
                    } else {
                        var city = data.city;
                        var state = data.state;
                        var cityValueTarget = "";
                        var stateValueTarget = "";
                        if (city != undefined && city.trim() != "") {
                            $('input[name="DisabledPreviousCity"]').val(city);
                        }
                        if (state != undefined && state.trim() != "") {
                            $('input[name="DisabledPreviousState"]').val(state);
                        }
                        $('.validateZipLoader').remove();
                    }
                },
                error: function() {}
            });
        } catch (e) {}
    } else if (zipLength >= 1) {
        $('.zipCodeErro').remove();
        $('input[name="DisabledPreviousZipCode"]').after("<span class='zipCodeErro' style='background-color:yellow;' ><font color='red'>Invalid Zip</font></span>");
        $('.validateZipLoader').remove();
        $('input[name="DisabledPreviousCity"]').val("");
        $('input[name="DisabledPreviousState"]').val("");
    }
}

/**
 * This function is used for remove special characters
 * @param stringValue -- value
 * 
 */
function removeSpecialCharacters(stringValue) {
    return stringValue.replace(/[\*?:\^\'\!]/g, '').split(' ').join(' ')
}
/**
 * This function is used for remove asstrick symbols if not mandatory
 * @param dataFeildList -- data field list json
 * 
 */
function isAppendAstreck(dataFeildList) {
    if (($.inArray(dataFeildList.dataFieldExID, removeAsstrickExtList) > 0 || $.inArray(dataFeildList.valueTarget, removeAsstrickExtList) > 0)) {
        return false;
    } else {
        return true;
    }
}

/**
 * This function is used for set maked value's to maskedValue attribute
 * @Attribute Name : maskedValue
 * Like DOB,Credit card/Debit card and SSN 
 * 
 */
function populateMaskedValues() {
    var maskedVal = $(this).attr("maskedValue");
    var ssnValue = $(this).val();
    if (ssnValue != undefined && ssnValue != "" && maskedVal != undefined && maskedVal != "") {
        $(this).val(maskedVal);

    }
};


/**
 * This function is used for build account holder qualifications information  html content 
 * @param dataFeildList -- data field list json (dailogue content)
 * @param isEnable -- diplay enalble fields
 * @return dialogueServiceItem -- div object
 * 
 */
function buildEnableHtml(dataFeildList, isEnable) {

    var dataFieldExIDs = dataFeildList.dataFieldExID;
    var mandatoryStar = $("<span></span>").text("*").attr({
        "class": "mandatory"
    });
    var dataFieldContSpan = $("<span></span>").text("*").attr({
        "class": "dataFieldContSpan"
    });

    var dialogueServiceItem = $("<div></div>").attr({
        "class": "dialogueServiceItem"
    });
    var dataFieldText = $("<div></div>").attr({
        "class": "dataFieldText"
    });
    var dataFieldCont = $("<div></div>").attr({
        "class": "dataFieldCont"
    });
    var dataFieldDisclosure = $("<div></div>").attr({
        "class": "alert alert-info"
    }).text(dataFeildList.dataFieldText);
    var dataFieldinforamtion = $("<span></span>").attr({
        "class": "dataFieldinforamtion"
    }).text("i");

    var staticSubHeading = $("<div></div>").attr({
        "class": "home-services-head"
    }).text("Previous Address");


    var dispalyInput = dataFeildList.dispalyInput.toLowerCase();

    var displayHtmlTag;

    if (dispalyInput != undefined && dispalyInput == "text") {
        if ((dataFeildList.dataFieldText != undefined) && (dataFeildList.valueTarget == "consumerFinancialInfo.creditCard.expirationDate")) {
            dataFieldText.attr({
                "style": "padding-bottom: 35px;"
            });
            if (isAppendAstreck(dataFeildList)) {
                dataFieldCont.append(mandatoryStar.attr({
                    "style": "float: left;"
                }));
            }
            var expDateContent = $("<div></div>").attr({
                "class": "expDateContent"
            }).text(dataFeildList.dataFieldText + " (month/year)");
            dataFieldCont.append(expDateContent);

            var months = $.Constants.MONTHS;
            var myDate = new Date();
            var year = myDate.getFullYear();
            var monthDiv = $("<div></div>").attr({
                "class": "monthContent"
            });
            var yearDiv = $("<div></div>").attr({
                "class": "yearContent"
            });
            var year = new Date().getFullYear();
            displaymonthTag = $("<select></select>").attr({
                "id": dataFeildList.dataFieldExID + "_monthID",
                "name": dataFeildList.dataFieldExID,
                "displayType": dataFeildList.type,
                "valueTarget": dataFeildList.valueTarget,
                "class": "expirationDate"
            });
            option = ($("<option></option>").attr("value", "").text("Select"));
            displaymonthTag.append(option)
            var selectedExpDate;
            if (dataFeildList.enteredValue != undefined) {
                selectedExpDate = dataFeildList.enteredValue.split("/");
            }
            $.each(months, function(k, month) {
                var txtNum = this.monthNum;
                var valName = this.monthNum;
                option = ($("<option></option>"));
                option = option.attr("value", valName).text(txtNum);
                if ((dataFeildList.enteredValue != undefined && dataFeildList.enteredValue != "") && (selectedExpDate[0] == valName)) {
                    option.attr({
                        "selected": "selected"
                    });
                }
                displaymonthTag.append(option)

            });
            monthDiv.append(displaymonthTag);
            dataFieldCont.append(monthDiv);

            displayYearTag = $("<select></select>").attr({
                "id": dataFeildList.dataFieldExID + "_yearID",
                "name": dataFeildList.dataFieldExID,
                "displayType": dataFeildList.type,
                "valueTarget": dataFeildList.valueTarget,
                "class": "expirationDate"
            });
            option = ($("<option></option>").attr("value", "").text("Select"));
            displayYearTag.append(option)
            var year = new Date().getFullYear();
            for (var j = 10; j >= 1; j--) {
                option = ($("<option></option>"));
                option = option.attr("value", year).text(year);
                if ((dataFeildList.enteredValue != undefined && dataFeildList.enteredValue != "") && (selectedExpDate[1] == year)) {
                    option.attr({
                        "selected": "selected"
                    });
                }
                displayYearTag.append(option)
                year++;
            }
            yearDiv.append(displayYearTag);
            dataFieldCont.append(yearDiv);
            dataFieldCont.append(displayHtmlTag);
            dataFieldText.append(dataFieldCont);
        } else {
            if (dataFeildList.enteredValue != undefined || dataFeildList.enteredValue != undefined) {
                displayHtmlTag = "<input id=" + dataFieldExIDs + " validation=" + dataFeildList.validation + " name=" + dataFieldExIDs + " type='text' displayType=" + dataFeildList.type + " valueTarget=" + dataFeildList.valueTarget + " class='dialogueTextInput' value='" + dataFeildList.enteredValue + "'>";
            } else {
                displayHtmlTag = "<input id=" + dataFieldExIDs + " validation=" + dataFeildList.validation + " name=" + dataFieldExIDs + " type='text' displayType=" + dataFeildList.type + " valueTarget=" + dataFeildList.valueTarget + " class='dialogueTextInput'>";
            }
            dataFieldContSpan.text(dataFeildList.dataFieldText);
            if (isAppendAstreck(dataFeildList)) {
                dataFieldCont.append(mandatoryStar);
            }
            dataFieldCont.append(dataFieldContSpan);
            dataFieldCont.append("<br>" + displayHtmlTag);
            dataFieldText.append(dataFieldCont);
        }

    } else if (dispalyInput != undefined && dispalyInput == "checkbox") {

        if (dataFeildList.enteredValue != undefined && dataFeildList.enteredValue != "") {
            displayHtmlTag = $("<input type='checkbox'/>");
            displayHtmlTag.attr({
                "id": dataFieldExIDs,
                "checked": "checked",
                "validation": dataFeildList.validation,
                "name": dataFieldExIDs,
                "displayType": dataFeildList.type,
                "valueTarget": dataFeildList.valueTarget,
                "class": 'checkBoxInput',
                "value": dataFieldExIDs
            })

        } else {
            displayHtmlTag = $("<input type='checkbox'/>");
            displayHtmlTag.attr({
                "id": dataFieldExIDs,
                "validation": dataFeildList.validation,
                "name": dataFieldExIDs,
                "displayType": dataFeildList.type,
                "valueTarget": dataFeildList.valueTarget,
                "class": 'checkBoxInput',
                "value": dataFieldExIDs
            })

        }
        dataFieldContSpan.text(dataFeildList.dataFieldText);
        dataFieldCont.append(dataFieldContSpan);
        dataFieldText.append(dataFieldCont);
        dataFieldText.append(displayHtmlTag);

    } else if (dispalyInput != undefined && dispalyInput == "radio") {
        if (dataFeildList.enteredValue != undefined && dataFeildList.enteredValue != "") {
            displayHtmlTag = "<input type='checkbox' checked='checked' name=" + dataFieldExIDs + "valueTarget=" + dataFeildList.valueTarget + "' displayType=" + dataFeildList.type + " class='radioInput'>";
        } else {
            displayHtmlTag = "<input type='checkbox' name=" + dataFieldExIDs + "valueTarget=" + dataFeildList.valueTarget + "' displayType=" + dataFeildList.type + " class='radioInput'>";
        }
        dataFieldContSpan.text(dataFeildList.dataFieldText)
        if (isAppendAstreck(dataFeildList)) {
            dataFieldCont.append(mandatoryStar);
        }
        dataFieldCont.append(dataFieldContSpan);
        dataFieldText.append(dataFieldCont);
        dataFieldText.append(displayHtmlTag);
    } else if (dispalyInput != undefined && dispalyInput == "dropdown") {
        if (dataFeildList.valueTarget != undefined && dataFeildList.valueTarget == "consumerFinancialInfo.cardType") {
            displayHtmlTag = $("<select></select>").attr({
                "id": dataFeildList.dataFieldExID,
                "class": "dialogueDropDown",
                "name": dataFieldExIDs,
                "displayType": dataFeildList.type,
                "valueTarget": dataFeildList.valueTarget
            });
            var option = $('<option/>');
            option.attr({
                'value': ""
            }).text("Select");
            displayHtmlTag.append(option);
            if (dataFeildList.dataConstraintValueList != undefined) {

                $.each(dataFeildList.dataConstraintValueList, function(l, dataConstraintValue) {
                    option = $('<option/>');
                    option.attr({
                        'value': dataConstraintValue
                    }).text(dataConstraintValue);
                    if (dataFeildList.enteredValue != undefined || dataFeildList.enteredValue != "") {
                        if (dataFeildList.enteredValue == dataConstraintValue) {
                            option.attr("selected", "selected");
                        }
                    }
                    displayHtmlTag.append(option);
                });
            }

            dataFieldContSpan.text(dataFeildList.dataFieldText)
            if (isAppendAstreck(dataFeildList)) {
                dataFieldCont.append(mandatoryStar);
            }
            dataFieldCont.append(dataFieldContSpan);
            dataFieldText.append(dataFieldCont);
            dataFieldText.append("<br>")
            dataFieldText.append(displayHtmlTag);

        } else if (dataFeildList.dataFieldExID != undefined && dataFeildList.featureExID == "NEW_XFER_INSTALL") {
            var displayHtmlTag = $("<div></div>").attr({
                "id": dataFeildList.dataFieldExID,
                "name": dataFieldExIDs,
                "displayType": dataFeildList.type,
                "class": "serviceType",
                "valueTarget": dataFeildList.valueTarget
            });
            var submitJson = {
                'displaytype': dataFeildList.type,
                'name': dataFeildList.dataFieldExID,
                'inputType': "radio"
            };
            if (dataFeildList.dataConstraintValueList != undefined) {

                $.each(dataFeildList.dataConstraintValueList, function(l, dataConstraintValue) {
                    option = $("<input type='radio'/>");
                    option.attr({
                        "class": "newOrTransferCustomer",
                        "id": dataFeildList.dataFieldExID,
                        'value': dataConstraintValue,
                        "name": dataFeildList.dataFieldExID,
                        "tabindex": "0",
                        "submitJson": JSON.stringify(submitJson)
                    }).text(dataConstraintValue);

                    if (dataFeildList.enteredValue != undefined && dataFeildList.enteredValue != "") {
                        if (dataFeildList.enteredValue == dataConstraintValue) {
                            option.attr({
                                "checked": "checked"
                            })
                            isBackButtonInfo = false;
                        }
                    }
                    displayHtmlTag.append(option);
                    displayHtmlTag.append(dataConstraintValue);
                    displayHtmlTag.append("<br/>");
                });
            }

            dataFieldContSpan.text(dataFeildList.dataFieldText);
            dataFieldCont.attr("style", "width:auto !important");
            if (isAppendAstreck(dataFeildList)) {
                dataFieldCont.append("<span style='color:red;'>*</span>");
                dataFieldCont.append("&nbsp;");
            }
            dataFieldCont.append(dataFieldContSpan);
            dataFieldText.append(dataFieldCont);
            dataFieldText.append("<br>")
            dataFieldText.append(displayHtmlTag);
        } else {
            displayHtmlTag = $("<select></select>").attr({
                "id": dataFeildList.dataFieldExID,
                "class": "dialogueDropDown",
                "name": dataFieldExIDs,
                "displayType": dataFeildList.type,
                "valueTarget": dataFeildList.valueTarget
            });
            var option = $('<option/>');
            option.attr({
                'value': ""
            }).text("Select");
            displayHtmlTag.append(option);
            if (dataFeildList.dataConstraintValueList != undefined) {

                $.each(dataFeildList.dataConstraintValueList, function(l, dataConstraintValue) {
                    option = $('<option/>');
                    option.attr({
                        'value': dataConstraintValue
                    }).text(dataConstraintValue);
                    if (dataFeildList.enteredValue != undefined || dataFeildList.enteredValue != "") {
                        if (dataFeildList.enteredValue == dataConstraintValue) {
                            option.attr("selected", "selected");
                        }
                    }
                    displayHtmlTag.append(option);
                });
            }
            dataFieldContSpan.text(dataFeildList.dataFieldText)
            if (isAppendAstreck(dataFeildList)) {
                dataFieldCont.append(mandatoryStar);
            }
            dataFieldCont.append(dataFieldContSpan);
            dataFieldText.append(dataFieldCont);
            dataFieldText.append(displayHtmlTag);
        }
    }

    if ((dispalyInput != undefined && dispalyInput == "disclosure") || (dispalyInput != undefined && dispalyInput == "inforamtion") || (dispalyInput != undefined && dispalyInput == "inforamtional")) {

        if (dispalyInput == "inforamtion" || dispalyInput == "inforamtional") {
            if (dataFeildList.enteredValue != undefined || dataFeildList.enteredValue != "") {
                dialogueServiceItem.append(dataFieldDisclosure.text(dataFeildList.dataFieldText));
            } else {
                dataFieldDisclosure.append(dataFieldinforamtion);
                dialogueServiceItem.append(dataFieldDisclosure);
            }
        } else {
            if (dataFeildList.enteredValue != undefined || dataFeildList.enteredValue != "") {
                dialogueServiceItem.append(dataFieldDisclosure.text(dataFeildList.dataFieldText));
            } else {
                dialogueServiceItem.append(dataFieldDisclosure.text(dataFeildList.dataFieldText));
            }
        }
    } else {
        dialogueServiceItem.append(dataFieldText);
    }
    if (isEnable == false) {
        dialogueServiceItem.attr({
            "id": dataFeildList.dataFieldExID
        });
    }

    return dialogueServiceItem;
}

/**
 * This function is used for populate previous inforamtion(click back button) dashe(s) between ssn and phone number's
 * @param value -- current event
 */
function appendDashSymbols(previousPhoneOrSsnNumber) {

    if (previousPhoneOrSsnNumber.length == 9) {
        previousPhoneOrSsnNumber = previousPhoneOrSsnNumber.replace(/(\d{3})\-?(\d{2})\-?(\d{4})/, '$1-$2-$3');
    } else if (previousPhoneOrSsnNumber.length == 10) {
        previousPhoneOrSsnNumber = previousPhoneOrSsnNumber.replace(/(\d{3})\-?(\d{3})\-?(\d{4})/, '$1-$2-$3');
    }
    return previousPhoneOrSsnNumber;
}

function appendPageEventMandatoryStar(){
	if($("input[type='checkbox']").length > 0){
		   $("input[type='checkbox']").each(function() {
		        var currObj = $(this);
		            var dataFeildList = dataFieldMatrixMap[currObj.attr("name")];
		            if (dataFeildList != undefined) {
		                if (dataFeildList["N"] != undefined) {
		                    $.each(dataFeildList["N"], function(k, dataFeildObj) {
		                        if (dataFeildObj.externalId != undefined && dataFeildObj.externalId == "PAGE_EVENT:DECLINE_AUTHORIZATION") {
		                        	if(currObj.closest(".dataFieldText").find(".pageEventMandatory").length == 0){
		                        		currObj.closest(".dataFieldText").find(".dataFieldContSpan").before($("<span></span>").addClass("pageEventMandatory").text("*"))
		                        	}
		                        }
		                    });
		                }
		            }
		    });
	}
 
}


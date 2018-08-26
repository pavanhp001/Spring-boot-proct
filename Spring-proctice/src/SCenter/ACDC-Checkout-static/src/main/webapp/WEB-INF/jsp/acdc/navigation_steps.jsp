
<%-- <script src="<c:url value='/script/acdc/navigation.js'/>"></script> --%>
<style>
.CKO-wizard {
	margin-top: 40px;
}

.CKO-wizard {
	border-bottom: solid 1px #e0e0e0;
	padding: 0 0 10px 0;
}

.CKO-wizard>.CKO-wizard-step {
	padding: 0;
	position: relative;
}

.CKO-wizard>.CKO-wizard-step+.CKO-wizard-step {
	
}

.CKO-wizard>.CKO-wizard-step .CKO-wizard-stepnum {
	color: #595959;
	font-size: 16px;
	margin-bottom: 5px;
}

.CKO-wizard>.CKO-wizard-step .CKO-wizard-info {
	color: #999;
	font-size: 14px;
}

.CKO-wizard>.CKO-wizard-step>.CKO-wizard-round {
    -background: #81bd41 none repeat scroll 0 0;
    -background: rgba(0, 0, 0, 0) linear-gradient(to bottom, #f8ffe8 3%, #e3f5ab 26%, #b7df2d 100%) repeat scroll 0 0;
    border-radius: 50%;
    display: block;
    height: 40px;
    left: 50%;
    margin-left: -15px;
    margin-top: -15px;
    position: absolute;
    top: 40px;
    width: 40px;
}

.CKO-wizard>.CKO-wizard-step>.CKO-wizard-round:after {
	content: ' ';
	width: 14px;
	height: 14px;
	-background: gold none repeat scroll 0 0;
	border-radius: 50px;
	position: absolute;
	top: 8px;
	left: 8px;
}

.CKO-wizard>.CKO-wizard-step>.progress {
	position: relative;
	border-radius: 0px;
	height: 6px;
	box-shadow: none;
	margin: 20px 0;
}

.CKO-wizard>.CKO-wizard-step>.progress>.progress-bar {
	width: 0px;
	box-shadow: none;
	-background: #81bd41 none repeat scroll 0 0;
	-background: rgba(0, 0, 0, 0) linear-gradient(to bottom, #f8ffe8 3%, #e3f5ab 26%, #b7df2d 100%) repeat scroll 0 0
}

.CKO-wizard>.CKO-wizard-step.complete>.progress>.progress-bar
	{
	width: 100%;
}

.CKO-wizard>.CKO-wizard-step.active>.progress>.progress-bar {
	width: 50%;
}

.CKO-wizard>.CKO-wizard-step:first-child.active>.progress>.progress-bar
	{
	width: 0%;
}

.CKO-wizard>.CKO-wizard-step:last-child.active>.progress>.progress-bar
	{
	width: 100%;
}

.CKO-wizard>.CKO-wizard-step.disabled>.CKO-wizard-round {
	background: #f5f5f5;
}

.CKO-wizard>.CKO-wizard-step.disabled>.CKO-wizard-round:after
	{
	opacity: 0;
}

.CKO-wizard>.CKO-wizard-step:first-child>.progress {
	left: 50%;
	width: 50%;
}

.CKO-wizard>.CKO-wizard-step:last-child>.progress {
	width: 50%;
}

.CKO-wizard>.CKO-wizard-step.disabled a.CKO-wizard-round
	{
	pointer-events: none;
}
.label{
color:#000;
}
.CKONumbers{
    -color: #999;
    font-size: 17px;
    font-weight: bold;
    margin-left: 14px;
    position: absolute;
    top: 7px;
}
.disableCKONumbers{
     -color: #999;
    font-size: 17px;
    font-weight: bold;
    margin-left: 14px;
    position: absolute;
    top: 7px;
}
</style>
<script type="text/javascript">
var channelCss = "${channelCss}";
</script>


		
      <!--  <c:choose>
           <c:when  test="${showBackButton != false && !ishybris && !frontierAsIs}">-->
            <div class="row CKO-wizard" style="border-bottom:0;">
                
                <div class="col-xs-3 col-md-3 col-sm-3 nav_step_1 CKO-wizard-step disabled">
                  <div class="text-center CKO-wizard-stepnum">Step</div>
                  <div class="progress"><div class="progress-bar"></div></div>
                  <a href="#" class="CKO-wizard-round"><span class="ckoNumOne disableCKONumbers">1</span></a>
                  <div class="CKO-wizard-info text-center">Product Customization</div>
                </div>
                
                <div class="col-xs-3 col-md-3 col-sm-3 nav_step_2 CKO-wizard-step disabled">
                  <div class="text-center CKO-wizard-stepnum">Step</div>
                  <div class="progress"><div class="progress-bar"></div></div>
                  <a href="#" class="CKO-wizard-round"><span class="ckoNumTwo disableCKONumbers">2</span></a>
                  <div class="CKO-wizard-info text-center">Customer Qualification</div>
                </div>
                
                 <div class="col-xs-3 col-md-3 col-sm-3  nav_step_3 CKO-wizard-step disabled">
                  <div class="text-center CKO-wizard-stepnum">Step</div>
                  <div class="progress"><div class="progress-bar"></div></div>
                  <a href="#" class="CKO-wizard-round"><span class="ckoNumThree disableCKONumbers">3</span></a>
                  <div class="CKO-wizard-info text-center">Authorization</div>
                </div>
                
               
            </div>
                    
                   <!-- </c:when>
				<c:otherwise>
                    
                    <div class="row CKO-wizard" style="border-bottom:0;">
                
                <div class="col-xs-4 nav_step_3 CKO-wizard-step disabled">
                  <div class="text-center CKO-wizard-stepnum">Step 1</div>
                  <div class="progress"><div class="progress-bar"></div></div>
                  <a href="#" class="CKO-wizard-round"></a>
                  <div class="CKO-wizard-info text-center">Product Customization</div>
                </div>
                
                <div class="col-xs-4 nav_step_2 CKO-wizard-step disabled">
                  <div class="text-center CKO-wizard-stepnum">Step 2</div>
                  <div class="progress"><div class="progress-bar"></div></div>
                  <a href="#" class="CKO-wizard-round"></a>
                  <div class="CKO-wizard-info text-center">Customer Qualification</div>
                </div>
                        
            </div>
                    
                    </c:otherwise>
			</c:choose>-->

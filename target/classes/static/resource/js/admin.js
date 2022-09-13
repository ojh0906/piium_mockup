//서버 주소 변수
//var ServerUrl = "http://13.125.114.252";
//   var ServerUrl = "http://localhost:8080";
// var ServerUrl = "http://13.125.114.252:8080";
// var ServerUrl = "http://3.39.240.88:8080";
var ServerUrl = "https://api.piium.co.kr";




// 파라미터 받기
function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}

// 회원 리스트
function memberList(){
    var data = {};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/admin/getMemberList",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);
            var memberHtml = '';
            var total = 0;
            $(result.BODY).each(function(idx, member) {

                total += 1;

                var role = '';
                var regdate = `${member.regDate}`;
                switch(`${member.role}`){
                    case 'ROLE_USER':
                        role = '일반 회원';
                        break;
                    case 'ROLE_STORE':
                        role = '가게 회원';
                        break;
                }
                regdate = regdate.substr(0,10);

                if(`${member.role}` != 'ROLE_ADMIN'){
                    memberHtml += `
                                <tr class="list-item" onClick="location.href='/admin/memberDetail?mid=${member.member}&cmd=2'">
                                    <td onclick="event.cancelBubble=true">
                                       <input type="checkbox" class="delChk" name="chk[]" value="${member.member}"/>
                                    </td>
                                    <td>
                                        ${member.member}
                                    </td>
                                    <td>
                                        `+role+`
                                    </td>
                                    <td>
                                        ${member.id}
                                    </td>
                                    <td>
                                        ${member.name}
                                    </td>
                                    <td>
                                       `+regdate+`
                                    </td>
                                </tr>
                                `;
                }
            });
            $("#member-list").html(memberHtml);

            $('.totalNum').text(total);
        },
        error: function(result) {
            console.log(result);
        }
    });
}
// 회원 리스트 검색
function memberSearchList(role, keyword){
    var data = {
                "role" : role,
                "keyword" : keyword
                };
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/admin/getMemberSearchList",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);
            var memberHtml = '';
            var total = 0;
            $(result.BODY).each(function(idx, member) {

                total += 1;

                var role = '';
                var regdate = `${member.regDate}`;
                switch(`${member.role}`){
                    case 'ROLE_USER':
                        role = '일반 회원';
                        break;
                    case 'ROLE_STORE':
                        role = '가게 회원';
                        break;
                }
                regdate = regdate.substr(0,10);

                if(`${member.role}` != 'ROLE_ADMIN'){
                    memberHtml += `
                                <tr class="list-item" onClick="location.href='/admin/memberDetail?mid=${member.member}&cmd=2'">
                                    <td onclick="event.cancelBubble=true">
                                       <input type="checkbox" class="delChk" name="chk[]" value="${member.member}"/>
                                    </td>
                                    <td>
                                        ${member.member}
                                    </td>
                                    <td>
                                        `+role+`
                                    </td>
                                    <td>
                                        ${member.id}
                                    </td>
                                    <td>
                                        ${member.name}
                                    </td>
                                    <td>
                                       `+regdate+`
                                    </td>
                                </tr>
                                `;
                }
            });
            $("#member-list").html(memberHtml);
            $('.totalNum').text(total);
        },
        error: function(result) {
            console.log(result);
        }
    });
}
// 회원 상세정보 불러오기
function getMember(mid){
    var data = {"member":mid};
        $.ajax({
            type: "POST",
            headers: {
                "Content-Type": "application/json",
                "X-HTTP-Method-Override": "POST"
            },
            url: ServerUrl+"/admin/getMember",
            data: JSON.stringify(data),
            success: function(result) {
                console.log(result);

                if(result.BODY.role == 'ROLE_USER'){
                    $('.rollStore').hide();
                }else if(result.BODY.role == 'ROLE_STORE'){
                    $('.rollUser').hide();
                }

                var regdate = result.BODY.regDate;
                var chnDate = result.BODY.chnDate;
                regdate = regdate.substr(0,10);
                chnDate = chnDate.substr(0,10);

                $('.market-btn').prop('href', '/admin/memberDetail?cmd=2&sid='+result.BODY.id);
                $('input[name=id]').val(result.BODY.id);
                $('input[name=name]').val(result.BODY.name);
                $('input[name=phone]').val(result.BODY.phone);
                $('input[name=email]').val(result.BODY.email);
                $('input[name=address]').val(result.BODY.address);
                $('input[name=regDate]').val(regdate);
                $('input[name=chnDate]').val(chnDate);

            },
            error: function(result) {
                console.log(result);
            }
        });
}

// 시장 리스트
function marketList(){
    var data = {};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/admin/getMarketList",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);
            var marketHtml = '';
            var total = 0;
            $(result.BODY).each(function(idx, market) {

                total += 1;

                var role = '';
                var regdate = `${market.regDate}`;

                regdate = regdate.substr(0,10);

                marketHtml += `
                            <tr class="list-item" onClick="location.href='/admin/marketDetail?mid=${market.market}&cmd=2'">
                                <td onclick="event.cancelBubble=true">
                                   <input type="checkbox" class="delChk" name="chk[]" value="${market.market}"/>
                                </td>
                                <td>
                                    ${market.market}
                                </td>
                                <td>
                                    `+regdate+`
                                </td>
                                <td>
                                     ${market.name}
                                </td>
                                <td>
                                     ${market.phone}
                                </td>
                                <td>
                                     ${market.email}
                                </td>
                                <td>
                                     ${market.address}
                                </td>
                            </tr>
                            `;

            });
            $("#market-list").html(marketHtml);
            $('.totalNum').text(total);
        },
        error: function(result) {
            console.log(result);
        }
    });
}
// 시장 상세정보 불러오기
function getMarket(mid){
    var data = {"market":mid};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/admin/getMarket",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);

            $('input[name=name]').val(result.BODY.name);
            $('input[name=phone]').val(result.BODY.phone);
            $('input[name=manager]').val(result.BODY.manager);
            $('input[name=email]').val(result.BODY.email);
            $('input[name=holiday]').val(result.BODY.holiday);
            $('input[name=homepage]').val(result.BODY.homepage);
            $('input[name=open]').val(result.BODY.open);
            $('input[name=park]').val(result.BODY.park);
            $('input[name=address]').val(result.BODY.address);
            $('input[name=lat]').val(result.BODY.lat);
            $('input[name=lon]').val(result.BODY.lon);
            $('textarea').val(result.BODY.detail);
            $(result.BODY.categories).each(function(idx, category) {

                $('.category'+`${category.category}`).prop("checked", true);

            });

            var imgHtml = '';
            if(result.BODY.files.length != 0){
                $(result.BODY.files).each(function(idx, file) {
                    var src = ServerUrl+'/file/download?fileName='+`${file.path}`;
                    var mrfile = `${file.mrfile}`;
                    imgHtml += '                                <div class="img-upload-item">\n' +
                        '                                    <div class="controll-wrap">\n' +
                        '                                        <div class="img-preview">\n' +
                        '                                            <img class="preview" src="'+src+'" style="display: block;"/>\n' +
                        '                                        </div>\n' +
                        '                                        <div class="delete-wrap">\n' +
                        '                                            <input type="hidden" name="mrfile" value="'+mrfile+'"/>\n' +
                        '                                            <span class="img-del-btn">삭제</span>\n'+
                        '                                        </div>\n' +
                        '                                    </div>\n' +
                        '                                </div>';
                });
            } else {
                imgHtml += '                                <div class="img-upload-item">\n' +
                    '                                    <input id="img1" class="img-file" type="file" accept="image/*" name="pimg" />\n' +
                    '                                    <div class="controll-wrap">\n' +
                    '                                        <div class="img-preview">\n' +
                    '                                            <p class="no-img">등록된 이미지 없음</p>\n' +
                    '                                            <img class="preview" src="" />\n' +
                    '                                        </div>\n' +
                    '                                        <div class="delete-wrap">\n' +
                    '                                        </div>\n' +
                    '                                    </div>\n' +
                    '                                </div>';
            }

            $("#imgFile").html(imgHtml);
        },
        error: function(result) {
            console.log(result);
        }
    });
}


// 가게 리스트
function storeList(){
    var data = {};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/admin/getStoreList",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);
            var storeHtml = '';
            var total = 0;
            $(result.BODY).each(function(idx, store) {

                total += 1;

                var regdate = `${store.regDate}`;
                regdate = regdate.substr(0,10);

                var recYn = `${store.recYn}`;
                var popYn = `${store.popYn}`;
                var recSelect = '';
                var popSelect = '';
                var recPopSelect = '';
                var noSelect = '';

                if(recYn == 'Y' && popYn == 'Y'){
                    recPopSelect = 'selected';
                }
                else if(recYn == 'Y'){
                    recSelect = 'selected';
                }
                else if(popYn == 'Y'){
                    popSelect = 'selected';
                }
                else if(recYn == 'N' && popYn == 'N'){
                    noSelect = 'selected';
                }


                storeHtml += `
                                <tr class="list-item" onClick="location.href='storeDetail?sid=${store.store}&cmd=2'">
                                    <td onclick="event.cancelBubble=true">
                                        <input type="checkbox" class="delChk" name="chk[]" value="${store.store}"/>
                                    </td>
                                    <td>
                                        ${store.store}
                                    </td>
                                    <td>
                                        ${store.mname}
                                    </td>
                                    <td>
                                    </td>
                                    <td>
                                    ${store.sname}
                                    </td>
                                    <td>
                                       `+regdate+`
                                    </td>
                                    <td onclick="event.cancelBubble=true">
                                        <input type="hidden" class="storeKey" value="${store.store}"/>
                                        <select id="storeSetting">
                                            <option value="NoRecPopStore" `+noSelect+`></option>
                                            <option value="RecStore" `+recSelect+`>추천</option>
                                            <option value="PopStore" `+popSelect+`>인기</option>
                                            <option value="RecPopStore" `+recPopSelect+`>추천/인기</option>
                                        </select>
                                    </td>
                                </tr>
                                `;
            });
            $("#storeList").html(storeHtml);
            $('.totalNum').text(total);
        },
        error: function(result) {
            console.log(result);
        }
    });
}
function marketSearchList(searchType, keyword){
    var data = {
                "searchType":searchType,
                "keyword":keyword
                };
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/admin/getStoreSearchList",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);
            var storeHtml = '';
            var total = 0;
            $(result.BODY).each(function(idx, store) {

                total += 1;

                var regdate = `${store.regDate}`;
                regdate = regdate.substr(0,10);


                storeHtml += `
                                <tr class="list-item" onClick="location.href='/admin/marketDetail?sid=${store.store}&cmd=2'">
                                    <td onclick="event.cancelBubble=true">
                                        <input type="checkbox" class="delChk" name="chk[]" value="${store.store}"/>
                                    </td>
                                    <td>
                                        ${store.store}
                                    </td>
                                    <td>
                                        ${store.mname}
                                    </td>
                                    <td>
                                    </td>
                                    <td>
                                    ${store.sname}
                                    </td>
                                    <td>
                                       `+regdate+`
                                    </td>
                                    <td>
                                        <form>
                                            <input type="hidden" class="storeSettingValue" name="recYn" value="N"/>
                                            <input type="hidden" class="storeSettingValue" name="popYn" value="N"/>
                                            <input type="hidden" name="store" value="${store.store}"/>
                                            <select id="storeSetting">
                                                <option value=""></option>
                                                <option value="recYn">추천</option>
                                                <option value="popYn">인기</option>
                                            </select>
                                        </form>
                                    </td>
                                </tr>
                                `;
            });
            $("#storeList").html(storeHtml);
            $('.totalNum').text(total);
        },
        error: function(result) {
            console.log(result);
        }
    });
}
function storeCategoryList(mid){
    var data = {"market":mid};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/admin/getMarket",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);

            $(result.BODY.categories).each(function(idx, category) {


            });

        },
        error: function(result) {
            console.log(result);
        }
    });

}
// 가게 카테고리 불러오기
function marketCategoryList(mid, category){
    var data = {"market":mid};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/admin/getMarket",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);
            var categoryhtml = '';
            $(result.BODY.categories).each(function(idx, category) {

                categoryhtml += `
                                <option value="${category.category}">${category.name}</option>
                            `;
            });
            $("#category").html(categoryhtml);

            $('#category').val(category).prop("selected", true);


        },
        error: function(result) {
            console.log(result);
        }
    });
}
// 가게 상세정보 불러오기
function getStore(sid){
    var data = {"store":sid};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/admin/getStore",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);

            var regdate = result.BODY.regDate;
            var chnDate = result.BODY.chnDate;
            regdate = regdate.substr(0,10);
            chnDate = chnDate.substr(0,10);

            $('input[name=sname]').val(result.BODY.sname);
            $('input[name=mname]').val(result.BODY.mname);



            $(result.BODY.categories).each(function(idx, category) {
                marketCategoryList(result.BODY.market, `${category.category}`);
            });
            $(result.BODY.workdays).each(function(idx, workday) {

                $('.workdays'+`${workday.type}`).prop("checked", true);
            });

            $('input[name=sthm]').val(result.BODY.sthm);
            $('input[name=ndhm]').val(result.BODY.ndhm);
            $('input[name=holidayInfo]').val(result.BODY.holidayInfo);
            $('input[name=sphone]').val(result.BODY.sphone);
            $('input[name=kakao]').val(result.BODY.kakao);
            $('input[name=address]').val(result.BODY.address);

            $('.sinfo').val(result.BODY.sinfo);

        },
        error: function(result) {
            console.log(result);
        }
    });
}
// 가게 상세정보(관리자) 카테고리 리스트
function getCategoryList(){
    var data = {};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/admin/getCategoryList",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);
            var categoryHtml = '';
            var first = "";

            $(result.BODY).each(function(idx, category) {

                if(idx == 0){
                    first = "active";
                }
                else{
                    first = "";
                }

                categoryHtml += `
                                <label>
                                    <input type="checkbox" name="categories" class="category${category.category}" value="${category.category}"/>
                                    ${category.name}
                                </label>
                                `;
                $(".category-wrap").html(categoryHtml);
            });

        },
        error: function(result) {
            console.log(result);
        }
    });
}
// // 가게 이름 가져오기
// function getMarketName(mid){
//
//
//     var data = {
//                     "market" : mid
//                 };
//     $.ajax({
//         type: "POST",
//         headers: {
//             "Content-Type": "application/json",
//             "X-HTTP-Method-Override": "POST"
//         },
//         url: ServerUrl+"/admin/getMarket",
//         data: JSON.stringify(data),
//         success: function(result) {
//             console.log(result);
//                 marketName = result.BODY.name;
//
//         },
//         error: function(result) {
//             console.log(result);
//         }
//     });
// }
// 이벤트 리스트
function eventList(){
    var data = {};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/admin/getEventList",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);
            var eventHtml = '';
            var total = 0;
            $(result.BODY).each(function(idx, event) {
                var marketName = '';
                total += 1;

                var regdate = `${event.regDate}`;
                regdate = regdate.substr(0,10);



                eventHtml += `
                                <tr class="list-item" onClick="location.href='/admin/eventDetail?eid=${event.event}&cmd=2'">
                                    <td onclick="event.cancelBubble=true">
                                        <input type="checkbox" class="delChk" name="chk[]" value="${event.event}"/>
                                    </td>
                                    <td>
                                        `+regdate+`
                                    </td>
                                    <td>
                                        ${event.market}
                                    </td>
                                    <td>
                                        ${event.title}
                                    </td>
                                    <td>
                                        ${event.tot}명 <a href="/admin/eventParticipants?eid=${event.event}" class="event-morm-btn" href="">보기</a>
                                    </td>
                                </tr>
                                `;
            });
            $("#eventList").html(eventHtml);
            $('.totalNum').text(total);
        },
        error: function(result) {
            console.log(result);
        }
    });
}
// 이벤트 리스트
function getMemberListInEvent(eid){
    var data = {"event":eid};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/admin/getMemberListInEvent",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);
            var eventHtml = '';
            var total = 0;
            $(result.BODY).each(function(idx, event) {
                var marketName = '';
                total += 1;

                var regdate = `${event.regDate}`;
                regdate = regdate.substr(0,10);



                eventHtml += `
                                <tr class="list-item">
                                    <td onclick="event.cancelBubble=true">
                                        <input type="checkbox" class="delChk" name="chk[]" value="${event.member}"/>
                                    </td>
                                    <td>
                                       `+regdate+`
                                    </td>
                                    <td>
                                        ${event.name}
                                    </td>
                                    <td>
                                        ${event.phone}
                                    </td>
                                    <td>
                                       ${event.email}
                                    </td>
                                    <td>
                                       ${event.address}
                                    </td>
                                </tr>
                                `;
            });
            $("#eventList").html(eventHtml);
            $('.totalNum').text(total);
        },
        error: function(result) {
            console.log(result);
        }
    });
}
// 이벤트 상세정보 불러오기
function getEvent(eid){
    var data = {"event":eid};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/admin/getEvent",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);
            $("#marketList").val(result.BODY.market).prop("selected", true);


            $('input[name=bgndt]').val(result.BODY.bgndt);
            $('input[name=nddt]').val(result.BODY.nddt);

            $('input[name=title]').val(result.BODY.title);
            $('.contents').text(result.BODY.contents);

            var imgHtml = '';
            if(result.BODY.files.length != 0){
                var src = ServerUrl+'/file/download?fileName='+result.BODY.files[0].path;
                imgHtml += '                                <div class="img-upload-item">\n' +
                    '                                    <input id="img1" class="img-file" type="file" accept="image/*" name="pimg" />\n' +
                    '                                    <div class="controll-wrap">\n' +
                    '                                        <div class="img-preview">\n' +
                    '                                            <img class="preview" src="'+src+'" style="display: block;"/>\n' +
                    '                                        </div>\n' +
                    '                                        <div class="delete-wrap">\n' +
                    '                                        </div>\n' +
                    '                                    </div>\n' +
                    '                                </div>';
            } else {
                imgHtml += '                                <div class="img-upload-item">\n' +
                    '                                    <input id="img1" class="img-file" type="file" accept="image/*" name="pimg" />\n' +
                    '                                    <div class="controll-wrap">\n' +
                    '                                        <div class="img-preview">\n' +
                    '                                            <p class="no-img">등록된 이미지 없음</p>\n' +
                    '                                            <img class="preview" src="" />\n' +
                    '                                        </div>\n' +
                    '                                        <div class="delete-wrap">\n' +
                    '                                        </div>\n' +
                    '                                    </div>\n' +
                    '                                </div>';
            }

            $("#imgFile").html(imgHtml);
        },
        error: function(result) {
            console.log(result);
        }
    });
}
//이벤트 가게 리스트 불러오기
function getEventMarketList() {
    var data = {};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/admin/getMarketList",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);

            var marketHtml = '';
            $(result.BODY).each(function(idx, market) {
                marketHtml += `
                              <option value="${market.market}">${market.name}</option>
                                `;
                $("#marketList").html(marketHtml);


            });

        },
        error: function(result) {
            console.log(result);
        }
    });

}

// 공지사항 리스트
function noticeList(){
    var data = {};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/admin/getNoticeList",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);
            var noticeHtml = '';
            var total = 0;
            $(result.BODY).each(function(idx, notice) {

                total += 1;

                var regdate = `${notice.regDate}`;
                regdate = regdate.substr(0,10);


                noticeHtml += `
                                <tr class="list-item" onClick="location.href='/admin/noticeDetail?nid=${notice.notice}'">
                                    <td onclick="event.cancelBubble=true">
                                        <input type="checkbox" class="delChk" name="chk[]" value="${notice.notice}"/>
                                    </td>
                                    <td>
                                        ${notice.notice}
                                    </td>
                                    <td>
                                        ${notice.title}
                                    </td>
                                    <td>
                                        `+regdate+`
                                    </td>
                                </tr>
                                `;
            });
            $("#noticeList").html(noticeHtml);
            $('.totalNum').text(total);
        },
        error: function(result) {
            console.log(result);
        }
    });
}
// 공지사항 상세정보 불러오기
function getNotice(nid){
    var data = {"notice":nid};
        $.ajax({
            type: "POST",
            headers: {
                "Content-Type": "application/json",
                "X-HTTP-Method-Override": "POST"
            },
            url: ServerUrl+"/admin/getNotice",
            data: JSON.stringify(data),
            success: function(result) {
                console.log(result);

                var regdate = result.BODY.regDate;
                regdate = regdate.substr(0,10);

                $('input[name=title]').val(result.BODY.title);
                $('input[name=contents]').val(result.BODY.contents);

                $('.title').text(result.BODY.title);
                $('.regdate').text(regdate);
                $('.contents').text(result.BODY.contents);
                $('.modify').prop('href', '/admin/noticeEdit?nid='+result.BODY.notice+'&cmd=2');

            },
            error: function(result) {
                console.log(result);
            }
        });
}
// 카테고리 리스트
function categoryList(){
    var data = {};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/admin/getCategoryList",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);
            var categoryHtml = '';
            var total = 0;
            $(result.BODY).each(function(idx, category) {

                total += 1;

                var regdate = `${category.regDate}`;
                regdate = regdate.substr(0,10);


                categoryHtml += `
                                <tr class="list-item" onClick="location.href='/admin/categoryDetail?cid=${category.category}&cmd=2'">
                                    <td onclick="event.cancelBubble=true">
                                        <input type="checkbox" class="delChk" name="chk[]" value="${category.category}"/>
                                    </td>
                                    <td>
                                        ${category.category}
                                    </td>
                                    <td>
                                        ${category.name}
                                    </td>
                                    <td>
                                        `+regdate+`
                                    </td>
                                </tr>

                                `;
            });
            $("#categoryList").html(categoryHtml);
            $('.totalNum').text(total);
        },
        error: function(result) {
            console.log(result);
        }
    });
}
// 카테고리 상세정보 불러오기
function getCategory(cid){
    var data = {"category":cid};
        $.ajax({
            type: "POST",
            headers: {
                "Content-Type": "application/json",
                "X-HTTP-Method-Override": "POST"
            },
            url: ServerUrl+"/admin/getCategory",
            data: JSON.stringify(data),
            success: function(result) {
                console.log(result);

                $('input[name=name]').val(result.BODY.name);
                $('input[name=category]').val(result.BODY.category);

            },
            error: function(result) {
                console.log(result);
            }
        });
}

// 로그아웃
function fnLogout(){
    if(confirm('로그아웃 하시겠습니까?')){
        window.location.href = '/logout';
    }
}
function banneerList(type){
    // 가게정보
    var data = {'type' : type};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/main/getBannerList",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);
            showBanner(result.BODY, type);
        },
        error: function(result) {
            console.log(result);
        }
    });
}
function showBanner(fileList, type){
    var domId = '';
    var idx = '';
    switch (type) {
        case 'T' :
            domId = 'topBanner';
            idx = '1';
            break;
        case 'M' :
            domId = 'middleBanner';
            idx = '0';
            break;
    }

    if(fileList.length != 0) {
        var bannerHtml = '';
        $(fileList).each(function (idx, file) {
            var src = ServerUrl + '/file/download?fileName=' + `${file.path}`;
            bannerHtml += `
                                <div class="img-upload-item">
                                    <div class="controll-wrap">
                                        <div class="img-preview">
                                            <img class="preview" src="` + src + `" style="display: block"/>
                                        </div>
                                        <div class="delete-wrap">
                                            <input type="hidden" name="banner" value="${file.banner}"/>
                                            <span class="img-del-btn">삭제</span>
                                        </div>
                                    </div>
                                    <p class="input-label">*클릭 시 이동할 링크 주소 (http://를 포함해서 입력해 주세요. 입력한 주소는 수정하실 수 없습니다.)</p>
                                    <input type="text" name="link" value="${file.link}" readonly/>
                                </div>
                    `;
        });
        $("#"+domId).html(bannerHtml);
    } else {
        var bannerHtml = '';

        $("#"+domId).html(bannerHtml);
    }

    if($('#middleBanner').children().length == 0){
        // $('.middle-add').show();
        // alert('adsf');
    }
}
function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}

// 회원 리스트
function memberList(){
    var data = {};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/admin/getMemberList",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);
            var memberHtml = '';
            var total = 0;
            $(result.BODY).each(function(idx, member) {

                total += 1;

                var role = '';
                var regdate = `${member.regDate}`;
                switch(`${member.role}`){
                    case 'ROLE_USER':
                        role = '일반 회원';
                        break;
                    case 'ROLE_STORE':
                        role = '가게 회원';
                        break;
                }
                regdate = regdate.substr(0,10);

                if(`${member.role}` != 'ROLE_ADMIN'){
                    memberHtml += `
                                <tr class="list-item" onClick="location.href='/admin/memberDetail?mid=${member.member}&cmd=2'">
                                    <td onclick="event.cancelBubble=true">
                                       <input type="checkbox" class="delChk" name="chk[]" value="${member.member}"/>
                                    </td>
                                    <td>
                                        ${member.member}
                                    </td>
                                    <td>
                                        `+role+`
                                    </td>
                                    <td>
                                        ${member.id}
                                    </td>
                                    <td>
                                        ${member.name}
                                    </td>
                                    <td>
                                       `+regdate+`
                                    </td>
                                </tr>
                                `;
                }
            });
            $("#member-list").html(memberHtml);

            $('.totalNum').text(total);
        },
        error: function(result) {
            console.log(result);
        }
    });
}
// 회원 리스트 검색
function memberSearchList(role, keyword){
    var data = {
                "role" : role,
                "keyword" : keyword
                };
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/admin/getMemberSearchList",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);
            var memberHtml = '';
            var total = 0;
            $(result.BODY).each(function(idx, member) {

                total += 1;

                var role = '';
                var regdate = `${member.regDate}`;
                switch(`${member.role}`){
                    case 'ROLE_USER':
                        role = '일반 회원';
                        break;
                    case 'ROLE_STORE':
                        role = '가게 회원';
                        break;
                }
                regdate = regdate.substr(0,10);

                if(`${member.role}` != 'ROLE_ADMIN'){
                    memberHtml += `
                                <tr class="list-item" onClick="location.href='/admin/memberDetail?mid=${member.member}&cmd=2'">
                                    <td onclick="event.cancelBubble=true">
                                       <input type="checkbox" class="delChk" name="chk[]" value="${member.member}"/>
                                    </td>
                                    <td>
                                        ${member.member}
                                    </td>
                                    <td>
                                        `+role+`
                                    </td>
                                    <td>
                                        ${member.id}
                                    </td>
                                    <td>
                                        ${member.name}
                                    </td>
                                    <td>
                                       `+regdate+`
                                    </td>
                                </tr>
                                `;
                }
            });
            $("#member-list").html(memberHtml);
            $('.totalNum').text(total);
        },
        error: function(result) {
            console.log(result);
        }
    });
}
// 회원 상세정보 불러오기
function getMember(mid){
    var data = {"member":mid};
        $.ajax({
            type: "POST",
            headers: {
                "Content-Type": "application/json",
                "X-HTTP-Method-Override": "POST"
            },
            url: ServerUrl+"/admin/getMember",
            data: JSON.stringify(data),
            success: function(result) {
                console.log(result);

                if(result.BODY.role == 'ROLE_USER'){
                    $('.rollStore').hide();
                }else if(result.BODY.role == 'ROLE_STORE'){
                    $('.rollUser').hide();
                }

                var regdate = result.BODY.regDate;
                var chnDate = result.BODY.chnDate;
                regdate = regdate.substr(0,10);
                chnDate = chnDate.substr(0,10);

                $('.market-btn').prop('href', '/admin/memberDetail?cmd=2&sid='+result.BODY.id);
                $('input[name=id]').val(result.BODY.id);
                $('input[name=name]').val(result.BODY.name);
                $('input[name=phone]').val(result.BODY.phone);
                $('input[name=email]').val(result.BODY.email);
                $('input[name=address]').val(result.BODY.address);
                $('input[name=regDate]').val(regdate);
                $('input[name=chnDate]').val(chnDate);

            },
            error: function(result) {
                console.log(result);
            }
        });
}

// 시장 리스트
function marketList(){
    var data = {};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/admin/getMarketList",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);
            var marketHtml = '';
            var total = 0;
            $(result.BODY).each(function(idx, market) {

                total += 1;

                var role = '';
                var regdate = `${market.regDate}`;

                regdate = regdate.substr(0,10);

                marketHtml += `
                            <tr class="list-item" onClick="location.href='/admin/marketDetail?mid=${market.market}&cmd=2'">
                                <td onclick="event.cancelBubble=true">
                                   <input type="checkbox" class="delChk" name="chk[]" value="${market.market}"/>
                                </td>
                                <td>
                                    ${market.market}  
                                </td>
                                <td>
                                    `+regdate+`
                                </td>
                                <td>
                                     ${market.name}  
                                </td>
                                <td>
                                     ${market.phone}  
                                </td>
                                <td>
                                     ${market.email}  
                                </td>
                                <td>
                                     ${market.address}  
                                </td>
                            </tr>
                            `;

            });
            $("#market-list").html(marketHtml);
            $('.totalNum').text(total);
        },
        error: function(result) {
            console.log(result);
        }
    });
}
// 시장 상세정보 불러오기
function getMarket(mid){
    var data = {"market":mid};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/admin/getMarket",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);

            $('input[name=name]').val(result.BODY.name);
            $('input[name=phone]').val(result.BODY.phone);
            $('input[name=manager]').val(result.BODY.manager);
            $('input[name=email]').val(result.BODY.email);
            $('input[name=holiday]').val(result.BODY.holiday);
            $('input[name=homepage]').val(result.BODY.homepage);
            $('input[name=open]').val(result.BODY.open);
            $('input[name=park]').val(result.BODY.park);
            $('input[name=address]').val(result.BODY.address);
            $('input[name=lat]').val(result.BODY.lat);
            $('input[name=lon]').val(result.BODY.lon);
            $('textarea').val(result.BODY.detail);
            $(result.BODY.categories).each(function(idx, category) {

                $('.category'+`${category.category}`).prop("checked", true);

            });

            var imgHtml = '';
            if(result.BODY.files.length != 0){
                $(result.BODY.files).each(function(idx, file) {
                    var src = ServerUrl+'/file/download?fileName='+`${file.path}`;
                    var mrfile = `${file.mrfile}`;
                    imgHtml += '                                <div class="img-upload-item">\n' +
                        '                                    <div class="controll-wrap">\n' +
                        '                                        <div class="img-preview">\n' +
                        '                                            <img class="preview" src="'+src+'" style="display: block;"/>\n' +
                        '                                        </div>\n' +
                        '                                        <div class="delete-wrap">\n' +
                        '                                            <input type="hidden" name="mrfile" value="'+mrfile+'"/>\n' +
                        '                                            <span class="img-del-btn">삭제</span>\n'+
                        '                                        </div>\n' +
                        '                                    </div>\n' +
                        '                                </div>';
                });
            } else {
                imgHtml += '                                <div class="img-upload-item">\n' +
                    '                                    <input id="img1" class="img-file" type="file" accept="image/*" name="pimg" />\n' +
                    '                                    <div class="controll-wrap">\n' +
                    '                                        <div class="img-preview">\n' +
                    '                                            <p class="no-img">등록된 이미지 없음</p>\n' +
                    '                                            <img class="preview" src="" />\n' +
                    '                                        </div>\n' +
                    '                                        <div class="delete-wrap">\n' +
                    '                                        </div>\n' +
                    '                                    </div>\n' +
                    '                                </div>';
            }

            $("#imgFile").html(imgHtml);
        },
        error: function(result) {
            console.log(result);
        }
    });
}


// 가게 리스트
function storeList(){
    var data = {};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/admin/getStoreList",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);
            var storeHtml = '';
            var total = 0;
            $(result.BODY).each(function(idx, store) {

                total += 1;

                var regdate = `${store.regDate}`;
                regdate = regdate.substr(0,10);

                var recYn = `${store.recYn}`;
                var popYn = `${store.popYn}`;
                var recSelect = '';
                var popSelect = '';
                var recPopSelect = '';
                var noSelect = '';

                if(recYn == 'Y' && popYn == 'Y'){
                    recPopSelect = 'selected';
                }
                else if(recYn == 'Y'){
                    recSelect = 'selected';
                }
                else if(popYn == 'Y'){
                    popSelect = 'selected';
                }
                else if(recYn == 'N' && popYn == 'N'){
                    noSelect = 'selected';
                }


                storeHtml += `
                                <tr class="list-item" onClick="location.href='storeDetail?sid=${store.store}&cmd=2'">
                                    <td onclick="event.cancelBubble=true">
                                        <input type="checkbox" class="delChk" name="chk[]" value="${store.store}"/>
                                    </td>
                                    <td>
                                        ${store.store}
                                    </td>
                                    <td>
                                        ${store.mname}
                                    </td>
                                    <td>
                                    </td>
                                    <td>
                                    ${store.sname}
                                    </td>
                                    <td>
                                       `+regdate+`
                                    </td>
                                    <td onclick="event.cancelBubble=true">
                                        <input type="hidden" class="storeKey" value="${store.store}"/>
                                        <select id="storeSetting">
                                            <option value="NoRecPopStore" `+noSelect+`></option>
                                            <option value="RecStore" `+recSelect+`>추천</option>
                                            <option value="PopStore" `+popSelect+`>인기</option>
                                            <option value="RecPopStore" `+recPopSelect+`>추천/인기</option>
                                        </select>
                                    </td>
                                </tr>
                                `;
            });
            $("#storeList").html(storeHtml);
            $('.totalNum').text(total);
        },
        error: function(result) {
            console.log(result);
        }
    });
}
function marketSearchList(searchType, keyword){
    var data = {
                "searchType":searchType,
                "keyword":keyword
                };
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/admin/getStoreSearchList",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);
            var storeHtml = '';
            var total = 0;
            $(result.BODY).each(function(idx, store) {

                total += 1;

                var regdate = `${store.regDate}`;
                regdate = regdate.substr(0,10);


                storeHtml += `
                                <tr class="list-item" onClick="location.href='/admin/marketDetail?sid=${store.store}&cmd=2'">
                                    <td onclick="event.cancelBubble=true">
                                        <input type="checkbox" class="delChk" name="chk[]" value="${store.store}"/>
                                    </td>
                                    <td>
                                        ${store.store}
                                    </td>
                                    <td>
                                        ${store.mname}
                                    </td>
                                    <td>
                                    </td>
                                    <td>
                                    ${store.sname}
                                    </td>
                                    <td>
                                       `+regdate+`
                                    </td>
                                    <td>
                                        <form>
                                            <input type="hidden" class="storeSettingValue" name="recYn" value="N"/>
                                            <input type="hidden" class="storeSettingValue" name="popYn" value="N"/>
                                            <input type="hidden" name="store" value="${store.store}"/>
                                            <select id="storeSetting">
                                                <option value=""></option>
                                                <option value="recYn">추천</option>
                                                <option value="popYn">인기</option>
                                            </select>
                                        </form>
                                    </td>
                                </tr>
                                `;
            });
            $("#storeList").html(storeHtml);
            $('.totalNum').text(total);
        },
        error: function(result) {
            console.log(result);
        }
    });
}
function storeCategoryList(mid){
    var data = {"market":mid};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/admin/getMarket",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);

            $(result.BODY.categories).each(function(idx, category) {


            });

        },
        error: function(result) {
            console.log(result);
        }
    });

}
// 가게 카테고리 불러오기
function marketCategoryList(mid, category){
    var data = {"market":mid};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/admin/getMarket",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);
            var categoryhtml = '';
            $(result.BODY.categories).each(function(idx, category) {

                categoryhtml += `
                                <option value="${category.category}">${category.name}</option>
                            `;
            });
            $("#category").html(categoryhtml);

            $('#category').val(category).prop("selected", true);


        },
        error: function(result) {
            console.log(result);
        }
    });
}
// 가게 상세정보 불러오기
function getStore(sid){
    var data = {"store":sid};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/admin/getStore",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);

            var regdate = result.BODY.regDate;
            var chnDate = result.BODY.chnDate;
            regdate = regdate.substr(0,10);
            chnDate = chnDate.substr(0,10);

            $('input[name=sname]').val(result.BODY.sname);
            $('input[name=mname]').val(result.BODY.mname);



            $(result.BODY.categories).each(function(idx, category) {
                marketCategoryList(result.BODY.market, `${category.category}`);
            });
            $(result.BODY.workdays).each(function(idx, workday) {

                $('.workdays'+`${workday.type}`).prop("checked", true);
            });

            $('input[name=sthm]').val(result.BODY.sthm);
            $('input[name=ndhm]').val(result.BODY.ndhm);
            $('input[name=holidayInfo]').val(result.BODY.holidayInfo);
            $('input[name=sphone]').val(result.BODY.sphone);
            $('input[name=kakao]').val(result.BODY.kakao);
            $('input[name=address]').val(result.BODY.address);

            $('.sinfo').val(result.BODY.sinfo);

        },
        error: function(result) {
            console.log(result);
        }
    });
}
// 가게 상세정보(관리자) 카테고리 리스트
function getCategoryList(){
    var data = {};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/admin/getCategoryList",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);
            var categoryHtml = '';
            var first = "";

            $(result.BODY).each(function(idx, category) {

                if(idx == 0){
                    first = "active";
                }
                else{
                    first = "";
                }

                categoryHtml += `
                                <label>
                                    <input type="checkbox" name="categories" class="category${category.category}" value="${category.category}"/> 
                                    ${category.name}
                                </label>
                                `;
                $(".category-wrap").html(categoryHtml);
            });

        },
        error: function(result) {
            console.log(result);
        }
    });
}
// // 가게 이름 가져오기
// function getMarketName(mid){
//
//
//     var data = {
//                     "market" : mid
//                 };
//     $.ajax({
//         type: "POST",
//         headers: {
//             "Content-Type": "application/json",
//             "X-HTTP-Method-Override": "POST"
//         },
//         url: ServerUrl+"/admin/getMarket",
//         data: JSON.stringify(data),
//         success: function(result) {
//             console.log(result);
//                 marketName = result.BODY.name;
//
//         },
//         error: function(result) {
//             console.log(result);
//         }
//     });
// }
// 이벤트 리스트
function eventList(){
    var data = {};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/admin/getEventList",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);
            var eventHtml = '';
            var total = 0;
            $(result.BODY).each(function(idx, event) {
                var marketName = '';
                total += 1;

                var regdate = `${event.regDate}`;
                regdate = regdate.substr(0,10);



                eventHtml += `
                                <tr class="list-item" onClick="location.href='/admin/eventDetail?eid=${event.event}&cmd=2'">
                                    <td onclick="event.cancelBubble=true">
                                        <input type="checkbox" class="delChk" name="chk[]" value="${event.event}"/>
                                    </td>
                                    <td>
                                        `+regdate+`
                                    </td>
                                    <td>
                                        ${event.market}
                                    </td>
                                    <td>
                                        ${event.title}
                                    </td>
                                    <td>
                                        ${event.tot}명 <a href="/admin/eventParticipants?eid=${event.event}" class="event-morm-btn" href="">보기</a>
                                    </td>
                                </tr>
                                `;
            });
            $("#eventList").html(eventHtml);
            $('.totalNum').text(total);
        },
        error: function(result) {
            console.log(result);
        }
    });
}
// 이벤트 리스트
function getMemberListInEvent(eid){
    var data = {"event":eid};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/admin/getMemberListInEvent",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);
            var eventHtml = '';
            var total = 0;
            $(result.BODY).each(function(idx, event) {
                var marketName = '';
                total += 1;

                var regdate = `${event.regDate}`;
                regdate = regdate.substr(0,10);



                eventHtml += `
                                <tr class="list-item">
                                    <td onclick="event.cancelBubble=true">
                                        <input type="checkbox" class="delChk" name="chk[]" value="${event.member}"/>
                                    </td>
                                    <td>
                                       `+regdate+`
                                    </td>
                                    <td>
                                        ${event.name}
                                    </td>
                                    <td>
                                        ${event.phone}
                                    </td>
                                    <td>
                                       ${event.email}
                                    </td>
                                    <td>
                                       ${event.address}
                                    </td>
                                </tr>
                                `;
            });
            $("#eventList").html(eventHtml);
            $('.totalNum').text(total);
        },
        error: function(result) {
            console.log(result);
        }
    });
}
// 이벤트 상세정보 불러오기
function getEvent(eid){
    var data = {"event":eid};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/admin/getEvent",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);
            $("#marketList").val(result.BODY.market).prop("selected", true);


            $('input[name=bgndt]').val(result.BODY.bgndt);
            $('input[name=nddt]').val(result.BODY.nddt);

            $('input[name=title]').val(result.BODY.title);
            $('.contents').text(result.BODY.contents);

            var imgHtml = '';
            if(result.BODY.files.length != 0){
                var src = ServerUrl+'/file/download?fileName='+result.BODY.files[0].path;
                imgHtml += '                                <div class="img-upload-item">\n' +
                    '                                    <input id="img1" class="img-file" type="file" accept="image/*" name="pimg" />\n' +
                    '                                    <div class="controll-wrap">\n' +
                    '                                        <div class="img-preview">\n' +
                    '                                            <img class="preview" src="'+src+'" style="display: block;"/>\n' +
                    '                                        </div>\n' +
                    '                                        <div class="delete-wrap">\n' +
                    '                                        </div>\n' +
                    '                                    </div>\n' +
                    '                                </div>';
            } else {
                imgHtml += '                                <div class="img-upload-item">\n' +
                    '                                    <input id="img1" class="img-file" type="file" accept="image/*" name="pimg" />\n' +
                    '                                    <div class="controll-wrap">\n' +
                    '                                        <div class="img-preview">\n' +
                    '                                            <p class="no-img">등록된 이미지 없음</p>\n' +
                    '                                            <img class="preview" src="" />\n' +
                    '                                        </div>\n' +
                    '                                        <div class="delete-wrap">\n' +
                    '                                        </div>\n' +
                    '                                    </div>\n' +
                    '                                </div>';
            }

            $("#imgFile").html(imgHtml);
        },
        error: function(result) {
            console.log(result);
        }
    });
}
//이벤트 가게 리스트 불러오기
function getEventMarketList() {
    var data = {};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/admin/getMarketList",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);

            var marketHtml = '';
            $(result.BODY).each(function(idx, market) {
                marketHtml += `
                              <option value="${market.market}">${market.name}</option>
                                `;
                $("#marketList").html(marketHtml);


            });

        },
        error: function(result) {
            console.log(result);
        }
    });

}

// 공지사항 리스트
function noticeList(){
    var data = {};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/admin/getNoticeList",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);
            var noticeHtml = '';
            var total = 0;
            $(result.BODY).each(function(idx, notice) {

                total += 1;

                var regdate = `${notice.regDate}`;
                regdate = regdate.substr(0,10);


                noticeHtml += `
                                <tr class="list-item" onClick="location.href='/admin/noticeDetail?nid=${notice.notice}'">
                                    <td onclick="event.cancelBubble=true">
                                        <input type="checkbox" class="delChk" name="chk[]" value="${notice.notice}"/>
                                    </td>
                                    <td>
                                        ${notice.notice}
                                    </td>
                                    <td>
                                        ${notice.title}
                                    </td>
                                    <td>
                                        `+regdate+`
                                    </td>
                                </tr>
                                `;
            });
            $("#noticeList").html(noticeHtml);
            $('.totalNum').text(total);
        },
        error: function(result) {
            console.log(result);
        }
    });
}
// 공지사항 상세정보 불러오기
function getNotice(nid){
    var data = {"notice":nid};
        $.ajax({
            type: "POST",
            headers: {
                "Content-Type": "application/json",
                "X-HTTP-Method-Override": "POST"
            },
            url: ServerUrl+"/admin/getNotice",
            data: JSON.stringify(data),
            success: function(result) {
                console.log(result);

                var regdate = result.BODY.regDate;
                regdate = regdate.substr(0,10);

                $('input[name=title]').val(result.BODY.title);
                $('input[name=contents]').val(result.BODY.contents);

                $('.title').text(result.BODY.title);
                $('.regdate').text(regdate);
                $('.contents').text(result.BODY.contents);
                $('.modify').prop('href', '/admin/noticeEdit?nid='+result.BODY.notice+'&cmd=2');

            },
            error: function(result) {
                console.log(result);
            }
        });
}
// 카테고리 리스트
function categoryList(){
    var data = {};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/admin/getCategoryList",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);
            var categoryHtml = '';
            var total = 0;
            $(result.BODY).each(function(idx, category) {

                total += 1;

                var regdate = `${category.regDate}`;
                regdate = regdate.substr(0,10);


                categoryHtml += `
                                <tr class="list-item" onClick="location.href='/admin/categoryDetail?cid=${category.category}&cmd=2'">
                                    <td onclick="event.cancelBubble=true">
                                        <input type="checkbox" class="delChk" name="chk[]" value="${category.category}"/>
                                    </td>
                                    <td>
                                        ${category.category}
                                    </td>
                                    <td>
                                        ${category.name}
                                    </td>
                                    <td>
                                        `+regdate+`
                                    </td>
                                </tr>
           
                                `;
            });
            $("#categoryList").html(categoryHtml);
            $('.totalNum').text(total);
        },
        error: function(result) {
            console.log(result);
        }
    });
}
// 카테고리 상세정보 불러오기
function getCategory(cid){
    var data = {"category":cid};
        $.ajax({
            type: "POST",
            headers: {
                "Content-Type": "application/json",
                "X-HTTP-Method-Override": "POST"
            },
            url: ServerUrl+"/admin/getCategory",
            data: JSON.stringify(data),
            success: function(result) {
                console.log(result);

                $('input[name=name]').val(result.BODY.name);
                $('input[name=category]').val(result.BODY.category);

            },
            error: function(result) {
                console.log(result);
            }
        });
}

// 로그아웃
function fnLogout(){
    if(confirm('로그아웃 하시겠습니까?')){
        window.location.href = '/logout';
    }
}
function banneerList(type){
    // 가게정보
    var data = {'type' : type};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/main/getBannerList",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);
            showBanner(result.BODY, type);
        },
        error: function(result) {
            console.log(result);
        }
    });
}
function showBanner(fileList, type){
    var domId = '';
    var idx = '';
    switch (type) {
        case 'T' :
            domId = 'topBanner';
            idx = '1';
            break;
        case 'M' :
            domId = 'middleBanner';
            idx = '0';
            break;
    }

    if(fileList.length != 0) {
        var bannerHtml = '';
        $(fileList).each(function (idx, file) {
            var src = ServerUrl + '/file/download?fileName=' + `${file.path}`;
            bannerHtml += `
                                <div class="img-upload-item">
                                    <div class="controll-wrap">
                                        <div class="img-preview">
                                            <img class="preview" src="` + src + `" style="display: block"/> 
                                        </div>
                                        <div class="delete-wrap">
                                            <input type="hidden" name="banner" value="${file.banner}"/>
                                            <span class="img-del-btn">삭제</span>
                                        </div>
                                    </div>
                                    <p class="input-label">*클릭 시 이동할 링크 주소 (http://를 포함해서 입력해 주세요. 입력한 주소는 수정하실 수 없습니다.)</p>
                                    <input type="text" name="link" value="${file.link}" readonly/>
                                </div>
                    `;
        });
        $("#"+domId).html(bannerHtml);
    } else {
        var bannerHtml = '';

        $("#"+domId).html(bannerHtml);
    }

    if($('#middleBanner').children().length == 0){
        // $('.middle-add').show();
        // alert('adsf');
    }
}

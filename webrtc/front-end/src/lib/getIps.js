/* eslint-disable*/
export default function getIPs(callback){
    var ip_dups = {};
    var RTCPeerConnection = window.RTCPeerConnection
        || window.mozRTCPeerConnection
        || window.webkitRTCPeerConnection;
    var useWebKit = !!window.webkitRTCPeerConnection;


    if(!RTCPeerConnection){
      
        var win = iframe.contentWindow;
        RTCPeerConnection = win.RTCPeerConnection
            || win.mozRTCPeerConnection
            || win.webkitRTCPeerConnection;
        useWebKit = !!win.webkitRTCPeerConnection;
    }


    var mediaConstraints = {
        optional: [{RtpDataChannels: true}]
    };


    var servers = {iceServers: [{urls: "stun:stun.services.mozilla.com"}]};


    var pc = new RTCPeerConnection(servers, mediaConstraints);


    function handleCandidate(candidate){
        var ip_regex = /([0-9]{1,3}(\.[0-9]{1,3}){3}|[a-f0-9]{1,4}(:[a-f0-9]{1,4}){7})/
        var tt=ip_regex.exec(candidate);
      
       if(tt==null||tt.length<2){
       return false;
       }
        var ip_addr = tt[1];
        
      


        if(ip_dups[ip_addr] === undefined)
            callback(ip_addr);


        ip_dups[ip_addr] = true;
    }


    pc.onicecandidate = function(ice){


        if(ice.candidate)
            handleCandidate(ice.candidate.candidate);
    };


    pc.createDataChannel("");


    pc.createOffer(function(result){


        pc.setLocalDescription(result, function(){}, function(){});


    }, function(){});


    setTimeout(function(){
        var lines = pc.localDescription.sdp.split('\n');


        lines.forEach(function(line){
            if(line.indexOf('a=candidate:') === 0)
                handleCandidate(line);
        });
    }, 1000);
}

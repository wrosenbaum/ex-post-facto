var bufferHeight = 400;// height of a single buffer
var bufferWidth = 30;  // width of a single buffer
var nBuffers = 16;     // number of buffers
var packetBorder = 5;  // boarder size of a packet
var packetMargin = 5;  // margin of a packet
var packetSpeed = 20; // speed of packet motion
var packetWidth = 30;  // width of a packet
var packetSize = packetWidth + 2 * packetBorder + 2 * packetMargin;
var canvasWidth = nBuffers * bufferWidth; // canvas width
var savedBuffers = [];

var canvases = [];

class InjectionSite {
    constructor(parentID, index) {
	this.pkt = document.createElement("div");
	this.pkt.className = "injectPacket";
	this.pkt.style.borderWidth = packetBorder + "px !important";
	this.pkt.style.margin = packetMargin + "px";
	this.pkt.style.width = packetWidth + "px";
	this.pkt.style.height = packetWidth + "px";
	this.pkt.style.top = "0px";
	this.pkt.style.left = index * packetSize + "px";
	this.pkt.id = index + "inj" + parentID;
	this.parentID = parentID;
	this.parent = document.getElementById(parentID);

	this.parent.appendChild(this.pkt);

	this.pkt.addEventListener("click", function() {
	    // Get index
	    var injIndex = parseInt(this.id);
	    var canvasIndex = getRoutingCanvasIndex(this);

	    // Inject a packet
	    canvases[canvasIndex].injectAt(injIndex);
		
	    var count = 0;
	    var id = setInterval(injectThenForward, 500);

	    function injectThenForward () {
	    	if (count == 0) {
	    	    canvases[canvasIndex].forwardFrom(injIndex);
	    	    count++;
	    	} else {		    
	    	    clearInterval(id);
	    	}
		
	    	count++;
	    }
	    
	}, false);
    }
}

class RoutingCanvas {
    constructor(contextID, rule) {	
	// Make a canvas object and give it the right dimensions
	this.canvas = document.createElement("div");
	this.canvas.className = "routing-canvas";
	this.canvas.id = "canvas" + contextID;
	this.canvas.style.width = nBuffers * packetSize + "px";
	this.canvas.style.height = bufferHeight + "px";
	this.canvas.style.margin = "auto";
	document.getElementById(contextID).appendChild(this.canvas);
	
	this.contextID = contextID;
	this.id = this.canvas.id;

	// Define the rule of the canvas (i.e. forwarding protocol)
	this.rule = rule;

	// Make arrays for buffers and packets
	this.buffers = [];
	this.packets = [];

	// Initialize the buffers
	for (var i = 0; i < nBuffers; i++) {
	    this.buffers.push(0);
	    this.packets.push([]);
	    // Make an inject packet at the top
	    var pkt = new InjectionSite(this.canvas.id, i);
	}
    }

    injectAt(index) {
	this.buffers[index]++;
    
	var pkt = document.createElement("div");
	this.canvas.appendChild(pkt);
    
	pkt.className = "packet";
	pkt.style.borderWidth = packetBorder + "px";
	pkt.style.margin = packetMargin + "px";
	pkt.style.width = packetWidth + "px";
	pkt.style.height = packetWidth + "px";
	pkt.style.top = "0px";
	pkt.style.left = (index * packetSize) + "px";

	this.packets[index].push(pkt);

	moveObjectTo(pkt, index * packetSize, bufferHeight - this.buffers[index] * packetSize);

    }

    forwardFrom(index) {
	//alert("Injecting into buffer " + this.id + " at index " + index);
	// Decide which packets need to be forwarded
	var fwd = getForwardArray(this.rule, index, this.buffers);
	var xFin, yFin, pkt;
	
	// Animate packet fowarding
	var pkt;
	for (var i = 0; i < nBuffers - 1; i++) {
	    if(fwd[i]) {
		xFin = (i + 1) * packetSize;
		yFin = bufferHeight - (this.packets[i+1].length + 1) * packetSize;

		if(fwd[i+1]) {
		    yFin += packetSize;
		}
		pkt = this.packets[i][this.packets[i].length - 1];
		moveObjectTo(pkt, xFin, yFin);
	    }
	}

	if (fwd[nBuffers - 1]) {
	    pkt = this.packets[nBuffers - 1][this.packets[nBuffers - 1].length - 1];
	    moveObjectTo(pkt, nBuffers * packetSize, bufferHeight);

	    // Remove the packet
	    this.packets[nBuffers - 1].pop();
	    this.canvas.removeChild(pkt);
	}

	// Move actual packets
	for (var i = nBuffers - 2; i >= 0; i--) {
	    if(fwd[i]) {
		this.packets[i+1].push(this.packets[i].pop());
	    }
	}

	// Update buffer sizes
	for (var i = 0; i < nBuffers; i++) {
	    this.buffers[i] = this.packets[i].length;
	}
	

    }
}

function initializeBuffers(contextID, rule) {
    var canvas = new RoutingCanvas(contextID, rule);
    canvases.push(canvas);

}


function getRoutingCanvasIndex(pkt) {
    var canvasID = pkt.parentNode.id;
    for(i = 0; i < canvases.length; i++) {
	if (canvases[i].canvas.id === canvasID) {
	    return i;
	}
    }
}

function getCanvasFromID(canvasID) {
    for(i = 0; i < canvases.length; i++) {
	if(canvases[i].canvas.id === canvasID) {
	    return i;
	}
    }
}

function forwardNoInj(contextID) {
    var index = getCanvasFromID("canvas" + contextID);
    canvases[index].forwardFrom(0);
}

function saveConfig(contextID) {
    var index = getCanvasFromID("canvas" + contextID);
    savedBuffers = canvases[index].buffers.slice();
}

function restoreConfig(contextID) {
    var index = getCanvasFromID("canvas" + contextID);

    // Destroy old packets
    for(var i = 0; i < nBuffers; i++) {
	for(var j = 0; j < canvases[index].packets[i].length; j++) {   
	    canvases[index].canvas.removeChild(canvases[index].packets[i][j]);
	}
	canvases[index].packets[i] = [];
    }

    canvases[index].buffers = [];

    for(var i = 0; i < nBuffers; i++) {
	canvases[index].buffers.push(0);
	canvases[index].packets.push([]);
    }

    // Restore
    for(var i = 0; i < nBuffers; i++) {
	for(var j = 0; j < savedBuffers[i]; j++) {   
	    canvases[index].injectAt(i);
	}
    }

}

function moveObjectTo(obj, xFin, yFin) {
    var x = parseInt(obj.style.left);
    var y = parseInt(obj.style.top);

    var dx = (xFin - x) / packetSpeed;
    var dy = (yFin - y) / packetSpeed;

    var pos, dp, fin;
    
    if (dx != 0) {
	pos = x;
	dp = dx;
	fin = xFin;
    } else {
	pos = y;
	dp = dy;
	fin = yFin;
    }
        
    var id = setInterval(frame, 10);
    
    function frame() {
    	if (pos == fin || Math.sign(dp) != Math.sign(fin - pos))
	{
    	    clearInterval(id);
	    obj.style.left = xFin + "px";
    	    obj.style.top = yFin + "px";
    	} else {
	    x += dx;
	    y += dy;
	    pos += dp;
    	    obj.style.left = x + "px";
	    obj.style.top = y + "px";
    	}
    }
}

function moveObjectRelative(obj, dx, dy) {
    var x = parseInt(obj.style.left);
    var y = parseInt(obj.style.top);
    moveObjectTo(obj, x + dx, y + dy);
}

// function injectPacket (index, canvas) {
//     buffers[index]++;
    
//     var pkt = document.createElement("div");
//     canvas.appendChild(pkt);
    
//     pkt.className = "packet";
//     pkt.style.borderWidth = packetBorder + "px";
//     pkt.style.margin = packetMargin + "px";
//     pkt.style.width = packetWidth + "px";
//     pkt.style.height = packetWidth + "px";
//     pkt.style.top = "0px";
//     pkt.style.left = (index * packetSize) + "px";

//     packets[index].push(pkt);



//     moveObjectTo(pkt, index * packetSize, bufferHeight - buffers[index] * packetSize)
// }

function getForwardArray (rule, inj, buffers) {
    var fwd = [];
    for (var i = 0; i < nBuffers; i++) {
	switch(rule) {
	case "oed":
	    fwd[i] = (buffers[i] > 0 && (i == nBuffers - 1 ||
					 buffers[i] > buffers[i+1] ||
					 (buffers[i] == buffers[i+1] && buffers[i] % 2 == 1)));
	    break;
	case "greedy":
	    fwd[i] = buffers[i] > 0;
	    break;
	case "global":
	    fwd[i] = (buffers[i] > 0 && i >= inj);
	    break;
	case "downhill":
	    fwd[i] = ((i == nBuffers - 1 && buffers[i] > 0) || buffers[i] > buffers[i+1]);
	    break;	    
	}
    }

    return fwd;
}



function forwardPackets (rule, inj, canvas) {
    // Decide which packets need to be forwarded
    var fwd = getForwardArray(rule, inj);
    var xFin, yFin, pkt;
    
    // Animate packet fowarding
    var pkt;
    for (var i = 0; i < nBuffers - 1; i++) {
	if(fwd[i]) {
	    xFin = (i + 1) * packetSize;
	    yFin = bufferHeight - (packets[i+1].length + 1) * packetSize;
	    if(fwd[i+1]) {
		yFin += packetSize;
	    }
	    pkt = packets[i][packets[i].length - 1];
	    moveObjectTo(pkt, xFin, yFin);
	}
    }

    if (fwd[nBuffers - 1]) {
	pkt = packets[nBuffers - 1][packets[nBuffers - 1].length - 1];
	moveObjectTo(pkt, nBuffers * packetSize, bufferHeight);

	// Remove the packet
	packets[nBuffers - 1].pop();
	canvas.removeChild(pkt);
    }

    // Move actual packets
    for (var i = nBuffers - 2; i >= 0; i--) {
	if(fwd[i]) {
	    packets[i+1].push(packets[i].pop());
	}
    }

    // Update buffer sizes
    for (var i = 0; i < nBuffers; i++) {
	buffers[i] = packets[i].length;
    }
    
}

function illustrateInject(id) {
    var index = getCanvasFromID("canvas" + id);
    var i = 0;
    var j = nBuffers;
    while(i < j - 1) {
	i = parseInt((i + j) / 2);
	
	for(var k = i; k < j; k++) {
	    canvases[index].injectAt(k);
	}

	j = parseInt((i + j) / 2);

	for(var k = i; k < j; k++) {
	    canvases[index].injectAt(k);
	}
	
	
    }

}

function plateauInject(id) {
    var index = getCanvasFromID("canvas" + id);
    var ht = 4; // plateau height
    var start = 3;
    var end = 14;
    for (var i = start; i < end; i++) {
	for (var j = 0; j < ht; j++) {
	    canvases[index].injectAt(i);
	}
    }
}

function randomInjections (num) {
    for (var i = 0; i < num; i++) {
	injectPacket(Math.floor(nBuffers * Math.random()));
    }
}

function randomInjectAndForward (num) {
    var count = 0;
    var id = setInterval(injectAndForward, 500);

    function injectAndForward () {
	if (count == num) {
	    clearInterval(id);
	} else {
	    forwardPackets();
	    randomInjections(1);	    
	    count++;
	}
    }
    
}



var lowerX = 82;
var dX = 100;
var lowerY = 352;
var dY = -100;
var offset = -10;
var treePackets = [];
var fwdCount = -1;

initialCoords = [
    [lowerX, lowerY],
    [lowerX, lowerY + offset],
    [lowerX, lowerY + 2 * offset],
    [lowerX + dX, lowerY],
    [lowerX + dX, lowerY + offset],
    [lowerX + 2 * dX, lowerY],
    [lowerX + 2 * dX, lowerY + offset],
    [lowerX + 3 * dX, lowerY],
    [lowerX + 3 * dX, lowerY + offset],
    [lowerX + 4 * dX, lowerY],
    [lowerX + 5 * dX, lowerY],
    [lowerX + 6 * dX, lowerY],
    [lowerX + 7 * dX, lowerY],
    [lowerX + 0.5 * dX, lowerY + dY],
    [lowerX + 0.5 * dX, lowerY + dY + offset],
    [lowerX + 2.5 * dX, lowerY + dY],
    [lowerX + 2.5 * dX, lowerY + dY + offset],
    [lowerX + 4.5 * dX, lowerY + dY],
    [lowerX + 6.5 * dX, lowerY + dY],
    [lowerX + 5.5 * dX, lowerY + 2 * dY],
    [lowerX + 5.5 * dX, lowerY + 2 * dY + offset]
];

fwdCoords = [
    [
	[2, [0.5 * dX, dY]],
	[9, [0.5 * dX, dY + offset]],
	[12, [-0.5 * dX, dY + offset]],
	[16, [-1 * dX, dY - offset]],
	[20, [-2 * dX, 0.5 * dY - offset]]
    ],
    
    [
	[2, [dX, dY - offset]],
	[8, [-0.5 * dX, dY]],
	[12, [-1 * dX, dY - offset]],
	[19, [-2 * dX, 0.5 * dY]],
	[20, [0, dY]]
    ],

    [
	[2, [2 * dX, 0.5 * dY - offset]],
	[9, [dX, dY]],
	[11, [0.5 * dX, dY + offset]],
	[19, [0, dY]]
	
    ],

    [
	[2, [0, dY]],
	[9, [-2 * dX, 0.5 * dY - offset]],
	[10, [-0.5 * dX, dY + offset]],
	[14, [dX, dY]]
    ]
    
];



function makeTreePacket () {
    var pkt = document.createElement("div");
    var treeCanvas = document.getElementById("tree-canvas");

    pkt.className = "packet";
    pkt.style.borderWidth = packetBorder + "px";
    pkt.style.margin = packetMargin + "px";
    pkt.style.width = packetWidth + "px";
    pkt.style.height = packetWidth + "px";
    pkt.style.top = "0px";
    pkt.style.left = "0px";

    treeCanvas.appendChild(pkt);

    return pkt;
}

function createTreePackets () {
    
    for(var i = 0; i < initialCoords.length; i++) {
	treePackets.push(makeTreePacket());
    }

    for(var i = 0; i < initialCoords.length; i++) {
	moveObjectTo(treePackets[i], initialCoords[i][0], initialCoords[i][1]);
    }

}

function fwdFromCoords (index) {
    if (index < fwdCoords.length) {
	var fwds = fwdCoords[index];
	for (var i = 0; i < fwds.length; i++) {
	    moveObjectRelative(treePackets[fwds[i][0]], fwds[i][1][0], fwds[i][1][1]);
	}
    }
}

function treeStep() {
    if(fwdCount < 0) {
	createTreePackets();
    } else {
	fwdFromCoords(fwdCount);
    }
    
    fwdCount++;
}






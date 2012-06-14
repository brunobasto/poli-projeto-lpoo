/*! jQuery klass core plugin v0.1a - Jean-Louis Grall - MIT license - http://code.google.com/p/jquery-klass-plugin */
( function( $ ) {

var $klass = $.klass = function( parent, fields ) {	// The class factory. It is also the invisible "super class" of all classes. Methods added to its prototype will be available to all classes.
	
		// If no parent is specified:
		if ( !fields ) {
			fields = parent;
			parent = 0;	// Takes less space to write than false.
		}
		
	 	// klass is the new class (and klass.init = klass). This function will be the new class and the constructor for new objects of that new class:
		var klass = fields.init || ( fields.init = function() {	// If no init is provided, make one.
				parent && parent.prototype.init.apply( this, arguments );	// Automatically calls the superconstructor if there is one.
			} ),
			linkingObj = function() { },	// Used to make the link between the new class and its parent class.
			proto, name;	// Used later as short references.
		
		linkingObj.prototype = (parent || $klass).prototype;	// Prepare the linking to the parent prototype. In case there is no parent, the parent is $klass itself (invisible super-class of all classes).
		proto = klass.prototype = new linkingObj;	// At the end we have: klass.prototype.[[prototype]] = linkingObj.prototype = parent.prototype. Here the "new" operator creates the new object with the right prototype chain, but doesn't call the constructor because there is no "()". See also: http://brokenliving.blogspot.com/2009/09/simple-javascript-inheritance.html
		
		klass._superklass = parent || null;	// keeps a reference to the visible superclass.
		
		// Add each function to the prototype of the new class (they are our new class methods):
		for ( name in fields ) {
			// Add the static variables to the new class:
			if ( name == "_static" ) $.extend( klass, fields[name] );
			// Each new method keeps a reference to its name and its class, allowing us to find its super method dynamically at runtime:
			else ( proto[name] = fields[name] )._klass = { klass: klass, name: name };
		}
		
		proto.constructor = klass;	// Makes the klass function into a constructor for new objects.
		
		return klass;
	};

// Access a method of the super-class. The method _super is assigned to the $klass object, which is the invisible super-class of all classes.
// The super methods are determined dynamically at runtime. Thus the methods of any class can be dynamically added/removed at runtime (Must done properly: they must have a correct _klass field).
// The first argument must be the special "arguments" variable, followed by the arguments to be passed to the super method.
// Ex: this._up( arguments, "arg1", "arg2" );
// Optionally you can add the name of the super method to call as the first parameter. If not given, it will use the name of the method calling "_super()".
// Ex: this._up( "funcName", arguments, "arg1", "arg2" );
$klass.prototype._super = function( arg1, arg2 ) {
	var arg1IsArguments = arg1.callee,
		_klass = ( arg1IsArguments ? arg1 : arg2 ).callee._klass,
		name = arg1IsArguments ? _klass.name : arg1;
	return _klass.klass._superklass.prototype[ name ].apply( this, Array.prototype.slice.call( arguments, (arg1IsArguments ? 1 : 2) ) );
};

})( jQuery );
// Generated from F:/Mini-Java/Mini_Java/src/Parser\miniJava.g4 by ANTLR 4.7
package Parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class miniJavaParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, ID=31, INT=32, 
		WS=33, MUL=34, ADD=35, SUB=36, LT=37, AND=38;
	public static final int
		RULE_goal = 0, RULE_mainClass = 1, RULE_classDec = 2, RULE_varDec = 3, 
		RULE_methodDec = 4, RULE_type = 5, RULE_statement = 6, RULE_expression = 7;
	public static final String[] ruleNames = {
		"goal", "mainClass", "classDec", "varDec", "methodDec", "type", "statement", 
		"expression"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'class'", "'{'", "'public'", "'static'", "'void'", "'main'", "'('", 
		"'String'", "'['", "']'", "')'", "'}'", "'extends'", "';'", "','", "'return'", 
		"'int'", "'boolean'", "'if'", "'else'", "'while'", "'System.out.println'", 
		"'='", "'.'", "'length'", "'true'", "'false'", "'this'", "'new'", "'!'", 
		null, null, null, "'*'", "'+'", "'-'", "'<'", "'&&'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, "ID", "INT", "WS", "MUL", "ADD", 
		"SUB", "LT", "AND"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "miniJava.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public miniJavaParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class GoalContext extends ParserRuleContext {
		public MainClassContext mainClass() {
			return getRuleContext(MainClassContext.class,0);
		}
		public List<ClassDecContext> classDec() {
			return getRuleContexts(ClassDecContext.class);
		}
		public ClassDecContext classDec(int i) {
			return getRuleContext(ClassDecContext.class,i);
		}
		public GoalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_goal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof miniJavaListener ) ((miniJavaListener)listener).enterGoal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof miniJavaListener ) ((miniJavaListener)listener).exitGoal(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof miniJavaVisitor ) return ((miniJavaVisitor<? extends T>)visitor).visitGoal(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GoalContext goal() throws RecognitionException {
		GoalContext _localctx = new GoalContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_goal);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(16);
			mainClass();
			setState(20);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(17);
				classDec();
				}
				}
				setState(22);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MainClassContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(miniJavaParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(miniJavaParser.ID, i);
		}
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public MainClassContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mainClass; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof miniJavaListener ) ((miniJavaListener)listener).enterMainClass(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof miniJavaListener ) ((miniJavaListener)listener).exitMainClass(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof miniJavaVisitor ) return ((miniJavaVisitor<? extends T>)visitor).visitMainClass(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MainClassContext mainClass() throws RecognitionException {
		MainClassContext _localctx = new MainClassContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_mainClass);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(23);
			match(T__0);
			setState(24);
			match(ID);
			setState(25);
			match(T__1);
			setState(26);
			match(T__2);
			setState(27);
			match(T__3);
			setState(28);
			match(T__4);
			setState(29);
			match(T__5);
			setState(30);
			match(T__6);
			setState(31);
			match(T__7);
			setState(32);
			match(T__8);
			setState(33);
			match(T__9);
			setState(34);
			match(ID);
			setState(35);
			match(T__10);
			setState(36);
			match(T__1);
			setState(37);
			statement();
			setState(38);
			match(T__11);
			setState(39);
			match(T__11);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassDecContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(miniJavaParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(miniJavaParser.ID, i);
		}
		public List<VarDecContext> varDec() {
			return getRuleContexts(VarDecContext.class);
		}
		public VarDecContext varDec(int i) {
			return getRuleContext(VarDecContext.class,i);
		}
		public List<MethodDecContext> methodDec() {
			return getRuleContexts(MethodDecContext.class);
		}
		public MethodDecContext methodDec(int i) {
			return getRuleContext(MethodDecContext.class,i);
		}
		public ClassDecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classDec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof miniJavaListener ) ((miniJavaListener)listener).enterClassDec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof miniJavaListener ) ((miniJavaListener)listener).exitClassDec(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof miniJavaVisitor ) return ((miniJavaVisitor<? extends T>)visitor).visitClassDec(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassDecContext classDec() throws RecognitionException {
		ClassDecContext _localctx = new ClassDecContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_classDec);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(41);
			match(T__0);
			setState(42);
			match(ID);
			setState(45);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__12) {
				{
				setState(43);
				match(T__12);
				setState(44);
				match(ID);
				}
			}

			setState(47);
			match(T__1);
			setState(51);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__16) | (1L << T__17) | (1L << ID))) != 0)) {
				{
				{
				setState(48);
				varDec();
				}
				}
				setState(53);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(57);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(54);
				methodDec();
				}
				}
				setState(59);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(60);
			match(T__11);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarDecContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(miniJavaParser.ID, 0); }
		public VarDecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varDec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof miniJavaListener ) ((miniJavaListener)listener).enterVarDec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof miniJavaListener ) ((miniJavaListener)listener).exitVarDec(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof miniJavaVisitor ) return ((miniJavaVisitor<? extends T>)visitor).visitVarDec(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarDecContext varDec() throws RecognitionException {
		VarDecContext _localctx = new VarDecContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_varDec);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(62);
			type();
			setState(63);
			match(ID);
			setState(64);
			match(T__13);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MethodDecContext extends ParserRuleContext {
		public List<TypeContext> type() {
			return getRuleContexts(TypeContext.class);
		}
		public TypeContext type(int i) {
			return getRuleContext(TypeContext.class,i);
		}
		public List<TerminalNode> ID() { return getTokens(miniJavaParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(miniJavaParser.ID, i);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<VarDecContext> varDec() {
			return getRuleContexts(VarDecContext.class);
		}
		public VarDecContext varDec(int i) {
			return getRuleContext(VarDecContext.class,i);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public MethodDecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodDec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof miniJavaListener ) ((miniJavaListener)listener).enterMethodDec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof miniJavaListener ) ((miniJavaListener)listener).exitMethodDec(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof miniJavaVisitor ) return ((miniJavaVisitor<? extends T>)visitor).visitMethodDec(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethodDecContext methodDec() throws RecognitionException {
		MethodDecContext _localctx = new MethodDecContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_methodDec);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(66);
			match(T__2);
			setState(67);
			type();
			setState(68);
			match(ID);
			setState(69);
			match(T__6);
			setState(81);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__16) | (1L << T__17) | (1L << ID))) != 0)) {
				{
				setState(70);
				type();
				setState(71);
				match(ID);
				setState(78);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__14) {
					{
					{
					setState(72);
					match(T__14);
					setState(73);
					type();
					setState(74);
					match(ID);
					}
					}
					setState(80);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(83);
			match(T__10);
			setState(84);
			match(T__1);
			setState(88);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(85);
					varDec();
					}
					} 
				}
				setState(90);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
			}
			setState(94);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__18) | (1L << T__20) | (1L << T__21) | (1L << ID))) != 0)) {
				{
				{
				setState(91);
				statement();
				}
				}
				setState(96);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(97);
			match(T__15);
			setState(98);
			expression(0);
			setState(99);
			match(T__13);
			setState(100);
			match(T__11);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(miniJavaParser.ID, 0); }
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof miniJavaListener ) ((miniJavaListener)listener).enterType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof miniJavaListener ) ((miniJavaListener)listener).exitType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof miniJavaVisitor ) return ((miniJavaVisitor<? extends T>)visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_type);
		try {
			setState(108);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(102);
				match(T__16);
				setState(103);
				match(T__8);
				setState(104);
				match(T__9);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(105);
				match(T__17);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(106);
				match(T__16);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(107);
				match(ID);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode ID() { return getToken(miniJavaParser.ID, 0); }
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof miniJavaListener ) ((miniJavaListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof miniJavaListener ) ((miniJavaListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof miniJavaVisitor ) return ((miniJavaVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_statement);
		int _la;
		try {
			setState(151);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(110);
				match(T__1);
				setState(114);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__18) | (1L << T__20) | (1L << T__21) | (1L << ID))) != 0)) {
					{
					{
					setState(111);
					statement();
					}
					}
					setState(116);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(117);
				match(T__11);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(118);
				match(T__18);
				setState(119);
				match(T__6);
				setState(120);
				expression(0);
				setState(121);
				match(T__10);
				setState(122);
				statement();
				setState(123);
				match(T__19);
				setState(124);
				statement();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(126);
				match(T__20);
				setState(127);
				match(T__6);
				setState(128);
				expression(0);
				setState(129);
				match(T__10);
				setState(130);
				statement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(132);
				match(T__21);
				setState(133);
				match(T__6);
				setState(134);
				expression(0);
				setState(135);
				match(T__10);
				setState(136);
				match(T__13);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(138);
				match(ID);
				setState(139);
				match(T__22);
				setState(140);
				expression(0);
				setState(141);
				match(T__13);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(143);
				match(ID);
				setState(144);
				match(T__8);
				setState(145);
				expression(0);
				setState(146);
				match(T__9);
				setState(147);
				match(T__22);
				setState(148);
				expression(0);
				setState(149);
				match(T__13);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(miniJavaParser.INT, 0); }
		public TerminalNode ID() { return getToken(miniJavaParser.ID, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof miniJavaListener ) ((miniJavaListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof miniJavaListener ) ((miniJavaListener)listener).exitExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof miniJavaVisitor ) return ((miniJavaVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 14;
		enterRecursionRule(_localctx, 14, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(175);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				{
				setState(154);
				match(INT);
				}
				break;
			case 2:
				{
				setState(155);
				match(T__25);
				}
				break;
			case 3:
				{
				setState(156);
				match(T__26);
				}
				break;
			case 4:
				{
				setState(157);
				match(ID);
				}
				break;
			case 5:
				{
				setState(158);
				match(T__27);
				}
				break;
			case 6:
				{
				setState(159);
				match(T__28);
				setState(160);
				match(T__16);
				setState(161);
				match(T__8);
				setState(162);
				expression(0);
				setState(163);
				match(T__9);
				}
				break;
			case 7:
				{
				setState(165);
				match(T__28);
				setState(166);
				match(ID);
				setState(167);
				match(T__6);
				setState(168);
				match(T__10);
				}
				break;
			case 8:
				{
				setState(169);
				match(T__29);
				setState(170);
				expression(2);
				}
				break;
			case 9:
				{
				setState(171);
				match(T__6);
				setState(172);
				expression(0);
				setState(173);
				match(T__10);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(205);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(203);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
					case 1:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(177);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(178);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MUL) | (1L << ADD) | (1L << SUB) | (1L << LT) | (1L << AND))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(179);
						expression(14);
						}
						break;
					case 2:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(180);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(181);
						match(T__8);
						setState(182);
						expression(0);
						setState(183);
						match(T__9);
						}
						break;
					case 3:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(185);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(186);
						match(T__23);
						setState(187);
						match(T__24);
						}
						break;
					case 4:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(188);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(189);
						match(T__23);
						setState(190);
						match(ID);
						setState(191);
						match(T__6);
						setState(200);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__6) | (1L << T__25) | (1L << T__26) | (1L << T__27) | (1L << T__28) | (1L << T__29) | (1L << ID) | (1L << INT))) != 0)) {
							{
							setState(192);
							expression(0);
							setState(197);
							_errHandler.sync(this);
							_la = _input.LA(1);
							while (_la==T__14) {
								{
								{
								setState(193);
								match(T__14);
								setState(194);
								expression(0);
								}
								}
								setState(199);
								_errHandler.sync(this);
								_la = _input.LA(1);
							}
							}
						}

						setState(202);
						match(T__10);
						}
						break;
					}
					} 
				}
				setState(207);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 7:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 13);
		case 1:
			return precpred(_ctx, 12);
		case 2:
			return precpred(_ctx, 11);
		case 3:
			return precpred(_ctx, 10);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3(\u00d3\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\3\2\3\2\7\2\25"+
		"\n\2\f\2\16\2\30\13\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\5\4\60\n\4\3\4\3\4\7\4\64\n\4"+
		"\f\4\16\4\67\13\4\3\4\7\4:\n\4\f\4\16\4=\13\4\3\4\3\4\3\5\3\5\3\5\3\5"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\7\6O\n\6\f\6\16\6R\13\6\5\6T"+
		"\n\6\3\6\3\6\3\6\7\6Y\n\6\f\6\16\6\\\13\6\3\6\7\6_\n\6\f\6\16\6b\13\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\5\7o\n\7\3\b\3\b\7\bs\n\b"+
		"\f\b\16\bv\13\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b"+
		"\3\b\3\b\3\b\5\b\u009a\n\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t"+
		"\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\5\t\u00b2\n\t\3\t\3\t\3\t"+
		"\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\7\t\u00c6"+
		"\n\t\f\t\16\t\u00c9\13\t\5\t\u00cb\n\t\3\t\7\t\u00ce\n\t\f\t\16\t\u00d1"+
		"\13\t\3\t\2\3\20\n\2\4\6\b\n\f\16\20\2\3\3\2$(\2\u00e9\2\22\3\2\2\2\4"+
		"\31\3\2\2\2\6+\3\2\2\2\b@\3\2\2\2\nD\3\2\2\2\fn\3\2\2\2\16\u0099\3\2\2"+
		"\2\20\u00b1\3\2\2\2\22\26\5\4\3\2\23\25\5\6\4\2\24\23\3\2\2\2\25\30\3"+
		"\2\2\2\26\24\3\2\2\2\26\27\3\2\2\2\27\3\3\2\2\2\30\26\3\2\2\2\31\32\7"+
		"\3\2\2\32\33\7!\2\2\33\34\7\4\2\2\34\35\7\5\2\2\35\36\7\6\2\2\36\37\7"+
		"\7\2\2\37 \7\b\2\2 !\7\t\2\2!\"\7\n\2\2\"#\7\13\2\2#$\7\f\2\2$%\7!\2\2"+
		"%&\7\r\2\2&\'\7\4\2\2\'(\5\16\b\2()\7\16\2\2)*\7\16\2\2*\5\3\2\2\2+,\7"+
		"\3\2\2,/\7!\2\2-.\7\17\2\2.\60\7!\2\2/-\3\2\2\2/\60\3\2\2\2\60\61\3\2"+
		"\2\2\61\65\7\4\2\2\62\64\5\b\5\2\63\62\3\2\2\2\64\67\3\2\2\2\65\63\3\2"+
		"\2\2\65\66\3\2\2\2\66;\3\2\2\2\67\65\3\2\2\28:\5\n\6\298\3\2\2\2:=\3\2"+
		"\2\2;9\3\2\2\2;<\3\2\2\2<>\3\2\2\2=;\3\2\2\2>?\7\16\2\2?\7\3\2\2\2@A\5"+
		"\f\7\2AB\7!\2\2BC\7\20\2\2C\t\3\2\2\2DE\7\5\2\2EF\5\f\7\2FG\7!\2\2GS\7"+
		"\t\2\2HI\5\f\7\2IP\7!\2\2JK\7\21\2\2KL\5\f\7\2LM\7!\2\2MO\3\2\2\2NJ\3"+
		"\2\2\2OR\3\2\2\2PN\3\2\2\2PQ\3\2\2\2QT\3\2\2\2RP\3\2\2\2SH\3\2\2\2ST\3"+
		"\2\2\2TU\3\2\2\2UV\7\r\2\2VZ\7\4\2\2WY\5\b\5\2XW\3\2\2\2Y\\\3\2\2\2ZX"+
		"\3\2\2\2Z[\3\2\2\2[`\3\2\2\2\\Z\3\2\2\2]_\5\16\b\2^]\3\2\2\2_b\3\2\2\2"+
		"`^\3\2\2\2`a\3\2\2\2ac\3\2\2\2b`\3\2\2\2cd\7\22\2\2de\5\20\t\2ef\7\20"+
		"\2\2fg\7\16\2\2g\13\3\2\2\2hi\7\23\2\2ij\7\13\2\2jo\7\f\2\2ko\7\24\2\2"+
		"lo\7\23\2\2mo\7!\2\2nh\3\2\2\2nk\3\2\2\2nl\3\2\2\2nm\3\2\2\2o\r\3\2\2"+
		"\2pt\7\4\2\2qs\5\16\b\2rq\3\2\2\2sv\3\2\2\2tr\3\2\2\2tu\3\2\2\2uw\3\2"+
		"\2\2vt\3\2\2\2w\u009a\7\16\2\2xy\7\25\2\2yz\7\t\2\2z{\5\20\t\2{|\7\r\2"+
		"\2|}\5\16\b\2}~\7\26\2\2~\177\5\16\b\2\177\u009a\3\2\2\2\u0080\u0081\7"+
		"\27\2\2\u0081\u0082\7\t\2\2\u0082\u0083\5\20\t\2\u0083\u0084\7\r\2\2\u0084"+
		"\u0085\5\16\b\2\u0085\u009a\3\2\2\2\u0086\u0087\7\30\2\2\u0087\u0088\7"+
		"\t\2\2\u0088\u0089\5\20\t\2\u0089\u008a\7\r\2\2\u008a\u008b\7\20\2\2\u008b"+
		"\u009a\3\2\2\2\u008c\u008d\7!\2\2\u008d\u008e\7\31\2\2\u008e\u008f\5\20"+
		"\t\2\u008f\u0090\7\20\2\2\u0090\u009a\3\2\2\2\u0091\u0092\7!\2\2\u0092"+
		"\u0093\7\13\2\2\u0093\u0094\5\20\t\2\u0094\u0095\7\f\2\2\u0095\u0096\7"+
		"\31\2\2\u0096\u0097\5\20\t\2\u0097\u0098\7\20\2\2\u0098\u009a\3\2\2\2"+
		"\u0099p\3\2\2\2\u0099x\3\2\2\2\u0099\u0080\3\2\2\2\u0099\u0086\3\2\2\2"+
		"\u0099\u008c\3\2\2\2\u0099\u0091\3\2\2\2\u009a\17\3\2\2\2\u009b\u009c"+
		"\b\t\1\2\u009c\u00b2\7\"\2\2\u009d\u00b2\7\34\2\2\u009e\u00b2\7\35\2\2"+
		"\u009f\u00b2\7!\2\2\u00a0\u00b2\7\36\2\2\u00a1\u00a2\7\37\2\2\u00a2\u00a3"+
		"\7\23\2\2\u00a3\u00a4\7\13\2\2\u00a4\u00a5\5\20\t\2\u00a5\u00a6\7\f\2"+
		"\2\u00a6\u00b2\3\2\2\2\u00a7\u00a8\7\37\2\2\u00a8\u00a9\7!\2\2\u00a9\u00aa"+
		"\7\t\2\2\u00aa\u00b2\7\r\2\2\u00ab\u00ac\7 \2\2\u00ac\u00b2\5\20\t\4\u00ad"+
		"\u00ae\7\t\2\2\u00ae\u00af\5\20\t\2\u00af\u00b0\7\r\2\2\u00b0\u00b2\3"+
		"\2\2\2\u00b1\u009b\3\2\2\2\u00b1\u009d\3\2\2\2\u00b1\u009e\3\2\2\2\u00b1"+
		"\u009f\3\2\2\2\u00b1\u00a0\3\2\2\2\u00b1\u00a1\3\2\2\2\u00b1\u00a7\3\2"+
		"\2\2\u00b1\u00ab\3\2\2\2\u00b1\u00ad\3\2\2\2\u00b2\u00cf\3\2\2\2\u00b3"+
		"\u00b4\f\17\2\2\u00b4\u00b5\t\2\2\2\u00b5\u00ce\5\20\t\20\u00b6\u00b7"+
		"\f\16\2\2\u00b7\u00b8\7\13\2\2\u00b8\u00b9\5\20\t\2\u00b9\u00ba\7\f\2"+
		"\2\u00ba\u00ce\3\2\2\2\u00bb\u00bc\f\r\2\2\u00bc\u00bd\7\32\2\2\u00bd"+
		"\u00ce\7\33\2\2\u00be\u00bf\f\f\2\2\u00bf\u00c0\7\32\2\2\u00c0\u00c1\7"+
		"!\2\2\u00c1\u00ca\7\t\2\2\u00c2\u00c7\5\20\t\2\u00c3\u00c4\7\21\2\2\u00c4"+
		"\u00c6\5\20\t\2\u00c5\u00c3\3\2\2\2\u00c6\u00c9\3\2\2\2\u00c7\u00c5\3"+
		"\2\2\2\u00c7\u00c8\3\2\2\2\u00c8\u00cb\3\2\2\2\u00c9\u00c7\3\2\2\2\u00ca"+
		"\u00c2\3\2\2\2\u00ca\u00cb\3\2\2\2\u00cb\u00cc\3\2\2\2\u00cc\u00ce\7\r"+
		"\2\2\u00cd\u00b3\3\2\2\2\u00cd\u00b6\3\2\2\2\u00cd\u00bb\3\2\2\2\u00cd"+
		"\u00be\3\2\2\2\u00ce\u00d1\3\2\2\2\u00cf\u00cd\3\2\2\2\u00cf\u00d0\3\2"+
		"\2\2\u00d0\21\3\2\2\2\u00d1\u00cf\3\2\2\2\22\26/\65;PSZ`nt\u0099\u00b1"+
		"\u00c7\u00ca\u00cd\u00cf";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
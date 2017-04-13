package grails.plugins.mail

import static grails.plugins.mail.MailGrailsPlugin.convertMapToSingleLevel
import static grails.plugins.mail.MailGrailsPlugin.convertNestedMapToSingleLevelMap

/**
 * Test case for {@link MailGrailsPlugin}.
 * @author Puneet Behl
 */
class MailGrailsPluginTests extends GroovyTestCase {
    void testCanary() {
        assertEquals true, true
    }


    void testConvertNestedMapToSingleLevelMap() {
        compare convertNestedMapToSingleLevelMap([a: [b: 'c']]), ['a.b': 'c']
        compare convertNestedMapToSingleLevelMap([x: 'y']), [x: 'y']
        compare(convertNestedMapToSingleLevelMap(
                [
                        mail:
                                [
                                        smtp:
                                                [
                                                        auth         : true,
                                                        socketFactory:
                                                                [
                                                                        port    : 465,
                                                                        class   : 'javax.net.ssl.SSLSocketFactory',
                                                                        fallback: false
                                                                ]
                                                ]
                                ]
                ]),
                ['mail.smtp.auth'                  : true,
                 'mail.smtp.socketFactory.port'    : 465,
                 'mail.smtp.socketFactory.class'   : 'javax.net.ssl.SSLSocketFactory',
                 'mail.smtp.socketFactory.fallback': 'false'])
        compare(
            convertNestedMapToSingleLevelMap(['mail.smtp.auth'                  : true,
                 'mail.smtp.socketFactory.port'    : 465,
                 'mail.smtp.socketFactory.class'   : 'javax.net.ssl.SSLSocketFactory',
                 'mail.smtp.socketFactory.fallback': 'false']),
            
                ['mail.smtp.auth'                  : true,
                 'mail.smtp.socketFactory.port'    : 465,
                 'mail.smtp.socketFactory.class'   : 'javax.net.ssl.SSLSocketFactory',
                 'mail.smtp.socketFactory.fallback': 'false']
        )
    }

    void testConvertMapToSingleLevel() {
        compare convertMapToSingleLevel('x', [a: 1, b: 2]), ['x.a': 1, 'x.b': 2]
        compare convertMapToSingleLevel('x', [a: 1, b: [c: 2, d: 3]]), ['x.a': 1, 'x.b.c': 2, 'x.b.d': 3]
        compare convertMapToSingleLevel('x', [a: 1, b: [c: [d: 3]]]), ['x.a': 1, 'x.b.c.d': 3]
    }

    static void compare(result, expected) {
        assertEquals expected.toString(), result.toString()
    }

}
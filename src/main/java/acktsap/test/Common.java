/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.test;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class Common {

  @Getter
  protected final String value;

}

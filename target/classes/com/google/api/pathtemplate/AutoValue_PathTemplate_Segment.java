
package com.google.api.pathtemplate;

import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
final class AutoValue_PathTemplate_Segment extends PathTemplate.Segment {

  private final PathTemplate.SegmentKind kind;
  private final String value;

  AutoValue_PathTemplate_Segment(
      PathTemplate.SegmentKind kind,
      String value) {
    if (kind == null) {
      throw new NullPointerException("Null kind");
    }
    this.kind = kind;
    if (value == null) {
      throw new NullPointerException("Null value");
    }
    this.value = value;
  }

  @Override
  PathTemplate.SegmentKind kind() {
    return kind;
  }

  @Override
  String value() {
    return value;
  }

  @Override
  public String toString() {
    return "Segment{"
        + "kind=" + kind + ", "
        + "value=" + value
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof PathTemplate.Segment) {
      PathTemplate.Segment that = (PathTemplate.Segment) o;
      return (this.kind.equals(that.kind()))
           && (this.value.equals(that.value()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= kind.hashCode();
    h *= 1000003;
    h ^= value.hashCode();
    return h;
  }

}

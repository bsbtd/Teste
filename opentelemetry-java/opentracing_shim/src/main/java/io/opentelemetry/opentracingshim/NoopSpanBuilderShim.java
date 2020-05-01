/*
 * Copyright 2019, OpenTelemetry Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.opentelemetry.opentracingshim;

import io.opentelemetry.trace.DefaultTracer;
import io.opentracing.Span;
import io.opentracing.SpanContext;
import io.opentracing.Tracer.SpanBuilder;
import io.opentracing.tag.Tag;

final class NoopSpanBuilderShim extends BaseShimObject implements SpanBuilder {
  private final String spanName;

  public NoopSpanBuilderShim(TelemetryInfo telemetryInfo, String spanName) {
    super(telemetryInfo);
    this.spanName = spanName == null ? "" : spanName; // OT is more permissive than OTel.
  }

  @Override
  public SpanBuilder asChildOf(Span parent) {
    return this;
  }

  @Override
  public SpanBuilder asChildOf(SpanContext parent) {
    return this;
  }

  @Override
  public SpanBuilder addReference(String referenceType, SpanContext referencedContext) {
    return this;
  }

  @Override
  public SpanBuilder ignoreActiveSpan() {
    return this;
  }

  @Override
  public SpanBuilder withTag(String key, String value) {
    return this;
  }

  @Override
  public SpanBuilder withTag(String key, boolean value) {
    return this;
  }

  @Override
  public SpanBuilder withTag(String key, Number number) {
    return this;
  }

  @Override
  public <T> SpanBuilder withTag(Tag<T> tag, T value) {
    return this;
  }

  @Override
  public SpanBuilder withStartTimestamp(long microseconds) {
    return this;
  }

  @Override
  public Span start() {
    return new SpanShim(
        telemetryInfo, DefaultTracer.getInstance().spanBuilder(spanName).startSpan());
  }
}

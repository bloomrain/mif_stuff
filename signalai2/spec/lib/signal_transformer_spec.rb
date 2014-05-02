require 'spec_helper'

describe SignalsTransformer do
  let(:signals) {
    { '2012-01' => 5,
      '2012-02' => 12,
      '2012-03' => 99
    }
  }
  subject{ SignalsTransformer.new(signals) }

  its(:fake_signals_count){ should == 1 }

  describe '#signals' do
    it 'should always contain length of format 2**n' do
      expect(subject.signals.length).to eq(4)
    end

    it 'fills missing signals with 0 values' do
      expect(subject.signals.values.last).to eq(0.0)
    end
  end

  describe "#c" do
    it 'should return mean value on first member' do
      subject.c(0).should == 29
    end

    it 'should return some value on second member' do # I'm unsure about this result
      subject.c(1).should == Complex('-23.5-3.0i')
    end

    it 'should return some value on third member' do # I'm unsure about this result
      subject.c(2).should == Complex('23.0+0.0i')
    end
  end

  describe '#fourier_signals' do
    it 'should be very similar to original signals' do
      fourier_signals = subject.fourier
      expect(fourier_signals.values[0].real).to be_within(0.000001).of(5)
      expect(fourier_signals.values[1].real).to be_within(0.000001).of(12)
      expect(fourier_signals.values[2].real).to be_within(0.000001).of(99)
      expect(fourier_signals.values[3].real).to be_within(0.000001).of(0)
    end

    context 'when using filter' do
      let(:filter_options){ {from_ck_number: 12} }
      let(:signals) {
        { '2012-01' => 5,
          '2012-02' => 12,
          '2012-03' => 24,
          '2012-04' => 22,
          '2012-05' => 28,
          '2012-06' => 8,
          '2012-07' => 5,
          '2012-08' => 2,
          '2012-09' => 99,
          '2012-10' => 80,
          '2012-11' => 85,
          '2012-12' => 90
        }
      }
      subject{ SignalsTransformer.new(signals, filter_options) }

      context 'when filtering from ck number that is bigger then last ck number' do
        it 'returns same result as without filter' do
          fourier_signals = subject.fourier
          signal_values = fourier_signals.values.map{|v| v.real.round(10) }
          expected_values = signals.values.map(&:to_f) + [0.0] * 4
          expect(signal_values).to eq expected_values
        end
      end

      context 'when filtering some of ck\'s' do
        let(:filter_options){ {from_ck_number: 8} }

        it 'returns different signals then without filter' do
          fourier_signals = subject.fourier
          signal_values = fourier_signals.values.map{|v| v.real.round(10) }
          expected_values = signals.values.map(&:to_f) + [0.0] * 4
          expect(signal_values).to_not eq expected_values
        end
      end
    end

    context 'with noise' do
      subject{ SignalsTransformer.new(signals, noise: noise) }
      let(:noise){ [1,1,1,1] }
      it 'adds noise values to signal values' do
        signals = subject.signals
        expect(signals.values).to eq [6, 13, 100, 1]
      end
    end
  end

  its(:power_spectrum){ should == "asdf"}
end